package com.pknu.finalproject.admin.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pknu.finalproject.account.entity.Account;
import com.pknu.finalproject.account.repository.AccountRepository;
import com.pknu.finalproject.admin.dto.request.OfficerSignupRequest;
import com.pknu.finalproject.admin.dto.response.AdminOfficerListResponse;
import com.pknu.finalproject.department.entity.Department;
import com.pknu.finalproject.department.repository.DepartmentRepository;
import com.pknu.finalproject.officer.entity.Officer;
import com.pknu.finalproject.officer.entity.OfficerRole;
import com.pknu.finalproject.officer.repository.OfficerRepository;
import com.pknu.finalproject.officer.repository.OfficerRoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminOfficerServiceImpl implements AdminOfficerService {

    private final AccountRepository accountRepository;
    private final OfficerRepository officerRepository;
    private final OfficerRoleRepository officerRoleRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void signup(OfficerSignupRequest request) {

        if (accountRepository.existsByLoginId(request.getLoginId())) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        Department dept = departmentRepository.findById(request.getDeptId())
                .orElseThrow(() -> new RuntimeException("부서를 찾을 수 없습니다."));

        Account account = new Account();
        account.setLoginId(request.getLoginId());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setAccountType("OFFICER");
        account.setEnabled("Y");
        Account savedAccount = accountRepository.save(account);

        Officer officer = new Officer();
        officer.setAccount(savedAccount); // MapsId에 의해 자동으로 ID가 세팅됨
        officer.setDept(dept);
        officer.setName(request.getName());
        officer.setPosition(request.getPosition());
        officer.setPhone(request.getPhone());
        officer.setEmail(request.getEmail());
        officerRepository.save(officer);

        if (request.getRoleIds() != null) {
            request.getRoleIds().forEach(roleId -> {
                OfficerRole or = new OfficerRole();
                or.setAccountId(savedAccount.getAccountId());
                or.setRoleId(roleId);
                officerRoleRepository.save(or);
            });
        }
    }

    @Override
    public List<AdminOfficerListResponse> getOfficers(Long deptId, String enabled, String keyword) {
        // 간단 버전: 전체 조회 후 필터링. 데이터가 많아지면 Repository에 검색 쿼리 추가 권장.
        return officerRepository.findAll().stream()
                .filter(o -> deptId == null || o.getDept().getDeptId().equals(deptId))
                .filter(o -> enabled == null || enabled.isBlank()
                        || o.getAccount().getEnabled().equalsIgnoreCase(enabled))
                .filter(o -> keyword == null || keyword.isBlank()
                        || o.getName().contains(keyword)
                        || o.getAccount().getLoginId().contains(keyword))
                .map(AdminOfficerListResponse::from)
                .toList();
    }

    @Override
    public AdminOfficerListResponse getOfficer(Long accountId) {
        Officer officer = officerRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("담당자 정보를 찾을 수 없습니다."));
        return AdminOfficerListResponse.from(officer);
    }

    @Override
    @Transactional
    public void updateStatus(Long accountId, String enabled) {

        if (!"Y".equals(enabled) && !"N".equals(enabled)) {
            throw new RuntimeException("enabled 값은 Y 또는 N 이어야 합니다.");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("계정을 찾을 수 없습니다."));

        account.setEnabled(enabled);
        accountRepository.save(account);
    }
}
