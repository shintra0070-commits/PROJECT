package com.pknu.finalproject.auth.service;

import com.pknu.finalproject.auth.dto.request.LoginRequest;
import com.pknu.finalproject.auth.dto.response.LoginResponse;


public interface AuthService {


    LoginResponse login(LoginRequest request);


}
