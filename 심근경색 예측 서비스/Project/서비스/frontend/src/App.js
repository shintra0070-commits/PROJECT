import './styles/common.css';
import './styles/responsive.css';

import React from 'react';
import {BrowserRouter} from "react-router-dom";

import Navbar from './components/layout/Navbar';
import Footer from './components/layout/Footer';

import HomeRoutes from './router/HomeRoutes';
import AuthRoutes from './router/AuthRoutes';
import AnalysisRoutes from './router/AnalysisRoutes';
import CommunityRoutes from './router/CommunityRoutes';
import MyPageRoutes from './router/MyPageRoutes';
import PolicyRoutes from './router/PolicyRoutes';
import { GoogleOAuthProvider } from '@react-oauth/google';
import { AuthProvider } from './pages/user/AuthContext';


const Home = () =>  {
    return (
        <div className="app">
            <BrowserRouter>
                
                {/* 상단 Header */}
                <Navbar />
                {/* Home 페이지*/}
                <HomeRoutes />
                {/* goole 인증 페이지*/}
                <AuthRoutes />
                {/* 데이터 입력 페이지 */}
                <AnalysisRoutes />
                {/* 커뮤니티 페이지 */}
                <CommunityRoutes />
                {/* 마이페이지 */}
                <MyPageRoutes />
                {/* footer 관련 페이지 */}
                <PolicyRoutes />
                <Footer />
            </BrowserRouter>
        </div>
    );
}

const App = () => {
    return (

        <>
            {/* 구글 인증을 위햇 전체 애플리케이션을 라우터로 감싸기
          - 클라이언트 ID 넣기 */}
            <GoogleOAuthProvider clientId="367426272482-ka4k6k25jnd11jhism95uocjtndnnc3v.apps.googleusercontent.com">
        
                {/* AuthContext.js에서 구현한 -> 사용자 정의 상태 관리 훅(Hoc) */}
                {/* <AuthProvider> */}
                
                    {/* AuthContext.js에서 children(하위 컴포넌트)으로 처리됨
                        하위 컴포넌트에서는 로그인 사용자 정보를 공유 받아서 사용 가능 */}
                    <Home />

                {/* </AuthProvider> */}

            </GoogleOAuthProvider>
        </>

        
    );
};

export default App;
