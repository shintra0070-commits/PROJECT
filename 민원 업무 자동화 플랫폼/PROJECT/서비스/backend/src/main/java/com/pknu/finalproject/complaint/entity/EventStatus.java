package com.pknu.finalproject.complaint.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EVENT_STATUS")
@Getter
@Setter
@NoArgsConstructor
public class EventStatus {

    @Id
    @Column(name = "EVENT_ID")
    private Long eventId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "EVENT_ID")
    private ProcessEvent event;

    @ManyToOne
    @JoinColumn(name = "BEFORE_STATUS_ID")
    private Status beforeStatus;

    @ManyToOne
    @JoinColumn(name = "AFTER_STATUS_ID")
    private Status afterStatus;

    @Column(name = "STATUS_REASON")
    private String statusReason;
}
