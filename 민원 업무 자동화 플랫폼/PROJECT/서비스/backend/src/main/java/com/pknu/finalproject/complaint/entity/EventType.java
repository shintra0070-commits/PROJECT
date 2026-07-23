package com.pknu.finalproject.complaint.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EVENT_TYPE")
@Getter
@Setter
@NoArgsConstructor
public class EventType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_TYPE_ID")
    private Long eventTypeId;

    @Column(name = "EVENT_CODE")
    private String eventCode;

    @Column(name = "EVENT_NAME")
    private String eventName;

    @Column(name = "DEFAULT_PUBLIC_YN", length = 1, columnDefinition = "CHAR(1)")
    private String defaultPublicYn;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Column(name = "ENABLED", length = 1, columnDefinition = "CHAR(1)")
    private String enabled;

    @Column(name = "CREATED_AT", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
