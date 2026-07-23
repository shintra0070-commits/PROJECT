package com.pknu.finalproject.user.dto.response;

import java.time.LocalDateTime;

import com.pknu.finalproject.user.entity.UserInfo;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UserResponse {


    private Long accountId;


    private String loginId;


    private String name;


    private String phone;


    private String email;


    private String address;


    private String addressDetail;

    private LocalDateTime createdAt;



    public static UserResponse from(UserInfo userInfo){


        return UserResponse.builder()

                .accountId(
                        userInfo.getAccount().getAccountId()
                )

                .loginId(
                        userInfo.getAccount().getLoginId()
                )

                .name(
                        userInfo.getName()
                )

                .phone(
                        userInfo.getPhone()
                )

                .email(
                        userInfo.getEmail()
                )

                .address(
                        userInfo.getAddress()
                )

                .addressDetail(
                        userInfo.getAddressDetail()
                )

                .createdAt(
                        userInfo.getCreatedAt()
                )

                .build();
    }

}