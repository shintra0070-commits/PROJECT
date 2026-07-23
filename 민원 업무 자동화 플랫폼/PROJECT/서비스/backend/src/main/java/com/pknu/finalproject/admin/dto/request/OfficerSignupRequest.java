package com.pknu.finalproject.admin.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficerSignupRequest {
    private String loginId;
    private String password;
    private String name;
    private String position;
    private String phone;
    private String email;
    private Long deptId;
    private List<Long> roleIds; // ADMIN/MANAGER/OFFICER role_id 목록
}