package com.pknu.finalproject.officer.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;

import com.pknu.finalproject.complaint.entity.CommonCode;
import com.pknu.finalproject.complaint.entity.ComplaintWork;
import com.pknu.finalproject.complaint.entity.EventStatus;
import com.pknu.finalproject.complaint.entity.EventTransfer;
import com.pknu.finalproject.complaint.entity.EventType;
import com.pknu.finalproject.complaint.entity.ProcessEvent;
import com.pknu.finalproject.complaint.entity.Reply;
import com.pknu.finalproject.complaint.entity.Status;
import com.pknu.finalproject.complaint.repository.CommonCodeRepository;
import com.pknu.finalproject.complaint.repository.ComplaintWorkRepository;
import com.pknu.finalproject.complaint.repository.EventStatusRepository;
import com.pknu.finalproject.complaint.repository.EventTransferRepository;
import com.pknu.finalproject.complaint.repository.EventTypeRepository;
import com.pknu.finalproject.complaint.repository.ProcessEventRepository;
import com.pknu.finalproject.complaint.repository.ReplyRepository;
import com.pknu.finalproject.complaint.repository.StatusRepository;
import com.pknu.finalproject.department.entity.Department;
import com.pknu.finalproject.department.repository.DepartmentRepository;
import com.pknu.finalproject.officer.dto.request.AssignRequest;
import com.pknu.finalproject.officer.dto.request.ReplyRequest;
import com.pknu.finalproject.officer.dto.request.RejectRequest;
import com.pknu.finalproject.officer.dto.request.TransferRequest;
import com.pknu.finalproject.officer.dto.response.OfficerComplaintListResponse;
import com.pknu.finalproject.officer.entity.Officer;
import com.pknu.finalproject.officer.entity.WorkAssignment;
import com.pknu.finalproject.officer.repository.OfficerRepository;
import com.pknu.finalproject.officer.repository.WorkAssignmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
/**
 * 공무원의 민원 처리 명령을 트랜잭션 단위로 수행한다.
 * 업무 상태 변경과 함께 PROCESS_EVENT 및 유형별 상세 이력도 저장한다.
 */
public class OfficerComplaintServiceImpl implements OfficerComplaintService {

    private final OfficerRepository officerRepository;
    private final ComplaintWorkRepository complaintWorkRepository;
    private final WorkAssignmentRepository workAssignmentRepository;
    private final DepartmentRepository departmentRepository;
    private final StatusRepository statusRepository;
    private final ReplyRepository replyRepository;
    private final CommonCodeRepository commonCodeRepository;
    private final EventTypeRepository eventTypeRepository;
    private final ProcessEventRepository processEventRepository;
    private final EventStatusRepository eventStatusRepository;
    private final EventTransferRepository eventTransferRepository;

    @Override
    public List<OfficerComplaintListResponse> getComplaints(
            Long accountId,
            String tab,
            LocalDate startDate,
            LocalDate endDate,
            String status,
            String keyword
    ) {
        Officer officer = getOfficerOrThrow(accountId);
        if ("returned".equals(tab)
                && !isOversightDepartment(officer)) {
            throw new AccessDeniedException("반송민원은 일반소관확인 부서만 조회할 수 있습니다.");
        }
        Long officerId = "assigned".equals(tab) ? officer.getAccountId() : null;
        int returned = "returned".equals(tab) ? 1 : 0;
        LocalDateTime start = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime end = endDate != null ? endDate.plusDays(1).atStartOfDay() : null;
        String statusName = status == null || status.isBlank() || "all".equals(status)
                ? null : status;
        String keywordParam = keyword == null || keyword.isBlank() ? null : keyword;

        return complaintWorkRepository.searchByDeptAndFilters(
                        officer.getDept().getDeptId(), officerId, returned,
                        start, end, statusName, keywordParam)
                .stream()
                .map(work -> OfficerComplaintListResponse.from(
                        work,
                        eventTransferRepository.countByComplaintId(work.getComplaint().getComplaintId())))
                .toList();
    }

