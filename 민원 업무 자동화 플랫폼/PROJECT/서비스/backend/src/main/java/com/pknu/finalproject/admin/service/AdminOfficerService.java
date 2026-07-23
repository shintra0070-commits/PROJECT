package com.pknu.finalproject.admin.service;

import java.util.List;

import com.pknu.finalproject.admin.dto.request.OfficerSignupRequest;
import com.pknu.finalproject.admin.dto.response.AdminOfficerListResponse;

/** 관리자 전용 공무원 계정 관리 기능을 정의한다. */
public interface AdminOfficerService {

    /** ACCOUNT, OFFICER, OFFICER_ROLE 정보를 함께 생성한다. */
    void signup(OfficerSignupRequest request);

    /** 부서, 활성 여부, 키워드 조건으로 공무원을 검색한다. */
    List<AdminOfficerListResponse> getOfficers(Long deptId, String enabled, String keyword);

    /** 계정 ID로 공무원 상세 정보를 조회한다. */
    AdminOfficerListResponse getOfficer(Long accountId);

    /** 공무원 계정의 활성 여부를 Y 또는 N으로 변경한다. */
    void updateStatus(Long accountId, String enabled);
}
