package com.pknu.finalproject.complaint.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComplaintFrequencyResponse {
    private Long complaintId;
    private String title;
    private String content;
    private boolean isPublic = true;
    private List<AssignmentDTO> assignments;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class AssignmentDTO {
        private String deptName;
        private String officerName;
        private String replyContent;
        private String replyCreatedAt;
    }
}
