package com.pknu.finalproject.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pknu.finalproject.account.entity.Account;
import com.pknu.finalproject.user.dto.request.ChangePasswordRequest;
import com.pknu.finalproject.user.dto.request.SignupRequest;
import com.pknu.finalproject.user.dto.request.UpdateUserRequest;
import com.pknu.finalproject.user.dto.response.DuplicateCheckResponse;
import com.pknu.finalproject.user.dto.response.SignupResponse;
import com.pknu.finalproject.user.dto.response.UserResponse;
import com.pknu.finalproject.user.service.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public SignupResponse signup(
            @RequestBody SignupRequest request
    ){

        return userService.signup(request);

    }


    @GetMapping("/check-login-id")
    public DuplicateCheckResponse checkLoginId(
            @RequestParam String loginId
    ){

        return userService.checkLoginId(loginId);

    }


    @GetMapping("/check-email")
    public DuplicateCheckResponse checkEmail(
            @RequestParam String email
    ){

        return userService.checkEmail(email);

    }


    @GetMapping("/me")
    public UserResponse getMyInfo(
            Authentication authentication
    ){

        Account account =
                (Account) authentication.getPrincipal();


        return userService.getMyInfo(
                account.getAccountId()
        );
    }


    @PutMapping("/me")
    public UserResponse updateMyInfo(
            Authentication authentication,
            @RequestBody UpdateUserRequest request
    ){

        Account account = (Account) authentication.getPrincipal();

        return userService.updateMyInfo(
                account.getAccountId(),
                request
        );
    }


    @PutMapping("/me/password")
    public SignupResponse changePassword(
            Authentication authentication,
            @RequestBody ChangePasswordRequest request
    ){

        Account account = (Account) authentication.getPrincipal();

        userService.changePassword(
                account.getAccountId(),
                request
        );

        return new SignupResponse("비밀번호가 변경되었습니다.");
    }
    //지금 여긴 테스트용...
    @GetMapping("/test")
    @PreAuthorize("hasRole('USER')")
    public String userTest(){

        return "USER 접근 성공";
    }

}
