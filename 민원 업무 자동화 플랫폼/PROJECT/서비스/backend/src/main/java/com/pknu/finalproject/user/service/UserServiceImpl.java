package com.pknu.finalproject.user.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pknu.finalproject.account.entity.Account;
import com.pknu.finalproject.account.repository.AccountRepository;
import com.pknu.finalproject.user.dto.request.ChangePasswordRequest;
import com.pknu.finalproject.user.dto.request.SignupRequest;
import com.pknu.finalproject.user.dto.request.UpdateUserRequest;
import com.pknu.finalproject.user.dto.response.DuplicateCheckResponse;
import com.pknu.finalproject.user.dto.response.SignupResponse;
import com.pknu.finalproject.user.dto.response.UserResponse;
import com.pknu.finalproject.user.entity.UserInfo;
import com.pknu.finalproject.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;



    @Override
    public SignupResponse signup(SignupRequest request) {


        // 로그인 아이디 중복 확인
        if(accountRepository.existsByLoginId(request.getLoginId())){
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }


        // 이메일 중복 확인
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }


        // 전화번호 중복 확인
        if(userRepository.existsByPhone(request.getPhone())){
            throw new RuntimeException("이미 존재하는 전화번호입니다.");
        }



        /*
         * 1. ACCOUNT 생성
         */
        Account account = Account.builder()
                .loginId(request.getLoginId())
                .password(
                    passwordEncoder.encode(request.getPassword())
                )
                .accountType("USER")
                .enabled("Y")
                .build();


        Account savedAccount = accountRepository.save(account);



        /*
         * 2. USER_INFO 생성
         */
        UserInfo user = new UserInfo();

        user.setAccount(savedAccount);

        user.setName(request.getName());

        user.setPhone(request.getPhone());

        user.setEmail(request.getEmail());

        user.setAddress(request.getAddress());

        user.setAddressDetail(request.getAddressDetail());


        userRepository.save(user);



        return new SignupResponse(
                "회원가입이 완료되었습니다."
        );
    }

    @Override
    public void changePassword(Long accountId, ChangePasswordRequest request) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("계정을 찾을 수 없습니다."));

        // 현재 비밀번호 검증
        boolean matches = passwordEncoder.matches(
                request.getCurrentPassword(),
                account.getPassword()
        );

        if (!matches) {
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 새 비밀번호로 변경
        account.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        accountRepository.save(account);
    }

    @Override
    public UserResponse updateMyInfo(Long accountId, UpdateUserRequest request) {

        UserInfo userInfo = userRepository.findByAccountAccountId(accountId);

        if (userInfo == null) {
            throw new RuntimeException("회원 정보를 찾을 수 없습니다.");
        }

        // 이메일 변경 시 중복 체크 (본인 제외)
        if (!userInfo.getEmail().equals(request.getEmail())
                && userRepository.existsByEmailAndAccountAccountIdNot(request.getEmail(), accountId)) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        // 전화번호 변경 시 중복 체크 (본인 제외)
        if (!userInfo.getPhone().equals(request.getPhone())
                && userRepository.existsByPhoneAndAccountAccountIdNot(request.getPhone(), accountId)) {
            throw new RuntimeException("이미 존재하는 전화번호입니다.");
        }

        userInfo.setName(request.getName());
        userInfo.setEmail(request.getEmail());
        userInfo.setPhone(request.getPhone());
        userInfo.setAddress(request.getAddress());
        userInfo.setAddressDetail(request.getAddressDetail());

        return UserResponse.from(userInfo);
    }



    @Override
    public DuplicateCheckResponse checkLoginId(String loginId) {


        return new DuplicateCheckResponse(
                !accountRepository.existsByLoginId(loginId)
        );
    }



    @Override
    public DuplicateCheckResponse checkEmail(String email) {


        return new DuplicateCheckResponse(
                !userRepository.existsByEmail(email)
        );
    }



    @Override
    public UserResponse getMyInfo(Long accountId) {


        UserInfo userInfo =
                userRepository.findByAccountAccountId(accountId);


        return UserResponse.from(userInfo);
    }

}
