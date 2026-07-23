package com.pknu.finalproject.officer.dto.response;

import com.pknu.finalproject.department.entity.Department;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DepartmentResponse {
    private Long deptId;
    private String deptName;

    public static DepartmentResponse from(Department d) {
        return DepartmentResponse.builder()
                .deptId(d.getDeptId())
                .deptName(d.getDeptName())
                .build();
    }
}
