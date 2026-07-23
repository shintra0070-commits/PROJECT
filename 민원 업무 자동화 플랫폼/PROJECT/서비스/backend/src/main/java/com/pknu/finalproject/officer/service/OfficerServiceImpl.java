package com.pknu.finalproject.officer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pknu.finalproject.department.repository.DepartmentRepository;
import com.pknu.finalproject.officer.dto.response.DepartmentResponse;
import com.pknu.finalproject.officer.dto.response.OfficerMeResponse;
import com.pknu.finalproject.officer.dto.response.OfficerSummaryResponse;
import com.pknu.finalproject.officer.entity.Officer;
import com.pknu.finalproject.officer.repository.OfficerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OfficerServiceImpl implements OfficerService {

    private final OfficerRepository officerRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public OfficerMeResponse getMyInfo(Long accountId) {

        Officer officer = officerRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("담당자 정보를 찾을 수 없습니다."));

        boolean isManager = officerRepository.countManagerRoles(accountId) > 0;
        boolean isAdmin = officerRepository.countAdminRole(accountId) > 0;

        return OfficerMeResponse.builder()
                .accountId(officer.getAccountId())
                .name(officer.getName())
                .position(officer.getPosition())
                .deptId(officer.getDept().getDeptId())
                .deptName(officer.getDept().getDeptName())
                .manager(isManager)
                .admin(isAdmin)
                .build();
    }

    @Override
    public List<DepartmentResponse> getDepartments() {
        return departmentRepository.findByEnabledOrderByDeptNameAsc("Y").stream()
                .map(DepartmentResponse::from)
                .toList();
    }

    @Override
    public List<OfficerSummaryResponse> getDeptOfficers(Long accountId) {

        Officer me = officerRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("담당자 정보를 찾을 수 없습니다."));

        return officerRepository.findByDeptDeptId(me.getDept().getDeptId()).stream()
                .map(OfficerSummaryResponse::from)
                .toList();
    }
}