    @Override
    @Transactional
    /** 기존 현재 배정을 종료하고 같은 부서 담당자에게 새 현재 배정을 생성한다. */
    public void assign(Long workId, Long requesterAccountId, AssignRequest request) {
        Officer requester = getOfficerOrThrow(requesterAccountId);
        requireManager(requester);
        ComplaintWork work = getWorkOrThrow(workId);
        requireActiveWork(work);

        if (!work.getDept().getDeptId().equals(requester.getDept().getDeptId())) {
            throw new RuntimeException("소속 부서의 업무만 배정할 수 있습니다.");
        }

        Officer target = getOfficerOrThrow(request.getOfficerId());
        if (!target.getDept().getDeptId().equals(work.getDept().getDeptId())) {
            throw new RuntimeException("같은 부서 담당자에게만 배정할 수 있습니다.");
        }

        WorkAssignment current = workAssignmentRepository
                .findByWorkWorkIdAndUnassignedAtIsNull(workId)
                .orElse(null);
        boolean reassignment = current != null;

        if (current != null) {
            if (current.getOfficer().getAccountId().equals(target.getAccountId())) {
                throw new RuntimeException("이미 해당 담당자에게 배정된 업무입니다.");
            }
            current.setUnassignedAt(LocalDateTime.now());
            current.setUnassignReasonCode(getCode("UNASSIGN_REASON", "OFFICER_CHANGED"));

            // 새 담당자를 INSERT하기 전에 기존 담당자 해제를 DB에 반영
            workAssignmentRepository.saveAndFlush(current);
        }

        WorkAssignment assignment = new WorkAssignment();
        assignment.setWork(work);
        assignment.setOfficer(target);
        assignment.setAssignedByAccount(requester.getAccount());
        assignment.setAssignReasonCode(getCode("ASSIGN_REASON", reassignment ? "REASSIGN" : "INITIAL"));
        workAssignmentRepository.save(assignment);

        Status oldStatus = work.getStatus();
        Status assignedStatus = getStatus("ASSIGNED");
        work.setOfficer(target);
        work.setStatus(assignedStatus);
        complaintWorkRepository.save(work);

        createEvent(work, requester, reassignment ? "OFFICER_CHANGED" : "OFFICER_ASSIGNED",
                target.getName() + " 담당자 지정", null);

        if (!oldStatus.getStatusId().equals(assignedStatus.getStatusId())) {
            saveStatusEvent(work, requester, oldStatus, assignedStatus, "담당자 배정 완료");
        }
    }

