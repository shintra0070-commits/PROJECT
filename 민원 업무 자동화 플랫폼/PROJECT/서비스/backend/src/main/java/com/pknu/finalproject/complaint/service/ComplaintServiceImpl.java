package com.pknu.finalproject.complaint.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pknu.finalproject.complaint.dto.response.ComplaintDetailResponse;
import com.pknu.finalproject.complaint.dto.response.ComplaintListResponse;
import com.pknu.finalproject.complaint.dto.response.PagedComplaintResponse;
import com.pknu.finalproject.complaint.entity.Complaint;
import com.pknu.finalproject.complaint.entity.ComplaintWork;
import com.pknu.finalproject.complaint.entity.EventTransfer;
import com.pknu.finalproject.complaint.entity.ProcessEvent;
import com.pknu.finalproject.complaint.entity.Reply;
import com.pknu.finalproject.complaint.repository.AttachmentRepository;
import com.pknu.finalproject.complaint.repository.ComplaintRepository;
import com.pknu.finalproject.complaint.repository.ComplaintWorkRepository;
import com.pknu.finalproject.complaint.repository.EventTransferRepository;
import com.pknu.finalproject.complaint.repository.EventStatusRepository;
import com.pknu.finalproject.complaint.repository.ProcessEventRepository;
import com.pknu.finalproject.complaint.repository.ReplyRepository;
import com.pknu.finalproject.officer.entity.Officer;
import com.pknu.finalproject.officer.repository.OfficerRepository;
import com.pknu.finalproject.officer.repository.WorkAssignmentRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
/**
 * 민원 조회 결과를 요청자의 권한에 맞게 조립하는 서비스 구현체이다.
 * 원민원의 모든 업무와 이벤트를 조회해 현재 배정, 활성 업무, 공개 이력,
 * 최종 답변 완료 여부를 계산한다.
 */
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final AttachmentRepository attachmentRepository;
    private final ComplaintWorkRepository complaintWorkRepository;
    private final ReplyRepository replyRepository;
    private final OfficerRepository officerRepository;
    private final ProcessEventRepository processEventRepository;
    private final EventTransferRepository eventTransferRepository;
    private final EventStatusRepository eventStatusRepository;
    private final WorkAssignmentRepository workAssignmentRepository;
    private final EntityManager entityManager;

    @Override
    /** 회원 전체 목록 또는 비회원 전화번호 기준 목록을 반환한다. */
    public PagedComplaintResponse getList(
            Long requesterAccountId,
            String guestPhone,
            int page,
            int size,
            String status,
            boolean mineOnly
    ) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 100);
        String phoneDigits = null;
        boolean guestVerified = guestPhone != null && !guestPhone.isBlank();
        if (guestVerified) {
            phoneDigits = normalizePhone(guestPhone);
            validateGuestPhone(phoneDigits);
        }

        String normalizedStatus = normalizeListStatus(status);
        PageRequest pageable = PageRequest.of(
                safePage,
                safeSize,
                Sort.by(Sort.Direction.DESC, "complaintId")
        );
        Long accountFilter = mineOnly ? requesterAccountId : null;
        Page<Complaint> complaintPage = complaintRepository.findForRequester(
                accountFilter,
                phoneDigits,
                normalizedStatus,
                pageable
        );

        List<Long> complaintIds = complaintPage.getContent().stream()
                .map(Complaint::getComplaintId)
                .toList();
        Map<Long, List<ComplaintWork>> worksByComplaintId = new HashMap<>();
        if (!complaintIds.isEmpty()) {
            complaintWorkRepository.findActiveByComplaintIds(complaintIds)
                    .forEach(work -> worksByComplaintId
                            .computeIfAbsent(work.getComplaint().getComplaintId(), ignored -> new ArrayList<>())
                            .add(work));
        }

        List<ComplaintListResponse> content = complaintPage.getContent().stream()
                .map(complaint -> ComplaintListResponse.from(
                        complaint,
                        requesterAccountId,
                        guestVerified,
                        resolveListStatus(worksByComplaintId.getOrDefault(
                                complaint.getComplaintId(),
                                List.of()
                        ))
                ))
                .toList();
        return PagedComplaintResponse.from(complaintPage, content);
    }

    private String normalizeListStatus(String status) {
        if (status == null || status.isBlank() || "all".equalsIgnoreCase(status)) {
            return null;
        }
        if (!Set.of("접수", "처리중", "답변완료").contains(status)) {
            throw new IllegalArgumentException("지원하지 않는 민원 상태입니다.");
        }
        return status;
    }

    private String resolveListStatus(List<ComplaintWork> activeWorks) {
        if (!activeWorks.isEmpty() && activeWorks.stream().allMatch(this::isCompleted)) {
            return "답변완료";
        }
        if (activeWorks.isEmpty() || activeWorks.stream().allMatch(work -> work.getStatus() == null
                || "RECEIVED".equals(work.getStatus().getStatusCode()))) {
            return "접수";
        }
        return "처리중";
    }

    @Override
    /**
     * 상세 접근 권한을 검증하고 첨부파일·업무·이력·답변을 하나의 응답으로 만든다.
     * 모든 활성 업무가 COMPLETED일 때만 답변 완료로 판정한다.
     */
    public ComplaintDetailResponse getDetail(
            Long complaintId,
            String password,
            String guestPhone,
            Long requesterAccountId
    ) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 민원입니다."));

        boolean isPublic = "Y".equals(complaint.getIsPublic());
        boolean isOwner = requesterAccountId != null
                && complaint.getUser() != null
                && requesterAccountId.equals(complaint.getUser().getAccountId());
        boolean isGuestOwner = complaint.getGuest() != null
                && guestPhone != null
                && normalizePhone(complaint.getGuest().getPhone()).equals(normalizePhone(guestPhone));
        boolean isOfficer = requesterAccountId != null
                && officerRepository.existsByAccountId(requesterAccountId);
        boolean passwordVerified = false;

        if (!isPublic && !isOwner && !isGuestOwner && !isOfficer) {
            if (complaint.getPassword() == null
                    || password == null
                    || !complaint.getPassword().equals(password)) {
                throw new RuntimeException("비밀번호가 일치하지 않습니다.");
            }
            passwordVerified = true;
        }

        String rawName = complaint.getUser() != null
                ? complaint.getUser().getName()
                : complaint.getGuest().getName();
        String applicantName = isOwner || isGuestOwner || isOfficer || passwordVerified
                ? rawName : maskName(rawName);

        List<ComplaintDetailResponse.AttachmentResponse> attachments = attachmentRepository
                .findByComplaintComplaintId(complaintId)
                .stream()
                .map(ComplaintDetailResponse.AttachmentResponse::from)
                .toList();

        List<ComplaintWork> allWorks = complaintWorkRepository
                .findByComplaintComplaintIdOrderByWorkSequenceAsc(complaintId);
        // 이관 계보에서 부모가 된 업무를 찾아 assignments에는 계보별 마지막 업무만 남긴다.
        Set<Long> parentWorkIds = allWorks.stream()
                .filter(work -> work.getParentWork() != null)
                .map(work -> work.getParentWork().getWorkId())
                .collect(Collectors.toSet());

        // 각 최초 배정/이관 계보에서 가장 마지막 업무만 현재 부서 처리 결과로 노출한다.
        List<ComplaintDetailResponse.AssignmentResponse> assignments = allWorks.stream()
                .filter(work -> !parentWorkIds.contains(work.getWorkId()))
                .map(this::toAssignmentResponse)
                .toList();

        // 활성 분리민원은 사용자 답변 노출과 전체 답변완료 판정의 기준이다.
        List<ComplaintWork> activeWorks = allWorks.stream()
                .filter(work -> "Y".equals(work.getEnabled()))
                .toList();
        List<ComplaintDetailResponse.AssignmentResponse> activeAssignments = activeWorks.stream()
                .map(this::toAssignmentResponse)
                .toList();
        boolean answerCompleted = !activeWorks.isEmpty()
                && activeWorks.stream().allMatch(this::isCompleted);
        LocalDateTime answerCompletedAt = answerCompleted
                ? activeWorks.stream()
                        .map(work -> replyRepository.findByWorkWorkId(work.getWorkId()).orElse(null))
                        .filter(reply -> reply != null && reply.getCreatedAt() != null)
                        .map(Reply::getCreatedAt)
                        .max(LocalDateTime::compareTo)
                        .orElse(null)
                : null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<ComplaintDetailResponse.HistoryResponse> histories = new ArrayList<>();
        LocalDateTime receivedAt = complaint.getCreatedAt() != null
                ? complaint.getCreatedAt() : LocalDateTime.now();
        histories.add(ComplaintDetailResponse.HistoryResponse.builder()
                .date(receivedAt.format(formatter))
                .eventCode("COMPLAINT_RECEIVED")
                .text("민원 최초 접수 완료")
                .build());

        // 이관 후속 업무가 아닌 모든 최초 업무를 부서 배정 이력으로 노출한다.
        allWorks.stream()
                .filter(work -> work.getParentWork() == null)
                .forEach(originalWork -> {
            LocalDateTime assignedAt = originalWork.getCreatedAt() != null
                    ? originalWork.getCreatedAt() : receivedAt;
            String departmentName = originalWork.getDept() != null
                    ? originalWork.getDept().getDeptName() : "미지정";
            histories.add(ComplaintDetailResponse.HistoryResponse.builder()
                    .date(assignedAt.format(formatter))
                    .eventCode("DEPARTMENT_ASSIGNED")
                    .text("[부서 배정] " + departmentName + " 배정 완료")
                    .deptName(departmentName)
                    .build());
                });

        for (ProcessEvent event : processEventRepository.findTimelineByComplaintId(complaintId)) {
            if (!isOfficer && !"Y".equals(event.getPublicYn())) {
                continue;
            }
            LocalDateTime eventTime = event.getEventTime() != null ? event.getEventTime() : receivedAt;
            String eventName = event.getEventType() != null
                    ? event.getEventType().getEventName() : "처리 이력";
            String eventCode = event.getEventType() != null
                    ? event.getEventType().getEventCode() : null;
            String detail = "DEPARTMENT_TRANSFERRED".equals(eventCode)
                    ? formatTransferDetail(event)
                    : formatEventDetail(event);
            Officer eventOfficer = event.getAccount() == null
                    ? null
                    : officerRepository.findById(event.getAccount().getAccountId()).orElse(null);
            histories.add(ComplaintDetailResponse.HistoryResponse.builder()
                    .date(eventTime.format(formatter))
                    .eventCode(eventCode)
                    .text("[" + eventName + "] " + (detail == null ? "처리 완료" : detail))
                    .officerName(eventOfficer != null ? eventOfficer.getName() : null)
                    .officerPhone(eventOfficer != null ? eventOfficer.getPhone() : null)
                    .deptName(eventOfficer != null && eventOfficer.getDept() != null
                            ? eventOfficer.getDept().getDeptName() : null)
                    .automaticRejection("WORK_REJECTED".equals(eventCode)
                            && event.getEventComment() != null
                            && event.getEventComment().contains("이관")
                            && event.getEventComment().contains("자동 반려"))
                    .build());
        }

        return ComplaintDetailResponse.builder()
                .complaintId(complaint.getComplaintId())
                .title(complaint.getTitle())
                .content(complaint.getContent())
                .refinedContent(complaint.getRefinedContent())
                .createdAt(complaint.getCreatedAt())
                .isPublic(isPublic)
                .mine(isOwner || isGuestOwner)
                .applicantName(applicantName)
                .attachments(attachments)
                .assignments(assignments)
                .activeAssignments(activeAssignments)
                .histories(histories)
                .answerCompleted(answerCompleted)
                .answerCompletedAt(answerCompletedAt)
                .build();
    }

    @Override
    @Transactional
    public void delete(
            Long complaintId,
            String password,
            String guestPhone,
            Long requesterAccountId
    ) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 민원입니다."));

        boolean memberOwner = requesterAccountId != null
                && complaint.getUser() != null
                && requesterAccountId.equals(complaint.getUser().getAccountId());
        boolean guestOwner = complaint.getGuest() != null
                && guestPhone != null
                && normalizePhone(complaint.getGuest().getPhone()).equals(normalizePhone(guestPhone))
                && (complaint.getPassword() == null || complaint.getPassword().equals(password));
        if (!memberOwner && !guestOwner) {
            throw new RuntimeException("본인이 등록한 민원만 삭제할 수 있습니다.");
        }

        List<ComplaintWork> works = complaintWorkRepository
                .findByComplaintComplaintIdOrderByWorkSequenceAsc(complaintId);
        boolean receivedOnly = works.stream().allMatch(work -> work.getStatus() == null
                || "RECEIVED".equals(work.getStatus().getStatusCode()));
        if (!receivedOnly) {
            throw new RuntimeException("접수 상태의 민원만 삭제할 수 있습니다.");
        }

        List<ProcessEvent> events = processEventRepository.findTimelineByComplaintId(complaintId);
        List<Long> eventIds = events.stream().map(ProcessEvent::getEventId).toList();
        if (!eventIds.isEmpty()) {
            eventTransferRepository.deleteAllByIdInBatch(eventIds);
            eventStatusRepository.deleteAllByIdInBatch(eventIds);
            processEventRepository.deleteAllInBatch(events);
        }
        workAssignmentRepository.deleteAllInBatch(
                workAssignmentRepository.findByWorkComplaintComplaintIdOrderByAssignedAtAsc(complaintId));
        List<Reply> replies = works.stream()
                .map(work -> replyRepository.findByWorkWorkId(work.getWorkId()).orElse(null))
                .filter(reply -> reply != null)
                .toList();
        replyRepository.deleteAllInBatch(replies);
        attachmentRepository.deleteAllInBatch(attachmentRepository.findByComplaintComplaintId(complaintId));
        complaintWorkRepository.deleteAllInBatch(works);
        entityManager.clear();
        complaintRepository.deleteById(complaintId);
    }

    private boolean isCompleted(ComplaintWork work) {
        return work.getStatus() != null
                && "COMPLETED".equals(work.getStatus().getStatusCode());
    }

    private String formatTransferDetail(ProcessEvent event) {
        // EVENT_TRANSFER에서 실제 출발·도착 부서와 상세 이관 사유를 가져온다.
        EventTransfer transfer = eventTransferRepository
                .findByEventEventId(event.getEventId())
                .orElse(null);
        if (transfer == null) {
            return formatEventDetail(event);
        }

        String fromDept = transfer.getFromDept() != null
                ? transfer.getFromDept().getDeptName() : "미지정";
        String toDept = transfer.getToDept() != null
                ? transfer.getToDept().getDeptName() : "미지정";
        String detail = fromDept + " → " + toDept;
        if (transfer.getTransferReasonDetail() != null
                && !transfer.getTransferReasonDetail().isBlank()) {
            detail += "\n이관 사유: " + transfer.getTransferReasonDetail();
        }
        return detail;
    }

    private String formatEventDetail(ProcessEvent event) {
        String detail = event.getEventComment();
        String eventCode = event.getEventType() != null
                ? event.getEventType().getEventCode() : null;
        if ("WORK_REJECTED".equals(eventCode) && detail != null && detail.contains("이관")) {
            int automaticRejectionEnd = detail.indexOf("자동 반려");
            if (automaticRejectionEnd >= 0) {
                return detail.substring(0, automaticRejectionEnd + "자동 반려".length());
            }
        }
        return detail;
    }

    private String normalizePhone(String phone) {
        return phone == null ? "" : phone.replaceAll("[^0-9]", "");
    }

    private void validateGuestPhone(String phoneDigits) {
        if (!phoneDigits.matches("01[016789][0-9]{7,8}")) {
            throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다.");
        }
    }

    private ComplaintDetailResponse.AssignmentResponse toAssignmentResponse(ComplaintWork work) {
        // 업무와 업무별 답변 정보를 화면에서 사용하기 쉬운 단일 DTO로 변환한다.
        Reply reply = replyRepository.findByWorkWorkId(work.getWorkId()).orElse(null);
        return ComplaintDetailResponse.AssignmentResponse.builder()
                .workId(work.getWorkId())
                .statusId(work.getStatus() != null ? work.getStatus().getStatusId() : null)
                .deptId(work.getDept() != null ? work.getDept().getDeptId() : null)
                .officerId(work.getOfficer() != null ? work.getOfficer().getAccountId() : null)
                .active("Y".equals(work.getEnabled()))
                .deptName(work.getDept() != null ? work.getDept().getDeptName() : null)
                .officerName(work.getOfficer() != null ? work.getOfficer().getName() : null)
                .officerPhone(work.getOfficer() != null ? work.getOfficer().getPhone() : null)
                .officerPosition(work.getOfficer() != null ? work.getOfficer().getPosition() : null)
                .statusName(work.getStatus() != null ? work.getStatus().getStatusName() : null)
                .replyTitle(reply != null ? reply.getTitle() : null)
                .replyContent(reply != null ? reply.getContent() : null)
                .replyCreatedAt(reply != null ? reply.getCreatedAt() : null)
                .replyDeptName(reply != null
                        && reply.getOfficer() != null
                        && reply.getOfficer().getDept() != null
                                ? reply.getOfficer().getDept().getDeptName()
                                : null)
                .build();
    }

    private String maskName(String name) {
        if (name == null || name.isBlank()) {
            return name;
        }
        if (name.length() <= 2) {
            return name.charAt(0) + "*";
        }
        return name.charAt(0) + "*".repeat(name.length() - 2) + name.charAt(name.length() - 1);
    }
}
