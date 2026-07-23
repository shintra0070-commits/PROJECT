package com.pknu.finalproject.complaint.entity;

import java.time.LocalDateTime;

import com.pknu.finalproject.account.entity.Account;
import com.pknu.finalproject.department.entity.Department;
import com.pknu.finalproject.officer.entity.Officer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "COMPLAINT_WORK")
@Getter
@Setter
@NoArgsConstructor
public class ComplaintWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORK_ID")
    private Long workId;

    @ManyToOne
    @JoinColumn(name = "COMPLAINT_ID")
    private Complaint complaint;

    @ManyToOne
    @JoinColumn(name = "PARENT_WORK_ID")
    private ComplaintWork parentWork;

    @ManyToOne
    @JoinColumn(name = "DEPT_ID")
    private Department dept;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Officer officer;

    @ManyToOne
    @JoinColumn(name = "STATUS_ID")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "WORK_RESULT_CODE_ID")
    private CommonCode workResultCode;

    @Column(name = "WORK_SEQUENCE")
    private Integer workSequence;

    @Column(name = "STARTED_AT", insertable = false, updatable = false)
    private LocalDateTime startedAt;

    @Column(name = "DUE_AT")
    private LocalDateTime dueAt;

    @Column(name = "ENDED_AT")
    private LocalDateTime endedAt;

    @ManyToOne
    @JoinColumn(name = "CREATED_BY_ACCOUNT_ID")
    private Account createdByAccount;

    @Column(name = "ENABLED", length = 1, columnDefinition = "CHAR(1)")
    private String enabled;

    @Column(name = "CREATED_AT", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
