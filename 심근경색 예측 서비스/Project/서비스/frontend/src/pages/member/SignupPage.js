import '../../styles/pages/AuthPage.css';
import { useNavigate, Link } from 'react-router-dom';
import { useState } from 'react';

function SignupPage() {
  const navigate = useNavigate();

  // 수정사항: 회원가입은 아이디, 이름, 전화번호만 받도록 변경
  const [signupData, setSignupData] = useState({
    userId: '',
    name: '',
    phone: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setSignupData({ ...signupData, [name]: value });
  };

  const handleSignup = (e) => {
    e.preventDefault();

    // DB 연결 전 임시 회원가입 완료 처리
    alert('회원가입이 완료되었습니다.');
    navigate('/login');
  };

  return (
    <main className="page auth-page">
      <section className="auth-card signup-card">
        <div className="auth-form-area">
          <h2>회원가입</h2>
          <p>새 계정을 만들어 서비스를 이용하세요.</p>

          <form onSubmit={handleSignup}>
            <label>아이디</label>
            <input
              name="userId"
              value={signupData.userId}
              onChange={handleChange}
              placeholder="아이디를 입력하세요"
              required
            />

            <label>이름</label>
            <input
              name="name"
              value={signupData.name}
              onChange={handleChange}
              placeholder="이름을 입력하세요"
              required
            />

            <label>전화번호</label>
            <input
              name="phone"
              value={signupData.phone}
              onChange={handleChange}
              placeholder="010-0000-0000"
              required
            />

            <button type="submit" className="btn-primary full">회원가입</button>
          </form>

          <p className="auth-bottom-text">
            이미 계정이 있으신가요? <Link to="/login">로그인</Link>
          </p>
        </div>

        <div className="auth-illust-area">
          <div className="big-illust">✅</div>
        </div>
      </section>
    </main>
  );
}

export default SignupPage;
