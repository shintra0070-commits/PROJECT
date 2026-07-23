package com.pknu.finalproject.officer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pknu.finalproject.officer.entity.Officer;

@Repository
public interface OfficerRepository extends JpaRepository<Officer, Long> {

    boolean existsByAccountId(Long accountId);

    // 같은 부서 소속 담당자 목록 (배정 대상 선택용)
    List<Officer> findByDeptDeptId(Long deptId);

    /**
     * 해당 담당자가 ADMIN 또는 MANAGER 권한(role)을 갖고 있는지 카운트로 확인.
     * OFFICER_ROLE, ROLE 테이블을 직접 조회 (엔티티 매핑 없이 네이티브 쿼리 사용).
     */
    @Query(value = """
            SELECT COUNT(*)
            FROM OFFICER_ROLE orr
            JOIN ROLE r ON orr.ROLE_ID = r.ROLE_ID
            WHERE orr.ACCOUNT_ID = :accountId
              AND r.ROLE_CODE IN ('ADMIN', 'MANAGER')
            """, nativeQuery = true)
    long countManagerRoles(@Param("accountId") Long accountId);

    @Query(value = """
            SELECT COUNT(*)
            FROM OFFICER_ROLE orr
            JOIN ROLE r ON orr.ROLE_ID = r.ROLE_ID
            WHERE orr.ACCOUNT_ID = :accountId
              AND r.ROLE_CODE = 'ADMIN'
            """, nativeQuery = true)
    long countAdminRole(@Param("accountId") Long accountId);
}
