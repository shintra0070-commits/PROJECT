package com.pknu.finalproject.officer.dto.response;

import java.time.LocalDateTime;

import com.pknu.finalproject.complaint.entity.Complaint;
import com.pknu.finalproject.complaint.entity.ComplaintWork;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OfficerComplaintListResponse {

    private Long complaintId;
    private Long workId;
    private String title;
    private String applicantName;
    private LocalDateTime createdAt;
    private LocalDateTime endedAt;
    private String statusName;
    private String deptName;
    private long transferCount;
    private String reviewReason;
    private String officerName; // 담당자, 미배정이면 null

    public static OfficerComplaintListResponse from(ComplaintWork work) {
        return from(work, 0L);
    }

    public static OfficerComplaintListResponse from(ComplaintWork work, long transferCount) {

        Complaint c = work.getComplaint();

        String applicantName = c.getUser() != null
                ? c.getUser().getName()
                : (c.getGuest() != null ? c.getGuest().getName() : null);

        return OfficerComplaintListResponse.builder()
                .complaintId(c.getComplaintId())
                .workId(work.getWorkId())
                .title(c.getTitle())
                .applicantName(applicantName)
                .createdAt(c.getCreatedAt())
                .endedAt(work.getEndedAt())
                .statusName(work.getStatus().getStatusName())
                .deptName(work.getDept() != null ? work.getDept().getDeptName() : null)
                .transferCount(transferCount)
                .reviewReason(transferCount >= 3 ? "3회 이상 이관" : "활성 분리민원 반려")
                .officerName(work.getOfficer() != null ? work.getOfficer().getName() : null)
                .build();
    }
}
