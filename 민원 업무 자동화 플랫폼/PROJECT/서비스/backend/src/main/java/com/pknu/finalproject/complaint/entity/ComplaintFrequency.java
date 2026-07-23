package com.pknu.finalproject.complaint.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "COMPLAINT_FREQUENCY")
@Getter
@Setter
@NoArgsConstructor
public class ComplaintFrequency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAQ_ID")
    private Long faqId;

    @Column(name = "QUESTION", length = 500)
    private String question;

    @Lob
    @Column(name = "ANSWER")
    private String answer;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Column(name = "ENABLED", length = 1, columnDefinition = "CHAR(1)")
    private String enabled;

    @Column(name = "CREATED_AT", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