    @Override
    @Transactional
    /** 현재 업무를 종료하고 대상 부서에 부모 업무가 연결된 RECEIVED 후속 업무를 생성한다. */
    public void transfer(Long workId, Long requesterAccountId, TransferRequest request) {
        Officer requester = getOfficerOrThrow(requesterAccountId);
        requireManager(requester);
        ComplaintWork oldWork = getWorkOrThrow(workId);
        requireActiveWork(oldWork);

        if (!oldWork.getDept().getDeptId().equals(requester.getDept().getDeptId())) {
            throw new RuntimeException("소속 부서의 업무만 이관할 수 있습니다.");
        }
        long transferCount = eventTransferRepository
                .countByComplaintId(oldWork.getComplaint().getComplaintId());

        Department fromDept = oldWork.getDept();
        Department toDept = departmentRepository.findById(request.getToDeptId())
                .orElseThrow(() -> new RuntimeException("이관 대상 부서를 찾을 수 없습니다."));
        if (fromDept.getDeptId().equals(toDept.getDeptId())) {
            throw new RuntimeException("현재 부서와 다른 부서로만 이관할 수 있습니다.");
        }
        boolean alreadyAssigned = complaintWorkRepository
                .findByComplaintComplaintIdOrderByWorkSequenceAsc(oldWork.getComplaint().getComplaintId())
                .stream()
                .filter(work -> "Y".equals(work.getEnabled()))
                .anyMatch(work -> work.getDept() != null
                        && work.getDept().getDeptId().equals(toDept.getDeptId()));
        if (alreadyAssigned) {
            throw new RuntimeException("현재 민원에 이미 배정된 부서로는 이관할 수 없습니다.");
        }

        workAssignmentRepository.findByWorkWorkIdAndUnassignedAtIsNull(workId).ifPresent(current -> {
            current.setUnassignedAt(LocalDateTime.now());
            current.setUnassignReasonCode(getCode("UNASSIGN_REASON", "DEPARTMENT_TRANSFERRED"));
            workAssignmentRepository.save(current);
        });

        oldWork.setEndedAt(LocalDateTime.now());
        oldWork.setEnabled("N");
        oldWork.setWorkResultCode(getCode("WORK_RESULT", "TRANSFERRED"));
        complaintWorkRepository.save(oldWork);

        ComplaintWork newWork = new ComplaintWork();
        newWork.setComplaint(oldWork.getComplaint());
        newWork.setParentWork(oldWork);
        newWork.setDept(toDept);
        newWork.setStatus(getStatus("RECEIVED"));
        newWork.setWorkSequence(complaintWorkRepository
                .findMaxWorkSequence(oldWork.getComplaint().getComplaintId()) + 1);
        newWork.setCreatedByAccount(requester.getAccount());
        newWork.setEnabled("Y");
        newWork = complaintWorkRepository.save(newWork);

        ProcessEvent transferEvent = createEvent(oldWork, requester, "DEPARTMENT_TRANSFERRED",
                fromDept.getDeptName() + " → " + toDept.getDeptName(), request.getReason());

        EventTransfer detail = new EventTransfer();
        detail.setEvent(transferEvent);
        detail.setFromDept(fromDept);
        detail.setToDept(toDept);
        detail.setTransferReasonCode(getCode("TRANSFER_REASON",
                request.getReasonCode() == null || request.getReasonCode().isBlank()
                        ? "MIS_ASSIGNED" : request.getReasonCode()));
        detail.setTransferReasonDetail(request.getReason());
        detail.setNewWork(newWork);
        eventTransferRepository.save(detail);

        createEvent(newWork, requester, "WORK_CREATED", toDept.getDeptName() + " 업무 생성", null);

        if (transferCount + 1 >= 3) {
            rejectForTransferLimit(newWork, requester);
        }
    }

    @Override
    @Transactional
    /** 답변을 저장하고 업무를 COMPLETED로 변경하며 답변·상태 이벤트를 남긴다. */
    public void reply(Long workId, Long requesterAccountId, ReplyRequest request) {
        Officer requester = getOfficerOrThrow(requesterAccountId);
        ComplaintWork work = getWorkOrThrow(workId);
        boolean returnedReply = isOversightDepartment(requester)
                && "Y".equals(work.getEnabled())
                && work.getStatus() != null
                && "REJECTED".equals(work.getStatus().getStatusCode());

        if (!returnedReply) {
            requireActiveWork(work);
        }
        if (!returnedReply
                && (work.getOfficer() == null
                || !work.getOfficer().getAccountId().equals(requesterAccountId))) {
            throw new RuntimeException("본인에게 배정된 업무에만 답변할 수 있습니다.");
        }
        if (request.getTitle() == null || request.getTitle().isBlank()
                || request.getContent() == null || request.getContent().isBlank()) {
            throw new RuntimeException("답변 제목과 내용을 입력해야 합니다.");
        }

        String replyTitle = request.getTitle().trim();

        Reply reply = replyRepository.findByWorkWorkId(workId).orElseGet(Reply::new);
        boolean newReply = reply.getReplyId() == null;
        reply.setWork(work);
        reply.setOfficer(requester);
        reply.setTitle(replyTitle);
        reply.setContent(request.getContent());
        replyRepository.save(reply);

        Status oldStatus = work.getStatus();
        Status completedStatus = getStatus("COMPLETED");
        if (returnedReply) {
            work.setOfficer(requester);
        }
        work.setStatus(completedStatus);
        work.setEndedAt(LocalDateTime.now());
        work.setWorkResultCode(getCode("WORK_RESULT", "COMPLETED"));
        complaintWorkRepository.save(work);

        if (newReply) {
            createEvent(work, requester, "REPLY_CREATED", replyTitle, null);
        }
        createEvent(work, requester, "REPLY_COMPLETED", replyTitle, null);
        if (!oldStatus.getStatusId().equals(completedStatus.getStatusId())) {
            saveStatusEvent(work, requester, oldStatus, completedStatus,
                    returnedReply ? "일반소관확인 부서 최종 답변 등록" : "최종 답변 등록");
        }
    }

