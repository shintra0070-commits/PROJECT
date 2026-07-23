package com.pknu.finalproject.complaint.entity;

import com.pknu.finalproject.department.entity.Department;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EVENT_TRANSFER")
@Getter
@Setter
@NoArgsConstructor
public class EventTransfer {

    @Id
    @Column(name = "EVENT_ID")
    private Long eventId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "EVENT_ID")
    private ProcessEvent event;

    @ManyToOne
    @JoinColumn(name = "FROM_DEPT_ID")
    private Department fromDept;

    @ManyToOne
    @JoinColumn(name = "TO_DEPT_ID")
    private Department toDept;

    @ManyToOne
    @JoinColumn(name = "TRANSFER_REASON_CODE_ID")
    private CommonCode transferReasonCode;

    @Column(name = "TRANSFER_REASON_DETAIL")
    private String transferReasonDetail;

    @ManyToOne
    @JoinColumn(name = "NEW_WORK_ID")
    private ComplaintWork newWork;
}
