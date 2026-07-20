// 기본 라이브러리 불러들이기
import React, {useContext, useState} from "react";
import "../../styles/pages/MemberInsertPage.css";
// 링크 페이지 정보 라이브러리 불러들이기
import {useNavigate} from "react-router-dom";

// SpringBoot 백엔드로 저장 요청을 위한 함수가 정의된 Api 불러들이기
import {setMemberInsert} from "../../springApi/memberSpringBootApi";
import MemberForm from "./MemberForm";
import { AuthContext } from "../user/AuthContext";

// 입력 처리를 위한 몸체 정의하기 (함수로 정의)
const MemberInsertPage = () => {
    // 페이지 처리를 위한 네비게이트 정의
    const navigate = useNavigate();
    const { user, login } = useContext(AuthContext);

    // 사용자로부터 입력받는 회원정보를 담을 딕셔너리 상태변수 정의하기
    //  - SpringBoot 백엔드에 저장 요청시 넘겨줄 데이터로 사용
    //  - 서로 다른 서버로 데이터를 전송시 form태그의 action을 이용한 
    //     -> 페이지간 데이터 전송은 불가능 하기에(CORS 보안 규직 위반)
    //     -> 별도로 상태변수에 입력값을 저장하여, SpringBoot 백엔드에 전달하는 방식으로 사용

    /**
     * 입력값 수정 필요
     */
    const [member, setMember] = useState(
        {
            "mem_id" : user.id || user.email || "",
            "mem_name" : user.name || "",
            "mem_phone" : "",
            "mem_nickname" : ""
        }
    );

    // [취소] 버튼에 대한 이벤트 처리함수 정의
    const handleCancel = (e) => {
        // 이전 페이지로 이동 시키기(목록 페이지)
        // - history.go(-1)의 기능
        // - history는 페이지 정보를 담고 있으며, React에서는 navigate가 담당
        navigate(-1);
    };

    // [저장] 버튼 클릭시 form태그의 onSubmit 이벤트 처리함수 정의
    //  - 실제 SpringBoot 백엔드에 데이터를 전송하여 저장 처리를 합니다.
    const handleSubmit = (e) => {
        // form태그를 이용하여 submit 전송버튼 처리시 reload()를 방지하기 위해
        //  - 보통 무조건적으로 정의 후 진행
        e.preventDefault();

        // SpringBoot 백엔드서버로 저장 요청 하기
        // - 저장 요청 함수 호출
        setMemberInsert(member)
            // 응답 결과에 따른 처리
            .then((res) => {                
                login(member); 
                alert(`로그인 및 회원가입 성공: ${member.mem_name}님 환영합니다.`);

                // 입력 후 전체목록 페이지로 이동
                navigate("/");
            })
            // 오류 처리
            .catch((err) => {

                alert(err.response.status);

                if(err.response){
                    // SpringBoot에서 409번 응답을 받은 경우(중복 아이디 발생시)
                    if(err.response.status === 409){
                        alert(err.response.data);

                    }else{
                        alert("서버 통신 오류");
                    }
                }else{
                    // 물리적 오류(네트워크 차단, 서버 종료 등)
                    console.error(">>> 저장 실패 (물리적 오류 발생) : ", err);
                    alert("물리적 오류 발생-잠시 후 다시 사용하세요!");
                }
            });
    };

    /* 입력 박스(input)의 값이 수정되는 경우 이벤트 처리 함수 정의 */
    // - 값이 수정되면 member 내에 각 key의 값을 변경해 주어야 함
    // - submit(저장) 시에 member의 최종 저장된 값이 전송됨
    const handleChange = (e) => {
        // 현재 변경되고 있는 input 박스의 name과 value는 e 변수가 가지고 있음
        // - 현재 입력/수정된 input의 name과 value의 값을 추출하여 변수에 담기
        const {name, value} = e.target;

        // member 내에 key가 name인 부분의 값을 value값으로 변경 처리
        // - prevMember : 이전 member 변수내에 객체 정보(딕셔너리), 수정전의 값을 의미
        //   -> 이름 규칙 : prev상태변수명
        setMember((prevMember) => ({
            // React에서는 특정 위치의 값을 수정하는 함수가 없음
            //  따라서, 기존 원본 데이터를 복제하여 수정한 후 
            //  다시 상태변수에 저장하는 방식을 사용함
            ...prevMember,

            // member 내에 name이라는 key를 찾아서 value의 값을 넣어줌
            [name] : value
        }));
    };


    /* 입력 화면 정의하기 */
    return(
        <div className="register-container">
            <h3>회원 가입하기</h3>

            <p>처음 가입한 경우 회원정보 관리를 위한 추가정보(전화번호, 닉네임)를 </p>
            <p>입력하셔야 서비스 이용이 가능합니다</p>
            <hr/>

            {/* 입력을 위한 공통 페이지(MemberForm.js) 불러들이기 */}
            <MemberForm 
                // member, handleChange, handleCancel, handleSubmit, mode
                member = {member}
                handleChange={handleChange}
                handleCancel={handleCancel}
                handleSubmit={handleSubmit}
                mode="add" />
        </div>
    );

};

// 외부에서 import시에 자동으로 호출되어 사용됨
export default MemberInsertPage;