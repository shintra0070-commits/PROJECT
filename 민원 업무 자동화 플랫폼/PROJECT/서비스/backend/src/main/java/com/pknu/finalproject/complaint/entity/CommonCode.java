package com.pknu.finalproject.complaint.entity;

import java.time.LocalDateTime;

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
@Table(name = "COMMON_CODE")
@Getter
@Setter
@NoArgsConstructor
public class CommonCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMON_CODE_ID")
    private Long commonCodeId;

    @ManyToOne
    @JoinColumn(name = "CODE_GROUP_ID")
    private CommonCodeGroup codeGroup;

    @Column(name = "CODE_VALUE")
    private String codeValue;

    @Column(name = "CODE_NAME")
    private String codeName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Column(name = "ENABLED", length = 1, columnDefinition = "CHAR(1)")
    private String enabled;

    @Column(name = "CREATED_AT", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
