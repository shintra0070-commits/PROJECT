import React, { useContext } from 'react';
import { GoogleLogin } from '@react-oauth/google';
import { AuthContext } from '../../pages/user/AuthContext';
import { useNavigate } from 'react-router-dom';
import { getMemberView } from '../../springApi/memberSpringBootApi';


const GoogleLoginButton = () => {
    
    const { login } = useContext(AuthContext);
    const navigate = useNavigate();

    // 사용자가 정의한 버튼을 리턴함
    return (
      <GoogleLogin
        onSuccess={async (credentialResponse) => {
            console.log("Google 로그인 성공:");

            const token = credentialResponse.credential;

            // JWT Payload UTF-8 디코딩 (한글 깨짐 방지 처리)
            const base64Url = token.split('.')[1];
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const binary = atob(base64);
            const bytes = new Uint8Array([...binary].map((char) => char.charCodeAt(0)));
            // UTF-8 적용
            const decoded = JSON.parse(new TextDecoder().decode(bytes)); 

            // useContext에 사용자 정보 저장(서버 전역에서 사용 가능) 
            // - 브라우저에서 F12 > console 탭에서 확인
            console.table(decoded);
            login(decoded);

            // 컨텍스트에 전송할 규격으로 데이터 가공
            const login_data = {
            mem_name: decoded.name || "이름 없음",
            mem_id: decoded.email || "이메일 없음",
            };

            

        try {
            // 1. Spring Boot 백엔드 서버에 구글 고유 ID(decoded.sub)를 전송하여 회원 등록 여부 조회
            // (getMemberByGoogleId 함수는 Axios 등으로 구현된 백엔드 통신 함수)
            const response = await getMemberView(decoded.email);
            
            if (response.data) {
                // DB에 정보가 있으면 로그인 시켜주고 
                login(login_data); 
                alert(`로그인 성공: ${login_data.mem_name}님 환영합니다.`);
                navigate("/"); 
            } 
            
            else {
                // DB에 정보가 없으면 가입 페이지로 이동
                alert("등록된 회원 정보가 없어 회원가입 페이지로 이동합니다.");
                navigate("/signup");
            }
        } 
        
        catch (error) {
            console.error("백엔드 구글 회원 확인 중 오류 발생:", error);
            alert("서버 연결 실패로 인해 로그인 인증을 완료할 수 없습니다.");
        }

        }}

        onError={() => {
            console.log('Google 로그인 실패');
        }}

        />
    );
};

export default GoogleLoginButton;