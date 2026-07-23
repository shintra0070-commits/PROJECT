package com.pknu.finalproject.complaint.dto.response;

import com.pknu.finalproject.complaint.entity.Complaint;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ComplaintListResponse {

    private Long complaintId;
    private String title;
    private LocalDateTime createdAt;
    private boolean isPublic;
    private boolean mine;
    private String status;

    public static ComplaintListResponse from(Complaint complaint, Long requesterAccountId) {

        return from(complaint, requesterAccountId, false, "접수");
    }

    public static ComplaintListResponse from(
            Complaint complaint,
            Long requesterAccountId,
            boolean guestVerified,
            String status
    ) {

        boolean mine = guestVerified || (requesterAccountId != null
                && complaint.getUser() != null
                && requesterAccountId.equals(complaint.getUser().getAccountId()));

        return ComplaintListResponse.builder()
                .complaintId(complaint.getComplaintId())
                .title(complaint.getTitle())
                .createdAt(complaint.getCreatedAt())
                .isPublic("Y".equals(complaint.getIsPublic()))
                .mine(mine)
                .status(status)
                .build();
    }
}
