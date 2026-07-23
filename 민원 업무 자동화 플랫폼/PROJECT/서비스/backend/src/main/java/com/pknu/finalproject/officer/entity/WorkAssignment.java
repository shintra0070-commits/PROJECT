package com.pknu.finalproject.officer.entity;

import java.time.LocalDateTime;

import com.pknu.finalproject.account.entity.Account;
import com.pknu.finalproject.complaint.entity.CommonCode;
import com.pknu.finalproject.complaint.entity.ComplaintWork;

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
@Table(name = "WORK_ASSIGNMENT")
@Getter
@Setter
@NoArgsConstructor
public class WorkAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSIGNMENT_ID")
    private Long assignmentId;

    @ManyToOne
    @JoinColumn(name = "WORK_ID")
    private ComplaintWork work;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Officer officer;

    @ManyToOne
    @JoinColumn(name = "ASSIGNED_BY_ACCOUNT_ID")
    private Account assignedByAccount;

    @ManyToOne
    @JoinColumn(name = "ASSIGN_REASON_CODE_ID")
    private CommonCode assignReasonCode;

    @Column(name = "ASSIGNED_AT", insertable = false, updatable = false)
    private LocalDateTime assignedAt;

    @Column(name = "UNASSIGNED_AT")
    private LocalDateTime unassignedAt;

    @ManyToOne
    @JoinColumn(name = "UNASSIGN_REASON_CODE_ID")
    private CommonCode unassignReasonCode;

    @Column(name = "MEMO")
    private String memo;
}
