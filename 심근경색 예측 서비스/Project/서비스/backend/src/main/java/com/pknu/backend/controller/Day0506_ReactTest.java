package com.pknu.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/react")
@Slf4j
public class Day0506_ReactTest {

    /**
     * React 에서 Springboot응답 확인하는 메소드
     */
    @GetMapping(path = "/springboot_test")
    public ResponseEntity<String> getReactSpringBootTest() {

        log.info("연결됨");
        return ResponseEntity.ok("React에서 SpringBoot로 연결이 성공되었습니다.");
    }
}
