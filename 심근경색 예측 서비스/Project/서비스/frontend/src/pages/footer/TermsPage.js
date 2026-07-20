import '../../styles/pages/PolicyPage.css';
function TermsPage() {
  return (
    <main className="page policy-page">
      <section className="card policy-card">
        <h1>이용약관</h1>
        <p className="policy-desc">
          본 약관은 HealthiQ 서비스 이용과 관련하여 이용자와 서비스 제공자 간의 권리와 의무를 정합니다.
        </p>

        <div className="policy-section">
          <h2>1. 서비스 목적</h2>
          <p>HealthiQ는 건강 정보 입력, 심근경색 발생 확률 예측, 분석 결과 시각화, 커뮤니티 기능을 제공합니다.</p>
        </div>

        <div className="policy-section">
          <h2>2. 이용자 의무</h2>
          <ul>
            <li>타인의 정보를 무단으로 사용하지 않아야 합니다.</li>
            <li>허위 정보를 입력하거나 서비스 운영을 방해해서는 안 됩니다.</li>
            <li>커뮤니티 이용 시 타인을 비방하거나 부적절한 게시물을 작성해서는 안 됩니다.</li>
          </ul>
        </div>

        <div className="policy-section">
          <h2>3. 건강 분석 결과 안내</h2>
          <p>본 서비스의 분석 결과는 참고용이며, 정확한 진단과 치료는 의료기관의 전문 상담을 받아야 합니다.</p>
        </div>

        <div className="policy-section">
          <h2>4. 서비스 변경 및 중단</h2>
          <p>서비스 개선, 점검, 장애 대응 등의 사유로 서비스가 변경되거나 일시 중단될 수 있습니다.</p>
        </div>
      </section>
    </main>
  );
}

export default TermsPage;
