package com.pknu.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 해당 클래스가 테이블과 매핑하는데 사용되는 클래스임을 정의
@Entity

// 실제 사용할 테이블명 정의
@Table(name = "member_test")

// 해당 클래스를 디폴트(default) 생성자로 자동 생성시키도록 정의
@NoArgsConstructor

// 모든 멤버 변수를 매개변수로 받는 생성자를 자동 생성시키도록 정의
/**
 * 생성자 만들어서 this.name = name 하는거 자동으로 해줌.
 * 하나씩 하나씩 행의 개수만큼 생성해서 List에 담음 -> 이걸 Repository에서 알아서 담음
 */
@AllArgsConstructor

// getter setter 정의
@Getter
@Setter

// 모든 getter 메소드의 결과의 반환(return) 타입을 문자열로 정의
@ToString
public class Member {

    // 실제 테이블에서 고유한 값을 가지는 PK를 정의
    @Id
    @Column(name = "mem_id")
    private String mem_id;

    @Column(nullable = false)
    private String mem_phone;

    @Column(nullable = false)
    private String mem_name;

    @Column(nullable = false)
    private String mem_nickname;

    
   
}
