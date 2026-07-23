package com.pknu.finalproject.admin.dto.response;

import java.time.LocalDateTime;

import com.pknu.finalproject.officer.entity.Officer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminOfficerListResponse {

    private Long accountId;
    private String loginId;
    private String name;
    private String position;
    private String phone;
    private String email;
    private Long deptId;
    private String deptName;
    private String enabled;
    private LocalDateTime createdAt;

    public static AdminOfficerListResponse from(Officer officer) {
        return AdminOfficerListResponse.builder()
                .accountId(officer.getAccountId())
                .loginId(officer.getAccount().getLoginId())
                .name(officer.getName())
                .position(officer.getPosition())
                .phone(officer.getPhone())
                .email(officer.getEmail())
                .deptId(officer.getDept().getDeptId())
                .deptName(officer.getDept().getDeptName())
                .enabled(officer.getAccount().getEnabled())
                .createdAt(officer.getAccount().getCreatedAt())
                .build();
    }
}
