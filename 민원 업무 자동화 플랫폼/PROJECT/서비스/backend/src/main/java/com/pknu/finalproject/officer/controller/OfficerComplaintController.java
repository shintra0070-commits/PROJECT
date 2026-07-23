package com.pknu.finalproject.officer.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pknu.finalproject.account.entity.Account;
import com.pknu.finalproject.officer.dto.request.AssignRequest;
import com.pknu.finalproject.officer.dto.request.ReplyRequest;
import com.pknu.finalproject.officer.dto.request.RejectRequest;
import com.pknu.finalproject.officer.dto.request.TransferRequest;
import com.pknu.finalproject.officer.dto.response.OfficerComplaintListResponse;
import com.pknu.finalproject.officer.service.OfficerComplaintService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/officer/complaints")
@RequiredArgsConstructor
public class OfficerComplaintController {

    private final OfficerComplaintService officerComplaintService;

    @GetMapping
    @PreAuthorize("hasRole('OFFICER')")
    public List<OfficerComplaintListResponse> getComplaints(
            Authentication authentication,
            @RequestParam(defaultValue = "received") String tab,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword
    ) {
        Account account = (Account) authentication.getPrincipal();

        return officerComplaintService.getComplaints(
                account.getAccountId(),
                tab,
                startDate,
                endDate,
                status,
                keyword
        );
    }

    // 담당자 배정 (매니저 전용 - 서비스 계층에서 권한 검증)
    @PostMapping("/{workId}/assign")
    @PreAuthorize("hasRole('OFFICER')")
    public void assign(
            Authentication authentication,
            @PathVariable Long workId,
            @RequestBody AssignRequest request
    ) {
        Account account = (Account) authentication.getPrincipal();
        officerComplaintService.assign(workId, account.getAccountId(), request);
    }

    // 부서 이관 (매니저 전용 - 서비스 계층에서 권한 검증)
    @PostMapping("/{workId}/transfer")
    @PreAuthorize("hasRole('OFFICER')")
    public void transfer(
            Authentication authentication,
            @PathVariable Long workId,
            @RequestBody TransferRequest request
    ) {
        Account account = (Account) authentication.getPrincipal();
        officerComplaintService.transfer(workId, account.getAccountId(), request);
    }

    // 답변 등록 (본인 배정 건만 가능 - 서비스 계층에서 권한 검증)
    @PostMapping("/{workId}/reply")
    @PreAuthorize("hasRole('OFFICER')")
    public void reply(
            Authentication authentication,
            @PathVariable Long workId,
            @RequestBody ReplyRequest request
    ) {
        Account account = (Account) authentication.getPrincipal();
        officerComplaintService.reply(workId, account.getAccountId(), request);
    }

    @PostMapping("/{workId}/reject")
    @PreAuthorize("hasRole('OFFICER')")
    public void reject(
            Authentication authentication,
            @PathVariable Long workId,
            @RequestBody RejectRequest request
    ) {
        Account account = (Account) authentication.getPrincipal();
        officerComplaintService.reject(workId, account.getAccountId(), request);
    }

    // 처리중 상태로 변경 (본인 배정 건만 가능 - 서비스 계층에서 권한 검증)
    @PostMapping("/{workId}/start-processing")
    @PreAuthorize("hasRole('OFFICER')")
    public void startProcessing(
            Authentication authentication,
            @PathVariable Long workId
    ) {
        Account account = (Account) authentication.getPrincipal();
        officerComplaintService.startProcessing(workId, account.getAccountId());
    }
}
