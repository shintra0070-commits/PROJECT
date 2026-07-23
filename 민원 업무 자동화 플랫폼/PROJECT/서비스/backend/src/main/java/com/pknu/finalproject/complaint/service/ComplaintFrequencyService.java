package com.pknu.finalproject.complaint.service;

import com.pknu.finalproject.complaint.dto.response.ComplaintFrequencyResponse;
import java.util.List;

/** 공개 민원과 부서별 답변을 빈도 화면 형식으로 제공한다. */
public interface ComplaintFrequencyService {
    /** 민원별 부서 배정과 답변 목록을 조회한다. */
    List<ComplaintFrequencyResponse> getFrequencyList();
}
