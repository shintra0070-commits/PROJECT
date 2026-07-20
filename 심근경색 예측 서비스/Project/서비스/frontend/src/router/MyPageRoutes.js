import {Routes, Route } from 'react-router-dom';
import MyPage from '../pages/member/MyPage';

// 마이페이지는 로그인한 사용자만 접근 가능
function MyPageRoutes() {
  return (
    <Routes>
      <Route path="/mypage" element={<MyPage />}/>
    </Routes>
  );
}

export default MyPageRoutes;
