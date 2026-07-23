package com.pknu.finalproject.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pknu.finalproject.user.entity.UserInfo;


@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long>{

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    UserInfo findByAccountAccountId(Long accountId);

    boolean existsByEmailAndAccountAccountIdNot(String email, Long accountId);

    boolean existsByPhoneAndAccountAccountIdNot(String phone, Long accountId);

}