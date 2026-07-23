package com.pknu.finalproject.user.service;

import com.pknu.finalproject.user.dto.request.ChangePasswordRequest;
import com.pknu.finalproject.user.dto.request.SignupRequest;
import com.pknu.finalproject.user.dto.request.UpdateUserRequest;
import com.pknu.finalproject.user.dto.response.DuplicateCheckResponse;
import com.pknu.finalproject.user.dto.response.SignupResponse;
import com.pknu.finalproject.user.dto.response.UserResponse;


/** 일반 회원의 가입 및 개인정보 관리 기능을 정의한다. */
public interface UserService {

    /** ACCOUNT와 USER_INFO를 하나의 트랜잭션에서 생성한다. */
    SignupResponse signup(SignupRequest request);

    /** 로그인 아이디 사용 가능 여부를 확인한다. */
    DuplicateCheckResponse checkLoginId(String loginId);

    /** 이메일 사용 가능 여부를 확인한다. */
    DuplicateCheckResponse checkEmail(String email);

    /** 인증된 회원의 상세 정보를 조회한다. */
    UserResponse getMyInfo(Long accountId);

    /** 현재 비밀번호 확인 후 암호화된 새 비밀번호를 저장한다. */
    void changePassword(Long accountId, ChangePasswordRequest request);

    /** 이메일·전화번호 중복을 검증한 뒤 회원 정보를 수정한다. */
    UserResponse updateMyInfo(Long accountId, UpdateUserRequest request);

}
