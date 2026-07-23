package com.pknu.finalproject.user.entity;

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
@Table(name="GUEST")
@Getter
@Setter
@NoArgsConstructor
public class Guest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="GUEST_ID")
    private Long guestId;


    @Column(name="NAME")
    private String name;


    @Column(name="PHONE")
    private String phone;

    @Column(name="CREATED_AT", insertable = false, updatable = false)
    private LocalDateTime createdAt;

}
