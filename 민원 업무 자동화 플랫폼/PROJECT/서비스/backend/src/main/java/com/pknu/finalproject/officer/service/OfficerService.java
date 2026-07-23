package com.pknu.finalproject.officer.service;

import java.util.List;

import com.pknu.finalproject.officer.dto.response.DepartmentResponse;
import com.pknu.finalproject.officer.dto.response.OfficerMeResponse;
import com.pknu.finalproject.officer.dto.response.OfficerSummaryResponse;

/** 공무원 화면 구성에 필요한 공무원·부서 기준 정보를 제공한다. */
public interface OfficerService {

    /** 로그인 공무원의 소속 부서와 매니저·관리자 여부를 조회한다. */
    OfficerMeResponse getMyInfo(Long accountId);

    /** 이관 대상으로 선택 가능한 전체 부서를 조회한다. */
    List<DepartmentResponse> getDepartments();

    /** 배정 화면에서 선택할 같은 부서 담당자를 조회한다. */
    List<OfficerSummaryResponse> getDeptOfficers(Long accountId);
}
