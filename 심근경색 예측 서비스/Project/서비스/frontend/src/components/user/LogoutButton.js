import React, { useContext } from 'react';
import { AuthContext } from '../../pages/user/AuthContext';

const LogoutButton = () => {
    // 사용자 정보 처리 상태변수
    //  - logout(로그아웃 처리 함수 : 사용자 정보 삭제함)
    //  - user(사용자 정보 데이터)
    const { logout, user } = useContext(AuthContext);

    // 로그아웃 처리 함수
    const handleLogout = () => {
    // Kakao 로그아웃 처리(Kakao 로그아웃 절차를 따라야함)
    if (user.provider === 'kakao' && window.Kakao.Auth.getAccessToken()) {
        window.Kakao.Auth.logout(() => {
        // useContext에서 사용자 정보 삭제(서버 전역에서 사용 가능)
        logout();
        });

    // Google 로그아웃 처리 (구글은 사용자 정보만 삭제하면됨)
    } else {
        // useContext에서 사용자 정보 삭제(서버 전역에서 사용 가능)
        logout();
    }
    };

    // 버튼을 리턴함
    return <button onClick={handleLogout}>로그아웃</button>;
};

export default LogoutButton;







// const LogoutButton = () => {
//   const { logout } = useContext(AuthContext);

//   return (
//     <button onClick={logout} style={{ marginTop: '10px' }}>
//       로그아웃
//     </button>
//   );
// };

// export default LogoutButton;