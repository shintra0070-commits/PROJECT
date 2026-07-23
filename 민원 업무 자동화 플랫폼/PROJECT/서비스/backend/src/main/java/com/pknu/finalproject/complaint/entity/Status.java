package com.pknu.finalproject.complaint.entity;

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
@Table(name = "STATUS")
@Getter
@Setter
@NoArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATUS_ID")
    private Long statusId;

    @Column(name = "STATUS_NAME")
    private String statusName;

    @Column(name = "STATUS_CODE")
    private String statusCode;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Column(name = "ENABLED", length = 1, columnDefinition = "CHAR(1)")
    private String enabled;
}
