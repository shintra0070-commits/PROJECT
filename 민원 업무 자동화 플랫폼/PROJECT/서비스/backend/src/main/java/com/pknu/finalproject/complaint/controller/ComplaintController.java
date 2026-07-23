package com.pknu.finalproject.complaint.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pknu.finalproject.account.entity.Account;
import com.pknu.finalproject.complaint.dto.response.ComplaintDetailResponse;
import com.pknu.finalproject.complaint.dto.response.PagedComplaintResponse;
import com.pknu.finalproject.complaint.service.ComplaintService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @GetMapping
    public PagedComplaintResponse getList(
            @RequestParam(required = false) String guestPhone,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "false") boolean mineOnly,
            Authentication authentication
    ) {
        Long accountId = resolveAccountId(authentication);
        return complaintService.getList(accountId, guestPhone, page, size, status, mineOnly);
    }

    @GetMapping("/{id}")
    public ComplaintDetailResponse getDetail(
            @PathVariable Long id,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String guestPhone,
            Authentication authentication
    ) {
        Long accountId = resolveAccountId(authentication);
        return complaintService.getDetail(id, password, guestPhone, accountId);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String guestPhone,
            Authentication authentication
    ) {
        complaintService.delete(id, password, guestPhone, resolveAccountId(authentication));
    }

    private Long resolveAccountId(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Account)) {
            return null;
        }
        return ((Account) authentication.getPrincipal()).getAccountId();
    }
}
