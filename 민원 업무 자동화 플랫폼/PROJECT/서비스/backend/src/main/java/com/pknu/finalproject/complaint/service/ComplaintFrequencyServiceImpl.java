package com.pknu.finalproject.complaint.service;

import com.pknu.finalproject.complaint.dto.response.ComplaintFrequencyResponse;
import com.pknu.finalproject.complaint.entity.ComplaintFrequency;
import com.pknu.finalproject.complaint.repository.ComplaintFrequencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ComplaintFrequencyServiceImpl implements ComplaintFrequencyService {

    private final ComplaintFrequencyRepository repository;

    @Override
    public List<ComplaintFrequencyResponse> getFrequencyList() {
        List<ComplaintFrequency> list = repository
                .findByEnabledOrderByDisplayOrderAscFaqIdAsc("Y");
        
        return list.stream().map(cf -> {
            ComplaintFrequencyResponse res = new ComplaintFrequencyResponse();
            res.setComplaintId(cf.getFaqId());
            res.setTitle(cf.getQuestion());
            res.setContent("자주 찾는 민원 상세 안내 정보입니다.");
            res.setPublic(true);

            // assignments DTO 생성
            ComplaintFrequencyResponse.AssignmentDTO assign = new ComplaintFrequencyResponse.AssignmentDTO();
            assign.setDeptName("민원지원팀");
            assign.setOfficerName("AI 도우미");
            assign.setReplyContent(cf.getAnswer());
            assign.setReplyCreatedAt("2026-07-14T12:00:00");

            List<ComplaintFrequencyResponse.AssignmentDTO> assigns = new ArrayList<>();
            assigns.add(assign);
            res.setAssignments(assigns);

            return res;
        }).collect(Collectors.toList());
    }
}
