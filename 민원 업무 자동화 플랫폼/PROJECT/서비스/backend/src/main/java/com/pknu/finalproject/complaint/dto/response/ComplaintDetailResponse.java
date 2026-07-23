package com.pknu.finalproject.complaint.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.pknu.finalproject.complaint.entity.Attachment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ComplaintDetailResponse {

    private Long complaintId;
    private String title;
    private String content;
    private String refinedContent;
    private LocalDateTime createdAt;
    private boolean isPublic;
    private boolean mine;

    private String applicantName;

    private List<AttachmentResponse> attachments;
    private List<AssignmentResponse> assignments;
    private List<AssignmentResponse> activeAssignments;
    private List<HistoryResponse> histories;
    private boolean answerCompleted;
    private LocalDateTime answerCompletedAt;

    @Getter
    @Builder
    public static class AttachmentResponse {
        private Long fileId;
        private String originalName;
        private String filePath;

        public static AttachmentResponse from(Attachment a) {
            return AttachmentResponse.builder()
                    .fileId(a.getFileId())
                    .originalName(a.getOriginalName())
                    .filePath(a.getFilePath())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class AssignmentResponse {
        private Long workId;
        private Long statusId;
        private Long deptId;
        private Long officerId;
        private boolean active;

        private String deptName;
        private String officerName;
        private String officerPhone;
        private String officerPosition;
        private String statusName;
        private String replyTitle;
        private String replyContent;
        private LocalDateTime replyCreatedAt;
        private String replyDeptName;
    }

    @Getter
    @Builder
    public static class HistoryResponse {
        private String date;
        private String eventCode;
        private String text;
        private String officerName;
        private String officerPhone;
        private String deptName;
        private boolean automaticRejection;
    }
}
