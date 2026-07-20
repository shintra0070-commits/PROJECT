import '../../styles/layout/Navbar.css';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../../pages/user/AuthContext'; 

function Navbar() {
    const navigate = useNavigate();
    const location = useLocation();
    const { user, logout } = useAuth();

    const handleLogout = () => {
        navigate('/');
        logout();
    };

    const isActive = (path) => {
        if (path === '/') return location.pathname === '/';
        return location.pathname.startsWith(path);
    };

    // [핵심 교정] 마이페이지 진입 제어 통합 함수
    const handleMyPage = (e) => {
        // 만약 <Link> 태그의 기본 주소 이동 동작(Href)이 남아있다면 강제 차단
        if (e) e.preventDefault();

        if (!user) {
            alert("로그인 후 이용 가능합니다.");
            navigate("/login");
            return;
        }
        
        navigate("/mypage");
    };
    const handleAnalysisPage = (e) => {
        // 만약 <Link> 태그의 기본 주소 이동 동작(Href)이 남아있다면 강제 차단
        if (e) e.preventDefault();

        if (!user) {
            alert("로그인 후 이용 가능합니다.");
            navigate("/login");
            return;
        }
        
        navigate("/result");
    };

    return (
        <header className="navbar">
        <div className="navbar-inner">
            <Link to="/" className="logo">
            <span className="logo-icon">♡</span>
            <span>HealthiQ</span>
            </Link>

            <nav className="menu">
            <Link className={isActive('/') ? 'active' : ''} to="/">홈</Link>
            <Link className={isActive('/analysis') || isActive('/result') ? 'active' : ''} to="/analysis" onClick={handleAnalysisPage}>분석</Link>
            <Link className={isActive('/community') ? 'active' : ''} to="/community">커뮤니티</Link>
            
            {/* [수정] 일반 Link 이동을 방지하고 onClick 핸들러 가드를 바인딩했습니다 */}
            <Link 
                className={isActive('/mypage') ? 'active' : ''} 
                to="/mypage" 
                onClick={handleMyPage}
            >
                마이페이지
            </Link>
            </nav>

            <div className="auth-buttons">
            {user ? (
                <>
                <span className="user-name">{user?.mem_name||user?.name}님</span>
                <button className="btn-outline" onClick={handleLogout}>로그아웃</button>
                <button className="btn-outline mint" onClick={handleMyPage}>마이페이지</button>
                </>
            ) : (
                <>
                <button className="btn-outline" onClick={() => navigate('/login')}>로그인 및 회원가입</button>
                </>
            )}
            </div>
        </div>
        </header>
    );
}

export default Navbar;