package com.pknu.finalproject.complaint.entity;

import java.time.LocalDateTime;

import com.pknu.finalproject.account.entity.Account;

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
@Table(name = "PROCESS_EVENT")
@Getter
@Setter
@NoArgsConstructor
public class ProcessEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ID")
    private Long eventId;

    @ManyToOne
    @JoinColumn(name = "WORK_ID")
    private ComplaintWork work;

    @ManyToOne
    @JoinColumn(name = "EVENT_TYPE_ID")
    private EventType eventType;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @Column(name = "EVENT_SEQUENCE")
    private Long eventSequence;

    @Column(name = "EVENT_TIME", insertable = false, updatable = false)
    private LocalDateTime eventTime;

    @Column(name = "PUBLIC_YN", length = 1, columnDefinition = "CHAR(1)")
    private String publicYn;

    @Column(name = "EVENT_COMMENT")
    private String eventComment;

    @Column(name = "CREATED_AT", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