    @Override
    @Transactional
    /** 반려 사유를 저장하고 활성 분리민원 수에 따라 업무 활성 여부를 결정한다. */
    public void reject(Long workId, Long requesterAccountId, RejectRequest request) {
        Officer requester = getOfficerOrThrow(requesterAccountId);
        ComplaintWork candidateWork = complaintWorkRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("민원 업무 정보를 찾을 수 없습니다."));
        List<ComplaintWork> complaintWorks = complaintWorkRepository
                .findAllByComplaintIdForUpdate(candidateWork.getComplaint().getComplaintId());
        ComplaintWork work = complaintWorks.stream()
                .filter(candidate -> candidate.getWorkId().equals(workId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("민원 업무 정보를 찾을 수 없습니다."));
        requireActiveWork(work);

        if (isOversightDepartment(requester)) {
            throw new AccessDeniedException("일반소관확인 부서는 민원을 반려할 수 없습니다.");
        }

        if (work.getOfficer() == null || !work.getOfficer().getAccountId().equals(requesterAccountId)) {
            throw new RuntimeException("본인에게 배정된 업무만 반려할 수 있습니다.");
        }
        if (request.getReason() == null || request.getReason().isBlank()) {
            throw new RuntimeException("반려 사유를 입력해야 합니다.");
        }

        Status oldStatus = work.getStatus();
        Status rejectedStatus = getStatus("REJECTED");
        long activeWorkCount = complaintWorks.stream()
                .filter(candidate -> "Y".equals(candidate.getEnabled()))
                .filter(candidate -> candidate.getEndedAt() == null)
                .count();

        work.setStatus(rejectedStatus);
        work.setEndedAt(LocalDateTime.now());
        // 다른 활성 분리민원이 있으면 비활성화한다. 마지막 활성 업무는
        // 일반소관확인 부서의 반송 목록에서 조회할 수 있도록 활성 상태를 유지한다.
        work.setEnabled(activeWorkCount > 1 ? "N" : "Y");
        work.setWorkResultCode(getCode("WORK_RESULT", "REJECTED"));
        complaintWorkRepository.save(work);

        createEvent(work, requester, "WORK_REJECTED", request.getReason(), null);
        saveStatusEvent(work, requester, oldStatus, rejectedStatus, request.getReason());
    }

    @Override
    @Transactional
    public void startProcessing(Long workId, Long requesterAccountId) {
        Officer requester = getOfficerOrThrow(requesterAccountId);
        ComplaintWork work = getWorkOrThrow(workId);
        requireActiveWork(work);

        if (work.getOfficer() == null || !work.getOfficer().getAccountId().equals(requesterAccountId)) {
            throw new RuntimeException("본인에게 배정된 업무만 처리 중 상태로 변경할 수 있습니다.");
        }
        if (!"ASSIGNED".equals(work.getStatus().getStatusCode())) {
            throw new RuntimeException("담당자배정 상태의 업무만 처리 중으로 변경할 수 있습니다.");
        }

        Status oldStatus = work.getStatus();
        Status processing = getStatus("PROCESSING");
        work.setStatus(processing);
        complaintWorkRepository.save(work);
        saveStatusEvent(work, requester, oldStatus, processing, "담당자 처리 시작");
    }

