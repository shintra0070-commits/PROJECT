package com.pknu.backend.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "community_test")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@ToString
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "com_id")
    private Integer com_id;

    private String mem_id;

    private String com_title;
    @Column(columnDefinition = "CLOB")
    private String com_content;

    private Integer com_view;

    private Integer com_like;

    private String com_category;

    @Column(insertable = false, updatable = false)
    private Date com_created;
}