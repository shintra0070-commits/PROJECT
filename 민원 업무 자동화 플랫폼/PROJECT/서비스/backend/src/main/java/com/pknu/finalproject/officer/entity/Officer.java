package com.pknu.finalproject.officer.entity;

import java.time.LocalDateTime;

import com.pknu.finalproject.account.entity.Account;
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
@Table(name = "OFFICER")
@Getter
@Setter
@NoArgsConstructor
public class Officer {

    @Id
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "DEPT_ID")
    private Department dept;

    @Column(name = "NAME")
    private String name;

    @Column(name = "POSITION_NAME")
    private String position;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CREATED_AT", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
