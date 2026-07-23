package com.pknu.finalproject.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pknu.finalproject.account.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // 로그인 ID로 계정 조회
    Optional<Account> findByLoginId(String loginId);

    // 로그인 ID 중복 확인
    boolean existsByLoginId(String loginId);

}