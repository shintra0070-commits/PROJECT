package com.pknu.finalproject.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {


    // ACCOUNT
    private String loginId;

    private String password;


    // USER_INFO
    private String name;

    private String phone;

    private String email;

    private String address;

    private String addressDetail;

}
