package com.pknu.finalproject.auth.dto.response;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class LoginResponse {

    private Long accountId;

    private String loginId;

    private String accountType;

    private String accessToken;

    private boolean admin;

}