package com.pknu.finalproject.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pknu.finalproject.account.entity.Account;
import com.pknu.finalproject.account.repository.AccountRepository;
import com.pknu.finalproject.auth.dto.request.LoginRequest;
import com.pknu.finalproject.auth.dto.response.LoginResponse;
import com.pknu.finalproject.common.jwt.JwtProvider;
import com.pknu.finalproject.officer.repository.OfficerRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final OfficerRepository officerRepository;



    @Override
    public LoginResponse login(LoginRequest request) {


        // 1. loginId로 ACCOUNT 조회
        Account account = accountRepository
                .findByLoginId(request.getLoginId())
                .orElseThrow(
                    () -> new RuntimeException("아이디 또는 비밀번호가 틀렸습니다.")
                );


        // 2. 비밀번호 비교
        boolean passwordMatch =
                passwordEncoder.matches(
                    request.getPassword(),
                    account.getPassword()
                );


        if(!passwordMatch){
            throw new RuntimeException("아이디 또는 비밀번호가 틀렸습니다.");
        }


        String token = jwtProvider.createToken(account);
        boolean isAdmin = false;
        if ("OFFICER".equals(account.getAccountType())) {
            isAdmin = officerRepository.countAdminRole(account.getAccountId()) > 0;
        }
        // 3. 로그인 성공 응답
        return LoginResponse.builder()
                .accountId(account.getAccountId())
                .loginId(account.getLoginId())
                .accountType(account.getAccountType())
                .accessToken(token)
                .admin(isAdmin)
                .build();
    }

}
