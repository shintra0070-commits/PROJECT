// KakaoLoginButton.jsx
import React, { useEffect, useContext } from 'react';

import { AuthContext } from '../../pages/user/AuthContext';
import { useNavigate } from 'react-router-dom';

const KakaoLoginButton = () => {
    const { login } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        if (!window.Kakao.isInitialized()) {
        // JavaScript 키 정확히 입력
        window.Kakao.init("660bc6ef94287418e7c9ec1b72253acb"); 
        }
    }, []);

    const loginWithKakao = (navigate) => {

        window.Kakao.Auth.login({
            success: (authObj) => {
                console.log("Kakao 로그인 성공:", authObj);

                // 사용자 정보 요청
                window.Kakao.API.request({
                    // 카카오 서버에서 
                    url: "/v2/user/me",
                    // 로그인 인증 성공인 경우 처리
                    success: (res) => {
                        console.log("사용자 정보 응답:", res);

                        const kakaoAccount = res.kakao_account || {};
                        const profile = kakaoAccount.profile || {}; // 사용자 정보가 포함되어 있음
                        const email = kakaoAccount.email || "이메일 없음";
                        const nickname = profile.nickname || "이름 없음";
                        const profileimg = profile.profile_image_url || "";

                        // useContext에 사용자 정보 저장(서버 전역에서 사용 가능)
                        const login_data = {
                                            provider: "kakao",
                                            id: res.id,
                                            name: nickname  || "이름 없음",
                                            email: email || "이메일 없음",
                                            profile_image: profileimg || "이미지 없음"

                                        };
                        // - 브라우저에서 F12 > console 탭에서 확인
                        console.table(login_data);
                        login(login_data);

                        alert(`로그인 성공: ${nickname} (${email})`);

                        navigate("/member/insert");

                    },
                    fail: (error) => {
                        console.error("사용자 정보 요청 실패", error);
                    },
                });
            },
            fail: (err) => {
                console.error("Kakao 로그인 실패:", err);
            },

        });
    };

// 버튼을 리턴함
return (
    <div>
        {/* public폴더는 정적파일의 root(/) 시작 위치임 */}
        <button onClick={() => loginWithKakao(navigate)}
                style={{
                width: '100%',        // 화면 너비에 꽉 차게 설정
                padding: '5px 0',    // 버튼 내부 위아래 여백 (높이 확보)
                margin: '10px 0',     // 버튼 외부 위아래 간격 (다른 요소와 겹침 방지)
                
                // 디자인 유지를 위한 기본 추가 속성 (필요시 생략 가능)
                backgroundColor: '#FEE500', 
                border: 'none', 
                borderRadius: '6px',
                fontWeight: 'bold',
                cursor: 'pointer'
                }}>
                {/* React 로컬서버 내 디렉토리 root 경로는 Public 부터 시작(정적 파일의 위치 경로) */}
                <img src="/img/02_kakao_login_btn.png" width="120" alt="카카오 로그인 버튼"/>
        </button>
    </div>
);
};

export default KakaoLoginButton;
