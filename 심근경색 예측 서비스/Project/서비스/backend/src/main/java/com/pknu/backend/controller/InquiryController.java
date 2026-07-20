package com.pknu.backend.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pknu.backend.model.Inquiry;
import com.pknu.backend.service.InquiryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/inquiry")
@RequiredArgsConstructor
@Slf4j

public class InquiryController {

    private final InquiryService
        inquiryService;

    /**
     * 링크 테스트
    */
    @GetMapping("/check")
    public String checkLink(){

        return "InquiryController 정상";
    }

    /**
     * 전체 조회
    */
    @GetMapping("/list")
    public ResponseEntity<List<Inquiry>>
        getInquiryList(){

        return ResponseEntity.ok(

            this.inquiryService.getInquiryList()
        );
    }

    /**
     * 상세 조회
    */
    @GetMapping("/view/{inq_id}")
    public ResponseEntity<Inquiry>
        getInquiryView(
            @PathVariable Integer inq_id){

            return ResponseEntity.ok(this.inquiryService.getInquiryView(inq_id)
        );
    }

    /**
     * 등록
    */
    @PostMapping("/insert")
    public ResponseEntity<?>
        setInquiryInsert(
            @RequestBody Inquiry inquiry){

        Inquiry insert_inquiry =this.inquiryService.setInquiryInsert(inquiry);

        if(insert_inquiry == null){

            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("문의 등록 실패");
        }

        return ResponseEntity.ok(insert_inquiry);
    }

    /**
     * 수정
    */
    @PutMapping("/update")
    public ResponseEntity<Inquiry>
        setInquiryUpdate(
            @RequestBody Inquiry inquiry){

        return ResponseEntity.ok(

            this.inquiryService.setInquiryUpdate(inquiry)
        );
    }

    /**
     * 삭제
    */
    @DeleteMapping("/delete/{inq_id}")
    public ResponseEntity<Void>
        setInquiryDelete(
            @PathVariable Integer inq_id){

        this.inquiryService.setInquiryDelete(inq_id);

        return ResponseEntity
            .noContent()
            .build();
    }

    /**
     * Paging 조회
    */
    @GetMapping("/list_paging")
    public ResponseEntity<Page<Inquiry>>
        getInquiryListPaging(

            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "5")

            int size,

            @RequestParam(required = false)
            String mem_id
        )
    {
        return ResponseEntity.ok(this.inquiryService.getInquiryListPaging(page, size, mem_id));
    }
}