////////// 구글 + 카카오톡 동시 사용 //////////////
import React, { createContext, useContext ,useState } from 'react';

// 서버 전역에서 사용할 수 있는 React Context 객체를 생성
export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

    
    const [user, setUser] = useState(() => {
        const savedUser = localStorage.getItem("user_info");
        return savedUser ? JSON.parse(savedUser) : null;
    });

    // 2. 로그인 처리 함수 확장
    const login = (userInfo) => {
        setUser(userInfo);
        // 브라우저 저장소는 문자열만 지원하므로 객체를 문자열로 직렬화하여 저장
        localStorage.setItem("user_info", JSON.stringify(userInfo));
    };

    // 3. 로그아웃 처리 함수 확장
    const logout = () => {
        setUser(null);
        // 로그아웃 시 저장소 비우기
        localStorage.removeItem("user_info");
    };

    return (
        <AuthContext.Provider value={{ user, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export function useAuth() {
  return useContext(AuthContext);
}


