package com.pknu.backend.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pknu.backend.model.Data;
import com.pknu.backend.service.DataService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 사용자로부터 요청을 받을 때 사용(URL 패턴 처리 등)
// 이 클래스가 Controller 클래스라는 것을 정의
@RestController
// http://localhost:8080/member/xx 형태 중에
// -> 8080 뒤에 /member가 있으면 무조건 MemberController 클래스가 캐치하여 사용하겠다는 의미의 어노테이션
// -> /member 뒤에 추가되는 url 패턴은 클래스 내부 메소드에서 캐치하여 사용

// 앞으로 여기서 쓰인 모든 GetMapping은 앞에 /member가 있다고 생각하면 됨
@RequestMapping("/data")

// 최초 서버 실행 시에 미리 클래스들을 생성해 놓기 위한 어노테이션(라이브러리)
//  - 최초 서버 실행 시 final로 선언된 멤버 변수(클래스 변수)들을 자동으로 생성
@RequiredArgsConstructor

@Slf4j
public class DataController {
    private final DataService dataService;

    // 이 클래스에서 사용되는 모든 URL 패턴은 /member로 사용할 예정

    /**
     * 클래스 생성 후 링크 잘 들어오는지 확인
     *  - url 패턴 : http://localhost:8080/member/check
     */
    @GetMapping(path = "/check")
    public String checkLink() {
        return "dataController 링크 상태 정상";
    }

    /**
     * 오라클 DB의 member 테이블에서 모든 정보 조회하는 기능
     * 
     * @return
     */

    @GetMapping(path = "/list")
    public ResponseEntity<List<Data>> getDataList() {

        log.info("DataList() 메소드 호출");
        // Restful API 방식 적용한 리턴(데이터만 반환)
        // 이 코드(ResponseEntity)를 사용하면 Json 포맷으로 변환하여 프론트엔드 서버로 응답함(전달함)
        return ResponseEntity.ok(this.dataService.getDatalist());
    }

    /**
     * 회원 상세 보기 (한 건 조회)
     * 사용할 URL : http://localhost:8080/member/view/a001
     * 상세 조회 할 때는 PK 값이 필요함
     */

    @GetMapping(path = "/view/{data_id}")
    //                                        요청하는 값(파라미터) mem_id라는 PK 값으로 동일하게
    public ResponseEntity<Data> getMemberView(@PathVariable("data_id") String data_id) {

        log.info("회원 상세보기 메소드 호출됨 : mem_id : {}", data_id);

        // return "회원 상세 보기 페이지 : %s".formatted(mem_id);
        return ResponseEntity.ok(this.dataService.getDataView(data_id));
    }

    /**
     * 회원 정보 수정하기
     *  - 요청 URL : http://localhost:8080/member/update_get?mem_id=a001&mem_name=홍길동 -> 수정 받을 데이터를 전송 받았다고 생각하고 진행
     *  - mem_id 값은 PK로 사용, mem_name 값은 수정할 값으로 사용
     */

    // @GetMapping(path = "/update_get")
    // public String setMemberUpdateGet(@RequestParam String data_id, @RequestParam String mem_name) {

    //     // 수정 처리하기
    //     String resMsg = this.dataService.setMemberUpdateGet(mem_id, mem_name);

    //     return "GET 방식으로 데이터 전달 받아서 수정하기 <br/> %s".formatted(resMsg);
    // }

    /**
     * 회원 정보 수정하기
     *  - 요청 URL : http://localhost:8080/member/update -> 수정하는 페이지에서 수정하는 느낌
     *  - mem_id 값은 PK로 사용, mem_name 값은 수정할 값으로 사용
     * <form action='/member/update' method='post'> name이 있는 건 모두 전송됨.
     *     <input type='text' name='mem_id' value='a001'>
     *     <input type='text' name='mem_name' value='홍길동'>
     * </form>
     *    값을 자동으로 받아서 getter, setter가 @RequestBody에 의해서 골라짐.
     */

    // // @PostMapping(path="/update") // 같은 이름에 대해서 알아서 매핑해서 바꿔줌
    // @PutMapping(path = "update")
    // public ResponseEntity<Member> setMemberUpdate(@RequestBody Member p_member) {

    //     // 수정 처리하기
    //     // String resMsg = this.memberService.setMemberUpdateGet(p_member.getMem_id(), p_member.getMem_name());

    //     // 멤버 클래스 그대로 넘겨서 처리
    //     // 신규 POST 방시에서 사용할 Service 메소드 생성
    //     // String resMsg = this.memberService.setMemberUpdate(p_member);

    //     // return "GET 방식으로 데이터 전달 받아서 수정하기 <br/> %s".formatted(resMsg);
    //     log.info("회원정보 수정, mem_id={}, mem_name={}", p_member.getMem_id(), p_member.getMem_name());

    //     return ResponseEntity.ok(this.dataService.setMemberUpdate(p_member));
    // }

    // 제네릭 타입 <?> -> 어떤 리턴타입이 들어가는지 컴파일타임에서 결정되는 경우
    @PostMapping(path = "/insert")
    public ResponseEntity<?> setMemberInsert(@RequestBody Data data) {

        // String resMsg = this.memberService.setMemberInsert(member);

        // return "회원 입력 처리 상태...<br/>"+resMsg;
        log.info("회원 정보 입력", data.getData_id());

        // 반환값 null 처리
        // - React에서 null처리에 대한 응답을 처리하기 위해 응답 상태 코드 값 정의
        if (this.dataService.setDataInsert(data) == null) {
            String errMsg = "회원 아이디가 중복되었습니다.";

            // http 응답 상태 코드 값 409(Conflict)과 메세지로 응답
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errMsg);
        }

        return ResponseEntity.ok(this.dataService.setDataInsert(data));
    }

    /**
     * 회원 삭제하기
     *  - 사용 URL : /member/delete/a002
     */
    // @GetMapping(path="/delete")
    @DeleteMapping(path = "/delete/{data_id}")
    public ResponseEntity<Void> setmemberDelete(@PathVariable("data_id") String data_id) {

        log.info("회원 삭제하기 : data_id={}", data_id);

        // 삭제 처리를 위한 Service 메소드 호출
        this.dataService.setDataDelete(data_id);

        // noContent().build() : 반환할 값이 없다는 것을 명시적으로 의미.
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/list_paging")
    public ResponseEntity<Page<Data>> getMemberListPaging(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        log.info("DataList() 메소드 호출");

        Page<Data> data_list = this.dataService.getDataListPaging(page, size);

        return ResponseEntity.ok(data_list);
    }
}
