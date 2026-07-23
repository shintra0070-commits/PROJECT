package com.pknu.finalproject.auth.dto.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginRequest {


    private String loginId;


    private String password;

}
