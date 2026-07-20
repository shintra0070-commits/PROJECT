package com.pknu.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pknu.backend.model.Member;
import com.pknu.backend.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 해당 클래스가 service 클래스임을 정의하기 위한 어노테이션 정의
@Service

// final로 선언된 멤버 클래스 변수에 대한 클래스 자동 생성시키기
@RequiredArgsConstructor

// 실행 처리 진행중인 로그 남기기(터미널에 출력됨)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> getMemberlist() {

        log.info("회원 전체 목록 조회 Service 클래스 처리 시작...");

        // 조회 결과 Contorller 클래스에서 호출한 메소드에게 반환하기
        return this.memberRepository.findAll(); // -> return 타입 List<T>
        // JpaRepository class에서 findAll 실행되면 table -> member가 알아서 찾아짐
        // 23개의 Day_40428_03_Member 타입의 클래스가 List에 담김
        // 이것을 다시 리턴
    }

    /**
     * 회원 상세 조회(한 건 조회)
     * @param mem_id
     * @return
     */
    public Member getMemberView(String mem_id) {

        // Repository에 한 건 처리 요청 : 
        // -findById(mem_id) : 한 건 요청 시(조건 값 이용)
        Optional<Member> member = this.memberRepository.findById(mem_id);

        // 조회 결과가 있다면 (null이 아니라면)
        if (member.isPresent()) {
            log.info("회원 아이디[%s]에 대한 정보를 정상적으로 조회하였음>>>".formatted(mem_id));

            // Optional 안에 들어있는 실제 Member 클래스 추출하여 리턴하기
            return member.get();
        }

        return null;
        // return member.isPresent() ? member.get() : null ;
    }

    /**
     * GET 방식 수정 처리하기(회원 정보 수정)
     * @param mem_id
     * @param mem_name
     * @return
     */
    public String setMemberUpdateGet(String mem_id, String mem_name) {
        log.info("mem_id=[%s], mem_name=[%s]".formatted(mem_id, mem_name));

        Optional<Member> member = this.memberRepository.findById(mem_id);

        // step 2.
        if (member.isPresent()) {
            // 수정을 위해 사용되는 JPA 메소드 : save(Entity class)
            // 실제 Entity(Member) 클래스 추출 : member.get()

            Member member_update = member.get();
            member_update.setMem_name(mem_name);
            this.memberRepository.save(member_update);

            return "회원 아이디 [" + mem_id + "]에 대한 정보가 수정되었습니다.";
        }

        return "회원 아이디 [%s]에 대한 정보가 존재하지 않습니다.".formatted(mem_id);
    }

    public Member setMemberUpdate(Member p_member) {
        log.info("mem_id=[%s], mem_name=[%s]".formatted(p_member.getMem_id(), p_member.getMem_name()));

        Optional<Member> member = this.memberRepository.findById(p_member.getMem_id());

        // step 2.
        if (member.isPresent()) {
            // 수정을 위해 사용되는 JPA 메소드 : save(Entity class)
            // 실제 Entity(Member) 클래스 추출 : member.get()

            Member member_update = member.get();
            member_update.setMem_name(p_member.getMem_name());
            member_update.setMem_phone(p_member.getMem_phone());
            member_update.setMem_nickname(p_member.getMem_nickname());
            // this.memberRepository.save(member_update);

            // return "회원 아이디 ["+p_member.getMem_id()+"]에 대한 정보가 수정되었습니다.";
            return this.memberRepository.save(member_update);
        }

        // return "회원 아이디 [%s]에 대한 정보가 존재하지 않습니다.".formatted(p_member.getMem_id());
        return null;
    }

    /**
     * 자원 삭제하기
     * @param mem_id
     * @return
     */
    public String setMemberDelete(String mem_id) {

        //  step 1.
        if (this.memberRepository.existsById(mem_id)) {
            // step 2.
            this.memberRepository.deleteById(mem_id);
            return "회원 아이디[%s]에 대한 정보가 삭제되었습니다.".formatted(mem_id);
        }

        // step 3.

        return "회원 아이디[%s]에 대한 삭제할 정보가 존재하지 않습니다.".formatted(mem_id);
    }

    public Member setMemberInsert(Member member) {

        // step 1.
        if (this.memberRepository.existsById(member.getMem_id())) {
            // step 2.
            // return "회원 아이디 [%s]에 대한 데이터가 이미 존재합니다.".formatted(member.getMem_id());

            return null;
        }

        // step 3.
        return this.memberRepository.save(member);

        // return "회원 아이디 [%s] 입력 성공".formatted(member.getMem_id());

    }

    public Page<Member> getMemberListPaging(int page, int size) {

        log.info("회원 전체 목록 조회 Service 클래스 처리 시작...");

        Pageable pageable = PageRequest.of(page, size);

        Page<Member> member_list = this.memberRepository.findAll(pageable);

        return member_list;
    }
}
