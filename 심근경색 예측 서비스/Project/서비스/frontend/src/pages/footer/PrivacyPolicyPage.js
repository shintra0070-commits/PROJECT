import '../../styles/pages/PolicyPage.css';
function PrivacyPolicyPage() {
  return (
    <main className="page policy-page">
      <section className="card policy-card">
        <h1>개인정보처리방침</h1>
        <p className="policy-desc">
          HealthiQ는 이용자의 개인정보를 중요하게 생각하며, 관련 법령에 따라 개인정보를 안전하게 관리합니다.
        </p>

        <div className="policy-section">
          <h2>1. 개인정보 수집 항목</h2>
          <p>회원가입 및 서비스 이용을 위해 아이디, 이름, 전화번호, 서비스 이용 기록 등을 수집할 수 있습니다.</p>
        </div>

        <div className="policy-section">
          <h2>2. 개인정보 이용 목적</h2>
          <ul>
            <li>회원 식별 및 로그인 관리</li>
            <li>건강 분석 서비스 제공</li>
            <li>커뮤니티 서비스 운영</li>
            <li>고객 문의 대응</li>
          </ul>
        </div>

        <div className="policy-section">
          <h2>3. 개인정보 보관 및 파기</h2>
          <p>개인정보는 이용 목적 달성 후 지체 없이 파기하며, 법령에 따라 보관이 필요한 경우에는 해당 기간 동안 보관합니다.</p>
        </div>

        <div className="policy-section">
          <h2>4. 개인정보 보호 조치</h2>
          <p>개인정보 접근 권한 관리, 암호화, 보안 프로그램 적용 등을 통해 이용자 정보를 보호합니다.</p>
        </div>

        <div className="policy-section">
          <h2>5. 문의</h2>
          <p>개인정보 관련 문의는 고객센터 페이지를 통해 접수할 수 있습니다.</p>
        </div>
      </section>
    </main>
  );
}

export default PrivacyPolicyPage;
