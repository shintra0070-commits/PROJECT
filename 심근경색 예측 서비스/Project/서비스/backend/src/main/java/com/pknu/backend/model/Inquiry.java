package com.pknu.backend.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "INQUIRY_TEST")

@Data
public class Inquiry {

    /**
     * 문의 번호
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "INQ_ID")
    private Integer inq_id;

    /**
     * 작성자
    */
    @Column(name = "MEM_ID")
    private String mem_id;

    /**
     * 문의 제목
    */
    @Column(name = "INQ_TITLE")
    private String inq_title;

    /**
     * 문의 내용
    */
    @Column(name = "INQ_CONTENT")
    private String inq_content;

    /**
     * 작성일
    */
    @Column(name = "INQ_CREATED", insertable = false, updatable=false)
    private Date inq_created;
}