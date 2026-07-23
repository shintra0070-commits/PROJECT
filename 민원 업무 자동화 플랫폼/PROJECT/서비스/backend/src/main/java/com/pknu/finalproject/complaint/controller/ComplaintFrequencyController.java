package com.pknu.finalproject.complaint.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pknu.finalproject.complaint.dto.response.ComplaintFrequencyResponse;
import com.pknu.finalproject.complaint.service.ComplaintFrequencyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintFrequencyController {

    private final ComplaintFrequencyService service;

    @GetMapping("/frequency")
    public ResponseEntity<List<ComplaintFrequencyResponse>> getFrequencyList() {
        List<ComplaintFrequencyResponse> list = service.getFrequencyList();
        return ResponseEntity.ok(list);
    }
}
