package com.pknu.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pknu.backend.model.Member;

@Repository
public interface MemberRepository
        extends JpaRepository<Member, String> {

    // JpaRepository<Day_0428_03_Member, member테이블의 PK 타입>
    // JpaRepository 안에 findAll 같은거 다 있음.

    // 멤버(Day_0428_03_Member)에 정의된 테이블에 접근함 -> 해당 데이터를 조회하고 실제 멤버 변수에 세팅함.
                  
}
