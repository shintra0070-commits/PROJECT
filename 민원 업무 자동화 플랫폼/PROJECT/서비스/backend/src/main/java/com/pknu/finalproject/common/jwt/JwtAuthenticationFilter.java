package com.pknu.finalproject.common.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pknu.finalproject.account.entity.Account;
import com.pknu.finalproject.account.repository.AccountRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import com.pknu.finalproject.officer.repository.OfficerRepository;

/**
 * Authorization 헤더의 Bearer 토큰을 검증하고 유효한 계정의 인증 정보를
 * Spring SecurityContext에 한 번 등록하는 요청 필터이다.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtProvider jwtProvider;

    private final AccountRepository accountRepository;

    private final OfficerRepository officerRepository;



    public JwtAuthenticationFilter(
            JwtProvider jwtProvider,
            AccountRepository accountRepository,
            OfficerRepository officerRepository
    ){

        this.jwtProvider = jwtProvider;

        this.accountRepository = accountRepository;

        this.officerRepository = officerRepository;

    }



    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {



        // 1. Authorization Header 가져오기
        String header =
                request.getHeader("Authorization");



        // 2. Bearer 토큰 확인
        if(header != null && header.startsWith("Bearer ")) {


            String token =
                    header.substring(7);



            // 3. JWT 검증
            if(jwtProvider.validateToken(token)){


                String loginId =
                        jwtProvider.getLoginId(token);



                // 4. DB에서 계정 조회
                Account account =
                        accountRepository
                                .findByLoginId(loginId)
                                .orElse(null);



                if(account != null){


                    List<GrantedAuthority> authorities;
                    if ("OFFICER".equals(account.getAccountType())) {
                        boolean isAdmin = officerRepository.countAdminRole(account.getAccountId()) > 0;
                        boolean isManager = officerRepository.countManagerRoles(account.getAccountId()) > 0;
                        if (isAdmin) {
                            authorities = List.of(
                                new SimpleGrantedAuthority("ROLE_OFFICER"),
                                new SimpleGrantedAuthority("ROLE_ADMIN")
                            );
                        } else if (isManager) {
                            authorities = List.of(
                                new SimpleGrantedAuthority("ROLE_OFFICER"),
                                new SimpleGrantedAuthority("ROLE_MANAGER")
                            );
                        } else {
                            authorities = List.of(
                                new SimpleGrantedAuthority("ROLE_OFFICER")
                            );
                        }
                    } else {
                        authorities = List.of(
                            new SimpleGrantedAuthority("ROLE_" + account.getAccountType())
                        );
                    }


                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    account,
                                    null,
                                    authorities
                            );



                    // 5. SecurityContext 저장
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);


                        // ----------------------------- 나중에 이 두개는 지워봐 확인용이라서 나중에
                    System.out.println(
                        "인증 사용자 : "
                        + account.getLoginId()
                    );

                    System.out.println(
                        "권한 : "
                        + authorities
                    );
                }
                

            }

        }


        // 다음 필터 실행
        filterChain.doFilter(
                request,
                response
        );

    }


}
