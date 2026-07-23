package com.pknu.finalproject.complaint.entity;

import java.time.LocalDateTime;

import com.pknu.finalproject.user.entity.Guest;
import com.pknu.finalproject.user.entity.UserInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "COMPLAINT")
@Getter
@Setter
@NoArgsConstructor
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPLAINT_ID")
    private Long complaintId;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private UserInfo user;

    @ManyToOne
    @JoinColumn(name = "GUEST_ID")
    private Guest guest;

    @Column(name = "TITLE")
    private String title;

    // 비공개 민원 상세 조회용 비밀번호 (회원/비회원 공통, nullable)
    @Column(name = "PASSWORD")
    private String password;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    @Lob
    @Column(name = "REFINED_CONTENT")
    private String refinedContent;

    @Column(name = "IS_PUBLIC", length = 1, columnDefinition = "CHAR(1)")
    private String isPublic;

    @Column(name = "CREATED_AT", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
