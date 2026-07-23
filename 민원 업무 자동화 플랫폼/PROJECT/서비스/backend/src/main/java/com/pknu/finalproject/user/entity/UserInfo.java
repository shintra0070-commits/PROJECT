package com.pknu.finalproject.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.pknu.finalproject.account.entity.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="USER_INFO")
@Getter
@Setter
@NoArgsConstructor
public class UserInfo {

    @Id
    @Column(name="ACCOUNT_ID")
    private Long accountId;

    @OneToOne
    @MapsId
    @JoinColumn(name="ACCOUNT_ID")
    private Account account;

    @Column(name="NAME")
    private String name;

    @Column(name="PHONE")
    private String phone;

    @Column(name="EMAIL")
    private String email;


    @Column(name="ADDRESS")
    private String address;

    @Column(name="ADDRESS_DETAIL")
    private String addressDetail;

    @CreationTimestamp
    @Column(name="CREATED_AT")
    private LocalDateTime createdAt;

}
