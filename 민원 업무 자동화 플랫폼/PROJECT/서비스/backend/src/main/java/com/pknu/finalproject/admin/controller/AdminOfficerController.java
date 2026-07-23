package com.pknu.finalproject.admin.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.pknu.finalproject.admin.dto.request.OfficerSignupRequest;
import com.pknu.finalproject.admin.dto.request.OfficerStatusRequest;
import com.pknu.finalproject.admin.dto.response.AdminOfficerListResponse;
import com.pknu.finalproject.admin.service.AdminOfficerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/officers")
@RequiredArgsConstructor
public class AdminOfficerController {

    private final AdminOfficerService adminOfficerService;

    // 공무원 회원가입
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void signup(@RequestBody OfficerSignupRequest request) {
        adminOfficerService.signup(request);
    }

    // 공무원 목록 조회 (부서/활성여부/키워드 필터)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AdminOfficerListResponse> getOfficers(
            @RequestParam(required = false) Long deptId,
            @RequestParam(required = false) String enabled,
            @RequestParam(required = false) String keyword
    ) {
        return adminOfficerService.getOfficers(deptId, enabled, keyword);
    }

    // 공무원 상세 조회
    @GetMapping("/{accountId}")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminOfficerListResponse getOfficer(@PathVariable Long accountId) {
        return adminOfficerService.getOfficer(accountId);
    }

    // 활성화/비활성화
    @PatchMapping("/{accountId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateStatus(
            @PathVariable Long accountId,
            @RequestBody OfficerStatusRequest request
    ) {
        adminOfficerService.updateStatus(accountId, request.getEnabled());
    }
}