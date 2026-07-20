package com.pknu.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pknu.backend.model.Inquiry;
import com.pknu.backend.repository.InquiryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j

public class InquiryService {

    private final InquiryRepository inquiryRepository;

    /**
     * 전체 조회
    */
    public List<Inquiry>
        getInquiryList(){

        log.info(
            "문의 전체 조회"
        );

        return this.inquiryRepository
            .findAll();
    }

    /**
     * 상세 조회
    */
    public Inquiry getInquiryView(
            Integer inq_id){

        Optional<Inquiry> inquiry =this.inquiryRepository.findById(inq_id);

        if(inquiry.isPresent()){

            return inquiry.get();
        }

        return null;
    }

    /**
     * 등록
    */
    public Inquiry setInquiryInsert(
            Inquiry inquiry){

        return this.inquiryRepository
            .save(inquiry);
    }

    /**
     * 수정
    */
    public Inquiry setInquiryUpdate(
            Inquiry p_inquiry){

        Optional<Inquiry> inquiry =
            this.inquiryRepository
                .findById(
                    p_inquiry.getInq_id()
                );

        if(inquiry.isPresent()){

            Inquiry inquiry_update =
                inquiry.get();

            inquiry_update.setInq_title(
                p_inquiry.getInq_title()
            );

            inquiry_update.setInq_content(
                p_inquiry.getInq_content()
            );

            return this.inquiryRepository
                .save(inquiry_update);
        }

        return null;
    }

    /**
     * 삭제
    */
    public String setInquiryDelete(
            Integer inq_id){

        if(this.inquiryRepository.existsById(inq_id)){

            this.inquiryRepository.deleteById(inq_id);

            return "문의 삭제 성공";
        }

        return "삭제할 문의 없음";
    }

    /**
     * 문의사항 Paging 조회
    */
    public Page<Inquiry>
        getInquiryListPaging(

            int page,
            int size,
            String mem_id
        )

    {log.info( "문의사항 Paging 조회");

        Pageable pageable = PageRequest.of(page,size);

        // 내 문의사항 조회
        if(mem_id != null){

            return this.inquiryRepository.findByMemId(
                    mem_id,
                    pageable
                );
        }

        // 전체 조회
        return this.inquiryRepository.findAll(pageable);
    }
}