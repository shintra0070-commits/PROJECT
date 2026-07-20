package com.pknu.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 클라이언트의 요청과 서버의 응답을 처리하는 시작점 클래스
 *  - 시작 클래스는 여러 개 클래스가 될 수 있으며,
 *  - 요청 url 패턴에 맞는 설정이 있는 클래스가 시작점이 됨.
 * 
 *  -> RestController가 시작 클래스를 정의 함.
 */

@RestController
public class Index {

    /**
     * 요청 URL 패턴 : /index
     *  - 메소드명 : root
     *  - 반환할 처리 결과 : "<h1>스프링부트 메인 인덱스 페이지 URL 패턴</h1>"
     */
    // @GetMapping(path="/index")
    // public String root(){
    //     // return "<h1 style={'text-align':'center'}>스프링부트 메인 인덱스 페이지 URL 패턴</h1>";
    //     return "<h1>스프링부트 메인 인덱스 페이지 URL 패턴</h1>";
    // }

    /**
     * 요청 URL 패턴 : /
     *  - 다론 곳에서 / 패턴을 만들어 놓은 곳은 /test/index로 수정하기
     *  - 메소드명 : root2
     *  - 반환할 처리 결과 : "<h1>스프링부트 메인 인덱스 메인 페이지 root2 메소드 호출</h1>"
     */

    @GetMapping(path = "/root2")
    public String root2() {
        return "<h1>스프링부트 메인 인덱스 메인 페이지 root2 메소드 호출</h1>";
    }

    /**
     * 메인 페이지 URL 패턴 여러개 정의하기
     * : /, /index, /index.html, /index.htm, /index.jsp, /index.php
     */

    @GetMapping(path = { "/", "/index", "/index.html", "/index.htm", "/index.jsp", "/index.php" })
    public String root(String name) {
        // return "<h3>메인 페이지 입니다.</h3> <hr/>";
        // 테이블 태그 정의
        //  - 여러 행단위 문자열을 작성하기에 불편함이 있음
        //  - StringBuffer : 행단위로 버퍼 메모리(캐시 메모리)에 저장해 놓고 사용하는 문자열 객체
        StringBuffer sb = new StringBuffer();
        sb.append("<style> a{ text-decoration : none; </style>");
        sb.append("<h1>스프링부트 메인 페이지 입니다.</h1>");
        sb.append("<hr/>");
        sb.append("<table border=1 width = 100%>");
        sb.append("<tr><th>제 목</th> <th>바로가기 링크</th> </tr>");
        sb.append("<tr><td>회원 목록 바로가기</td> <td><a href='/member/list'>회원 전체 목록 페이지</a></td><tr/>");
        sb.append("<tr><td>데이터 목록 바로가기</td> <td><a href='/data/list'>데이터 전체 목록 페이지</a></td><tr/>");
        sb.append("<tr><td>예측모델 조회</td> <td><a href='/modeldata'>예측모델 조회</a></td><tr/>");
        sb.append("</table>");

        // 버퍼(캐시) 메모리에 저장된 문자열을 하나의 전체 문자열로 반환
        return sb.toString();
    }

    /**
     * URL 패스 경로 뒤에 숫자를 넣어서 파라미터(값 전달)로 사용
     *  - 경로 뒤에 숫자도 패턴으로 인식됨
     *  - @PathVariable : URL 경로 뒤의 숫자를 추출하는 어노테이션(라이브러리)
     */

    @GetMapping(path = "/path_variable/{id}")
    public String pathVariable(@PathVariable int id) {
        return "URL 뒤에 있는 숫자 파라미터 값 : %d".formatted(id);
    }

    /**
     * - 일반적으로 사용자가 요청 시 전송되는 데이터 형태
        - URL 패턴 : /param?mem_id='a001'
        - @RequestParam : URL 물음표(?) 뒤에 있는 부분을 파라미터(parameter)라고 칭함
                        : 파라미터는 보통 key=value 형태로 서버로 전송 됨.
                        : @RequestParam은 전송받은 파라미터의 key 이름을 이용해서
                          값을 추출하는 어노테이션(파라미터)
                        : key와 매개변수명은 같아야 함.
     */

    @GetMapping(path = "/param")
    public String paramVariable(@RequestParam String mem_id) {
        return "key = value 형태의 파라미터 받아서 처리 : mem_id = %s".formatted(mem_id);
    }

    /**
     * URL 물음표(?) 뒤에 파라미터가 여러 개인 경우 값 추출하기
     *  - URL : /param2?mem_id=a001&mem_name=홍길동
     *  - @Requestparam 어노테이션이 파라미터 개수만큼 사용됨
     */

    @GetMapping(path = "/param2")
    public String paramVariable2(@RequestParam String mem_id,
            @RequestParam String mem_name) {
        return "아이디 : %s, 이름 : %s".formatted(mem_id, mem_name);
    }
}
