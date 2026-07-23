package com.pknu.finalproject.common.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pknu.finalproject.account.entity.Account;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;



@Component
@Slf4j
/** JWT Access Token 생성, 사용자 식별 정보 추출 및 토큰 유효성 검사를 담당한다. */
public class JwtProvider {


    private final SecretKey secretKey;


    private final long expiration;



    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration
    ) {

        this.secretKey =
                Keys.hmacShaKeyFor(secret.getBytes());

        this.expiration = expiration;

    }




    /**
     * JWT 생성
     */
    public String createToken(Account account){


        Date now = new Date();


        return Jwts.builder()

                // 로그인 아이디 저장
                .setSubject(
                    account.getLoginId()
                )


                // 계정 PK 저장
                .claim(
                    "accountId",
                    account.getAccountId()
                )


                // 권한 정보 저장
                .claim(
                    "accountType",
                    account.getAccountType()
                )


                // 생성 시간
                .setIssuedAt(now)


                // 만료 시간
                .setExpiration(
                    new Date(
                        now.getTime() + expiration
                    )
                )


                // 서명
                .signWith(secretKey)


                .compact();

    }





    /**
     * JWT 검증
     */
    public boolean validateToken(String token){


        try {


            Jwts.parserBuilder()

                    .setSigningKey(secretKey)

                    .build()

                    .parseClaimsJws(token);


            return true;


        } catch(Exception e) {


            log.error("JWT 검증 실패 : {}", e.getMessage());


            return false;

        }

    }





    /**
     * JWT에서 loginId 추출
     */
    public String getLoginId(String token){


        Claims claims =
                Jwts.parserBuilder()

                    .setSigningKey(secretKey)

                    .build()

                    .parseClaimsJws(token)

                    .getBody();


        return claims.getSubject();

    }



}
