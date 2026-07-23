package com.pknu.finalproject.complaint.service;

import com.pknu.finalproject.complaint.dto.response.ComplaintDetailResponse;
import com.pknu.finalproject.complaint.dto.response.PagedComplaintResponse;

/** 사용자와 공무원이 공통으로 사용하는 민원 목록·상세 조회 서비스이다. */
public interface ComplaintService {

    /**
     * 민원 목록을 조회한다. 로그인 회원은 mine 값으로 본인 민원을 구분한다.
     * guestPhone이 전달되면 해당 전화번호로 접수된 비회원 민원만 반환한다.
     */
    PagedComplaintResponse getList(
            Long requesterAccountId,
            String guestPhone,
            int page,
            int size,
            String status,
            boolean mineOnly
    );

    /**
     * 원민원과 첨부파일, 부서별 업무, 처리 이력, 답변을 조회한다.
     * 비공개 민원은 회원 본인, 공무원, 게스트 전화번호 또는 비밀번호로 접근을 검증한다.
     */
    ComplaintDetailResponse getDetail(
            Long complaintId,
            String password,
            String guestPhone,
            Long requesterAccountId
    );

    void delete(Long complaintId, String password, String guestPhone, Long requesterAccountId);
}
