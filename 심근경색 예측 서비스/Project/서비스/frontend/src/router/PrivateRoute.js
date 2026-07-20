import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

// 로그인한 사용자만 접근 가능한 페이지를 감싸는 라우터 컴포넌트
function PrivateRoute({ children }) {
  const { user } = useAuth();

  // 로그인 상태가 아니면 로그인 페이지로 이동
  return user ? children : <Navigate to="/login" replace />;
}

export default PrivateRoute;
