package com.pknu.finalproject.officer.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OfficerMeResponse {

    private Long accountId;
    private String name;
    private String position;
    private Long deptId;
    private String deptName;
    private boolean manager; // true: 배정/이관 권한 있음 (ADMIN 또는 MANAGER)
    private boolean admin;   // true: 공무원 관리 탭 노출 대상 (ADMIN)
}
