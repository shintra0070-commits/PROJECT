import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { calcHeartAgeFromSelectedData } from '../../assets/heartagecalc/Heartagelogic';
import { NON_SMOKER, SMOKER } from '../../assets/heartagecalc/Heartagedata';

function RecordDetailPage() {
    const navigate = useNavigate();
    const location = useLocation();
    const record = location.state?.record;

    // record 없으면 마이페이지로 복귀
    if (!record) {
        return (
            <main className="page result-page">
                <div className="card">
                    <p>데이터가 없습니다.</p>
                    <button className="btn-outline mint" onClick={() => navigate('/mypage')}>
                        마이페이지로 돌아가기
                    </button>
                </div>
            </main>
        );
    }

    const probability = record.PREDICT || 0;
    const percent = (probability * 100).toFixed(2);
    const fillDeg = Math.round(probability * 360);

    const heartAge = calcHeartAgeFromSelectedData(record, NON_SMOKER, SMOKER);

    const getRiskColor = (prob) => {
        if (prob < 0.1) return { main: "#0f9f8d", light: "#e0f7f4", label: "낮음" };
        if (prob < 0.3) return { main: "#f59e0b", light: "#fde68a", label: "보통" };
        return          { main: "#ef4444", light: "#fca5a5", label: "높음" };
    };

    const riskColor = getRiskColor(probability);

    const renderDiseaseStatus = (val) => {
        if (val === undefined || val === null) return "-";
        return Number(val) === 1 ? "질환/이상" : "정상";
    };

    const getCombinedRisk = (di1_dg, di2_dg, de1_dg, ge_gba1c, he_chol) => {
        const comments = [];
        if (di1_dg === 0) comments.push("심근경색의 주요 원인 중 하나인 고혈압 없으셔서, 현재 상태는 아주 좋습니다.");
        if (di1_dg === 1) comments.push("고혈압은 심근경색의 주요 원인이라 각별한 주의가 필요합니다.");
        if (di2_dg === 0) comments.push("심근경색의 주요 원인 중 하나인 이상지질혈증이 없으셔서, 현재 상태는 아주 좋습니다.");
        if (di2_dg === 1) comments.push("이상지질혈증은 심근경색의 주요 원인이라 각별한 주의가 필요합니다.");
        if (de1_dg === 0) comments.push("심근경색의 주요 원인 중 하나인 당뇨병이 없으셔서, 현재 상태는 아주 좋습니다.");
        if (de1_dg === 1) comments.push("당뇨병은 심근경색의 주요 원인이라 각별한 주의가 필요합니다.");
        if (ge_gba1c < 5.7) comments.push("당화혈색소 수치 정상입니다.");
        else if (ge_gba1c < 6.4) comments.push("당화혈색소가 당뇨 전단계 수준입니다. 식단과 운동 관리가 필요합니다.");
        else comments.push("당화혈색소 수치가 당뇨 기준을 넘었습니다. 적극적인 치료가 필요합니다.");
        if (he_chol < 200) comments.push("총 콜레스테롤 수치 정상입니다.");
        else if (he_chol <= 239) comments.push("총콜레스테롤이 경계 수치입니다. 식단과 운동 관리가 필요합니다.");
        else comments.push("총콜레스테롤 수치가 고지혈증 기준을 넘었습니다. 약물 관리가 필요할 수 있습니다.");
        return comments.length > 0 ? comments : ["분석할 수 있는 건강 데이터가 부족합니다."];
    };

    return (
        <main className="page result-page">
            {/* 상단 뒤로가기 */}
            <div style={{ marginBottom: "16px" }}>
                <button className="btn-outline mint" onClick={() => navigate(-1)}>← 돌아가기</button>
                <span style={{ marginLeft: "12px", color: "#666", fontSize: "14px" }}>
                    {record.CHECK_DATE}분석 기록 상세보기
                </span>
            </div>

            <section className="result-grid">
                {/* 왼쪽 카드: 위험도 + 심장나이 + 정상범위 */}
                <div className="card risk-card">
                    <h3>{record.CHECK_DATE} 심근경색 발생 확률</h3>
                    <div className="circle-chart" style={{
                        "--risk-main": riskColor.main,
                        "--risk-light": riskColor.light,
                        "--risk-deg": `${fillDeg}deg`
                    }}>
                        <div className='circle-chart-inner'>
                            <span>{percent}%</span>
                        </div>
                    </div>
                    <h3 className="risk-low" style={{ color: riskColor.main }}>위험도 : {riskColor.label}</h3>

                    <div>
                        <p style={{ fontWeight: "bold", marginBottom: "10px" }}>심장 나이 계산</p>
                        <p style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>
                            {heartAge
                                ? `${heartAge.heartAge}세 (실제보다 ${Math.abs(heartAge.diff)}세 ${heartAge.diff > 0 ? "더 많음" : "더 젊음"})`
                                : "계산 불가 (30~79세만 지원)"}
                        </p>
                    </div>

                    <div>
                        <p style={{ fontWeight: "bold", marginBottom: "10px" }}>정상 범위 및 수치</p>
                        <table style={{ width: "100%", borderCollapse: "collapse", textAlign: "left", fontSize: "13px" }}>
                            <thead>
                                <tr style={{ backgroundColor: "#f1f3f5" }}>
                                    <th style={{ padding: "10px", border: "1px solid #dee2e6" }}>데이터 항목</th>
                                    <th style={{ padding: "10px", border: "1px solid #dee2e6" }}>정상 수치</th>
                                </tr>
                            </thead>
                            <tbody>
                                {[
                                    ["공복혈당", "70 ~ 99mg/dL"],
                                    ["당화혈색소", "5.7% 미만"],
                                    ["총콜레스테롤", "200mg/dL 미만"],
                                    ["체질량지수", "18.5 ~ 22.9"],
                                    ["허리둘레", "남성: 90cm / 여성: 85cm"],
                                ].map(([label, normal]) => (
                                    <tr key={label}>
                                        <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>{label}</td>
                                        <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{normal}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>

                {/* 오른쪽 카드: Raw Data 표 */}
                <div className="card result-chart-card">
                    <h3>산출 원본 데이터 (전체 변수)</h3>
                    <div style={{ padding: "20px", border: "1px solid #e2e8f0", borderRadius: "8px", backgroundColor: "#fff" }}>
                        <div style={{ display: "flex", gap: "30px", marginBottom: "20px", backgroundColor: "#ebf8ff", padding: "15px", borderRadius: "6px" }}>
                            <p style={{ margin: 0 }}><strong>검사 일자:</strong> {record.CHECK_DATE}</p>
                            <p style={{ margin: 0 }}><strong>예측 확률:</strong> <span style={{ color: "#007bff", fontWeight: "bold" }}>{record.PREDICT}</span></p>
                        </div>

                        <table style={{ width: "100%", borderCollapse: "collapse", textAlign: "left", fontSize: "13px" }}>
                            <thead>
                                <tr style={{ backgroundColor: "#f1f3f5" }}>
                                    <th style={{ padding: "10px", border: "1px solid #dee2e6", width: "30%" }}>데이터 항목</th>
                                    <th style={{ padding: "10px", border: "1px solid #dee2e6", width: "20%" }}>수치</th>
                                    <th style={{ padding: "10px", border: "1px solid #dee2e6", width: "30%" }}>데이터 항목</th>
                                    <th style={{ padding: "10px", border: "1px solid #dee2e6", width: "20%" }}>수치</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>나이 (AGE)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{record.AGE}세</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>성별 (SEX)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{Number(record.SEX) === 1 ? "남성" : "여성"}</td>
                                </tr>
                                <tr>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>공복혈당 (HE_GLU)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{record.HE_GLU} mg/dL</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>당화혈색소 (HE_HBA1C)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{record.HE_HBA1C} %</td>
                                </tr>
                                <tr>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>총콜레스테롤 (HE_CHOL)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{record.HE_CHOL} mg/dL</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>비만도 (HE_BMI)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{record.HE_BMI}</td>
                                </tr>
                                <tr>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>허리둘레 (HE_WC)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{record.HE_WC} cm</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>고혈압 유병여부 (HE_HP)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{record.HE_HP}</td>
                                </tr>
                                <tr>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>유산소 운동 (PA_AEROBIC)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{record.PA_AEROBIC}</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>수면 시간 (BE8_1)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{record.BE8_1} 시간</td>
                                </tr>
                                <tr>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>소득 수준 (INCM)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{record.INCM} 분위</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>교육 수준 (EDU)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{record.EDU} 단계</td>
                                </tr>
                                <tr>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>고혈압 진단 (DI1_DG)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{renderDiseaseStatus(record.DI1_DG)}</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>이상지질혈증 진단 (DI2_DG)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{renderDiseaseStatus(record.DI2_DG)}</td>
                                </tr>
                                <tr>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>당뇨병 진단 (DI3_DG)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{renderDiseaseStatus(record.DI3_DG)}</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>뇌졸중 진단 (DE1_DG)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{renderDiseaseStatus(record.DE1_DG)}</td>
                                </tr>
                                <tr>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>음주 빈도 (BD1_11)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{record.BD1_11}</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>음주량 (BD2_1)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{record.BD2_1}</td>
                                </tr>
                                <tr>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>흡연 상태 (BS1_1)</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{record.BS1_1}</td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}></td>
                                    <td style={{ padding: "10px", border: "1px solid #dee2e6" }}></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </section>

            {/* 해석 카드 */}
            <br />
            <div className="card">
                <h3>건강 데이터 해석</h3>
                <div className="risk-comment-container">
                    <p>해당 시점 위험도는 <strong style={{ color: riskColor.main }}>{riskColor.label}</strong> 입니다.</p>
                    {getCombinedRisk(
                        record.DI1_DG,
                        record.DI2_DG,
                        record.DE1_DG,
                        record.HE_HBA1C,
                        record.HE_CHOL
                    ).map((comment, index) => (
                        <p key={index} style={{ marginBottom: '8px', lineHeight: '1.5' }}>• {comment}</p>
                    ))}
                </div>
            </div>
        </main>
    );
}

export default RecordDetailPage;