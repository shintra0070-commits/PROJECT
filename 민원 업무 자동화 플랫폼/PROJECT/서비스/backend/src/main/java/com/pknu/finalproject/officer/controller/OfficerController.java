package com.pknu.finalproject.officer.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pknu.finalproject.account.entity.Account;
import com.pknu.finalproject.officer.dto.response.DepartmentResponse;
import com.pknu.finalproject.officer.dto.response.OfficerMeResponse;
import com.pknu.finalproject.officer.dto.response.OfficerSummaryResponse;
import com.pknu.finalproject.officer.service.OfficerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/officer")
@RequiredArgsConstructor
public class OfficerController {

    private final OfficerService officerService;

    // 로그인한 담당자 정보 (매니저 여부 포함) - 프론트에서 배정/이관 버튼 노출 여부 판단용
    @GetMapping("/me")
    @PreAuthorize("hasRole('OFFICER')")
    public OfficerMeResponse me(Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        return officerService.getMyInfo(account.getAccountId());
    }

    // 이관 대상 부서 목록
    @GetMapping("/departments")
    @PreAuthorize("hasRole('OFFICER')")
    public List<DepartmentResponse> departments() {
        return officerService.getDepartments();
    }

    // 배정 대상 담당자 목록 (같은 부서)
    @GetMapping("/colleagues")
    @PreAuthorize("hasRole('OFFICER')")
    public List<OfficerSummaryResponse> colleagues(Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        return officerService.getDeptOfficers(account.getAccountId());
    }
}
