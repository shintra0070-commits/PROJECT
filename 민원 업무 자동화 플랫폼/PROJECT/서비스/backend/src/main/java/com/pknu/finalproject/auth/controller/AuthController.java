package com.pknu.finalproject.auth.controller;

import org.springframework.web.bind.annotation.*;

import com.pknu.finalproject.auth.dto.request.LoginRequest;
import com.pknu.finalproject.auth.dto.response.LoginResponse;
import com.pknu.finalproject.auth.service.AuthService;


import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;



    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request
    ){

        return authService.login(request);

    }

}
