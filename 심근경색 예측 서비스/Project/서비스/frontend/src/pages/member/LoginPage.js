import '../../styles/pages/AuthPage.css';
import GoogleLoginButton from '../../components/user/GoogleLoginButton';

function LoginPage() {


    return (
        <main className="page auth-page login-google-page">
        <section className="auth-card login-google-card">
            <div className="login-google-left">
            <div className="auth-logo login-auth-logo">
                <span className="logo-icon">♡</span>
                <span>HealthiQ</span>
            </div>

            <h2>구글 계정으로 인증</h2>
            <p>구글 계정으로 간편하게 로그인하세요.</p>


            <GoogleLoginButton />
            
            </div>

            <div className="login-google-illust-box" aria-hidden="true">
            <div className="big-illust">📋</div>
            </div>
        </section>
        </main>
    );
}

export default LoginPage;
