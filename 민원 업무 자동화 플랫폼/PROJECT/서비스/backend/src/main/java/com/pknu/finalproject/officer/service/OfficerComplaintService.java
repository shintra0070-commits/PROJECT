package com.pknu.finalproject.officer.service;

import java.time.LocalDate;
import java.util.List;

import com.pknu.finalproject.officer.dto.request.AssignRequest;
import com.pknu.finalproject.officer.dto.request.ReplyRequest;
import com.pknu.finalproject.officer.dto.request.RejectRequest;
import com.pknu.finalproject.officer.dto.request.TransferRequest;
import com.pknu.finalproject.officer.dto.response.OfficerComplaintListResponse;

/** 공무원의 민원 처리 상태를 변경하는 핵심 업무 서비스이다. */
public interface OfficerComplaintService {

    /**
     * 탭과 검색 조건에 맞는 업무를 조회한다.
     * received는 소속 부서, assigned는 현재 담당자, returned는 반송 조건을 기준으로 한다.
     */
    List<OfficerComplaintListResponse> getComplaints(
            Long accountId,
            String tab,
            LocalDate startDate,
            LocalDate endDate,
            String status,
            String keyword
    );

    /** 같은 부서의 담당자에게 업무를 배정한다. 매니저 권한이 필요하다. */
    void assign(Long workId, Long requesterAccountId, AssignRequest request);

    /** 기존 업무를 종료하고 대상 부서에 부모 업무가 연결된 후속 업무를 생성한다. */
    void transfer(Long workId, Long requesterAccountId, TransferRequest request);

    /** 제목과 내용이 포함된 답변을 저장하고 업무 상태를 답변완료로 변경한다. */
    void reply(Long workId, Long requesterAccountId, ReplyRequest request);

    /** 담당 업무를 사유와 함께 반려하고 상태 변경 이벤트를 기록한다. */
    void reject(Long workId, Long requesterAccountId, RejectRequest request);

    /** 현재 담당자가 담당자배정 상태의 업무를 처리중으로 변경한다. */
    void startProcessing(Long workId, Long requesterAccountId);
}