    private ProcessEvent createEvent(
            ComplaintWork work,
            Officer actor,
            String eventCode,
            String comment,
            String internalComment
    ) {
        // 업무별 최대 EVENT_SEQUENCE 다음 번호를 사용해 이벤트의 논리 순서를 보장한다.
        EventType type = eventTypeRepository.findByEventCode(eventCode)
                .orElseThrow(() -> new RuntimeException("이벤트 유형을 찾을 수 없습니다: " + eventCode));
        ProcessEvent event = new ProcessEvent();
        event.setWork(work);
        event.setEventType(type);
        event.setAccount(actor != null ? actor.getAccount() : null);
        event.setEventSequence(processEventRepository.findMaxEventSequence(work.getWorkId()) + 1);
        event.setPublicYn(type.getDefaultPublicYn());
        event.setEventComment(internalComment != null ? internalComment : comment);
        return processEventRepository.save(event);
    }

    private void saveStatusEvent(
            ComplaintWork work,
            Officer actor,
            Status before,
            Status after,
            String reason
    ) {
        // 공통 이벤트와 상태 전용 상세(EVENT_STATUS)를 같은 이벤트 ID로 기록한다.
        ProcessEvent event = createEvent(work, actor, "STATUS_CHANGED",
                before.getStatusName() + " → " + after.getStatusName(), reason);
        EventStatus detail = new EventStatus();
        detail.setEvent(event);
        detail.setBeforeStatus(before);
        detail.setAfterStatus(after);
        detail.setStatusReason(reason);
        eventStatusRepository.save(detail);
    }

    private void rejectForTransferLimit(ComplaintWork work, Officer requester) {
        // 세 번째 이관으로 생성된 후속 업무를 자동 반려하고 입력된 이관 사유도 함께 보존한다.
        List<ComplaintWork> complaintWorks = complaintWorkRepository
                .findAllByComplaintIdForUpdate(work.getComplaint().getComplaintId());
        long activeWorkCount = complaintWorks.stream()
                .filter(candidate -> "Y".equals(candidate.getEnabled()))
                .filter(candidate -> candidate.getEndedAt() == null)
                .count();

        Status oldStatus = work.getStatus();
        Status rejectedStatus = getStatus("REJECTED");
        String reason = "3회 이상 이관 시도로 인한 자동 반려";

        work.setStatus(rejectedStatus);
        work.setEndedAt(LocalDateTime.now());
        work.setEnabled(activeWorkCount > 1 ? "N" : "Y");
        work.setWorkResultCode(getCode("WORK_RESULT", "REJECTED"));
        complaintWorkRepository.save(work);

        createEvent(work, requester, "WORK_REJECTED", reason, null);
        saveStatusEvent(work, requester, oldStatus, rejectedStatus, reason);
    }

    private CommonCode getCode(String groupCode, String codeValue) {
        return commonCodeRepository.findByCodeGroupGroupCodeAndCodeValue(groupCode, codeValue)
                .orElseThrow(() -> new RuntimeException("공통 코드를 찾을 수 없습니다: " + groupCode + "/" + codeValue));
    }

    private Status getStatus(String statusCode) {
        return statusRepository.findByStatusCode(statusCode)
                .orElseThrow(() -> new RuntimeException("상태 코드를 찾을 수 없습니다: " + statusCode));
    }

    private void requireManager(Officer officer) {
        if (officerRepository.countManagerRoles(officer.getAccountId()) == 0) {
            throw new RuntimeException("매니저 권한이 필요합니다.");
        }
    }

    private boolean isOversightDepartment(Officer officer) {
        if (officer == null || officer.getDept() == null) {
            return false;
        }
        String deptName = officer.getDept().getDeptName();
        return "일반소관확인".equals(deptName);
    }

    private Officer getOfficerOrThrow(Long accountId) {
        return officerRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("담당자 정보를 찾을 수 없습니다."));
    }

    private ComplaintWork getWorkOrThrow(Long workId) {
        return complaintWorkRepository.findByIdForUpdate(workId)
                .orElseThrow(() -> new RuntimeException("민원 업무 정보를 찾을 수 없습니다."));
    }

    private void requireActiveWork(ComplaintWork work) {
        if (work.getEndedAt() != null || !"Y".equals(work.getEnabled())) {
            throw new RuntimeException("이미 종료되었거나 비활성화된 업무입니다.");
        }
    }
}
