import '../../styles/pages/AuthPage.css';
import { useNavigate } from 'react-router-dom';

function GoogleAuthPage() {
  const navigate = useNavigate();

  const handleGoogleAuth = () => {
    // 실제 Google OAuth 연결 전 임시 인증 완료 처리
    navigate('/signup');
  };

  return (
    <main className="page auth-page">
      <section className="auth-card google-card">
        <div>
          <div className="auth-logo">
            <span className="logo-icon">♡</span>
            <span>HealthiQ</span>
          </div>

          <h2>구글 계정으로 인증</h2>
          <p>구글 계정으로 간편하게 회원가입하세요.</p>

          <button className="google-button" onClick={handleGoogleAuth}>
            <span>G</span>
            Google로 계속하기
          </button>
        </div>

        <div className="auth-illust-area">
          <div className="big-illust">📋</div>
        </div>
      </section>
    </main>
  );
}

export default GoogleAuthPage;
