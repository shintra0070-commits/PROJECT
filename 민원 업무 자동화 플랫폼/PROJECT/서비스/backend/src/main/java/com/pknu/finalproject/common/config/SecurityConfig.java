package com.pknu.finalproject.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pknu.finalproject.account.repository.AccountRepository;
import com.pknu.finalproject.officer.repository.OfficerRepository;
import com.pknu.finalproject.common.jwt.JwtAuthenticationFilter;
import com.pknu.finalproject.common.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.DispatcherType;


@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
/**
 * HTTP 공개 경로와 인증 필요 경로를 정의하고 JWT 인증 필터를 등록한다.
 * 세션 로그인 대신 Bearer 토큰을 사용하므로 폼 로그인, HTTP Basic, CSRF를 비활성화한다.
 */
public class SecurityConfig {



    private final JwtProvider jwtProvider;
    
    private final AccountRepository accountRepository;

    private final OfficerRepository officerRepository;



    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }




    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){


        return new JwtAuthenticationFilter(
                jwtProvider,
                accountRepository,
                officerRepository
        );

    }




    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {



        http

            .csrf(csrf -> csrf.disable())


            .formLogin(form -> form.disable())


            .httpBasic(basic -> basic.disable())


            .authorizeHttpRequests(auth -> auth

                // 내부 오류 처리 경로 허용 -----------------------------------------
                // .dispatcherTypeMatchers(
                //     DispatcherType.ERROR,
                //     DispatcherType.FORWARD
                // ).permitAll()
                //----------------------------------------------- 여기 나중에 꼭 지워야해

                .requestMatchers("/error").permitAll()

                // 회원가입
                .requestMatchers(
                    "/api/users/signup",
                    "/api/users/check-login-id",
                    "/api/users/check-email"
                ).permitAll()



                // 로그인
                .requestMatchers(
                    "/api/auth/login"
                ).permitAll()

                .requestMatchers(
                    "/api/dashboard/today"
                ).permitAll()                

                .requestMatchers(
                    "/api/complaints/**"
                ).permitAll()


                // 인증 필요
                .anyRequest()
                .authenticated()

            )



            // JWT Filter 등록
            .addFilterBefore(
                    jwtAuthenticationFilter(),
                    UsernamePasswordAuthenticationFilter.class
            );



        return http.build();

    }

}
