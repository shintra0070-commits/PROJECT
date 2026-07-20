import React, { useState, useEffect } from 'react';
import '../../styles/pages/ResultPage.css';
import { useLocation  } from 'react-router-dom';
import { useAuth } from '../user/AuthContext';
import { getDataView, checkTodayPredict } from "../../springApi/modeldataSpringBootApi"; 
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from "recharts";
import { calcHeartAgeFromSelectedData } from '../../assets/heartagecalc/Heartagelogic';
import { NON_SMOKER, SMOKER } from '../../assets/heartagecalc/Heartagedata';
import { useNavigate } from 'react-router-dom';


function ResultPage() {
    const navigate = useNavigate();
    const { user } = useAuth() || {}; // [수정] 수정 완료 후 전역 세션 갱신을 위해 login 함수 구독
    const location = useLocation();
    const result = location.state?.result || {};
    const [probability, setProbability] = useState(result.probability || 0);
    const percent = (probability * 100).toFixed(2);
    const fillDeg = Math.round(probability * 360);
    const [heartAge, setHeartAge] = useState(null);
    const [hasPredictedToday, setHasPredictedToday] = useState(false);
    
    
    // 상태 관리
    const [chartData, setChartData] = useState([]);
    const [selectedData, setSelectedData] = useState(null);
    const [loading, setLoading] = useState(true);

    const memId = user?.mem_id; 

    const getRiskColor = (prob) => {
        if (prob < 0.05) return { main: "#0f9f8d", light: "#e0f7f4", label: "낮음" };
        if (prob < 0.1) return { main: "#f59e0b", light: "#fde68a", label: "보통" };
        return          { main: "#ef4444", light: "#fca5a5", label: "높음" };
    };

    const riskColor = getRiskColor(probability);

    const getCombinedRisk = (di1_dg, di2_dg, de1_dg, ge_gba1c, he_chol) => {
        // 코멘트들을 담을 빈 배열 생성
        const comments = [];
        
        // 1. 고혈압 체크
        if (di1_dg === 0) comments.push("심근경색의 주요 원인 중 하나인 고혈압 없으셔서, 현재 상태는 아주 좋습니다.");
        if (di1_dg === 1) comments.push("고혈압은 심근경색의 주요 원인이라 각별한 주의가 필요합니다. 지금부터 철저히 관리하셔야 합니다.");

        // 2. 이상지질혈증 체크
        if (di2_dg === 0) comments.push("심근경색의 주요 원인 중 하나인 이상지질혈증이 없으셔서, 현재 상태는 아주 좋습니다.");
        if (di2_dg === 1) comments.push("이상지질혈증은 심근경색의 주요 원인이라 각별한 주의가 필요합니다. 지금부터 철저히 관리하셔야 합니다.");

        // 3. 당뇨병 유무 체크
        if (de1_dg === 0) comments.push("심근경색의 주요 원인 중 하나인 당뇨병이 없으셔서, 현재 상태는 아주 좋습니다.");
        if (de1_dg === 1) comments.push("당뇨병은 심근경색의 주요 원인이라 각별한 주의가 필요합니다. 지금부터 철저히 관리하셔야 합니다.");

        // 4. 당화혈색소 수치 체크
        if (ge_gba1c < 5.7) comments.push("당화혈색소 수치 정상입니다. 지금 상태 아주 좋습니다.");
        else if (ge_gba1c < 6.4) comments.push("당화혈색소가 당뇨 전단계 수준입니다. 지금부터 식단과 운동 관리가 필요합니다.");
        else if (ge_gba1c >= 6.5) comments.push("당화혈색소 수치가 당뇨 기준을 넘었습니다. 적극적인 치료와 관리가 시급합니다.");

        // 5. 총 콜레스테롤 수치 체크
        if (he_chol < 200) comments.push("총 콜레스테롤 수치 정상입니다. 지금 상태 아주 좋습니다.");
        else if (he_chol <= 239) comments.push("총콜레스테롤이 경계 수치입니다. 지금부터 식단과 운동 관리가 필요합니다.");
        else if (he_chol >= 240) comments.push("총콜레스테롤 수치가 고지혈증 기준을 넘었습니다. 적극적인 치료와 약물 관리가 필요할 수 있습니다.");

        // 조건에 맞는 문구들이 담긴 배열을 그대로 리턴합니다.
        return comments;
    };

    useEffect(() => {
        setLoading(true);
        
        // 오늘 예측 여부 확인
        const checkPredict = async () => {
            try {
                const checkres = await checkTodayPredict(memId);
                setHasPredictedToday(checkres.data === true);
            } catch (error) {
                console.error("예측 여부 확인 실패:", error);
                setHasPredictedToday(false);
            }
        };

        checkPredict();
        
        getDataView(memId)
            .then((response) => {
                const sortedData = response.data.sort((a, b) => {
                    return new Date(a.CHECK_DATE) - new Date(b.CHECK_DATE);
                });

                setChartData(sortedData);

                if (sortedData.length > 0) {
                    const latest = sortedData[sortedData.length - 1];
                    setProbability(latest.PREDICT);
                }
                setLoading(false);
            })

            .catch((error) => {
                console.error("데이터 로딩 실패:", error);
                setLoading(false);
            });

    }, [memId]);

    // 구조적 예외 처리를 추가한 클릭 핸들러
    const handlePointClick = (payload) => {
            if (payload?.payload) {
                const data = payload.payload;
                setSelectedData(data);
                const heartResult = calcHeartAgeFromSelectedData(data, NON_SMOKER, SMOKER);
                setHeartAge(heartResult);
            }
        };


    // 0 또는 1로 들어오는 진단 값을 '정상/질환' 텍스트로 치환하는 유틸 함수
    const renderDiseaseStatus = (val) => {
        if (val === undefined || val === null) return "-";
        return Number(val) === 1 ? "질환/이상" : "정상";
    };

    const handleAnalysis = () => {
        navigate("/analysis");
        return;
    }

    return (
    <main className="page result-page">
        <section className="result-grid">
        <div className="card risk-card">
            <h3>오늘의 심근경색 발생 확률</h3>
            <div className="circle-chart" style={{
                                        "--risk-main": riskColor.main,
                                        "--risk-light": riskColor.light,
                                        "--risk-deg": `${fillDeg}deg`
                                    }}
            ><div className='circle-chart-inner'><span>{hasPredictedToday ? `${percent}%` : '???'}</span></div></div>
            <h3 className="risk-low" style={{ color: riskColor.main }}>{hasPredictedToday ? `위험도 : ${riskColor.label}` : '오늘의 위험도 예측해주세요!'}</h3>
            <div>
                <p style={{ fontWeight: "bold", marginBottom: "10px" }}>심장 나이 계산</p>
                <p style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>
                    {heartAge? `${heartAge.heartAge}세 (실제보다 ${Math.abs(heartAge.diff)}세 ${heartAge.diff > 0 ? "더 많음" : "더 젊음"})`: "계산 불가 (30~79세만 지원)"}
                </p>
            </div>

            <div>
                <p style={{ fontWeight: "bold", marginBottom: "10px" }}>정상 범위 및 수치</p>
                <table style={{ width: "100%", borderCollapse: "collapse", textAlign: "left", fontSize: "13px" }}>
                    <thead>
                        <tr style={{ backgroundColor: "#f1f3f5" }}>
                            <th style={{ padding: "10px", border: "1px solid #dee2e6", width: "30%" }}>데이터 항목</th>
                            <th style={{ padding: "10px", border: "1px solid #dee2e6", width: "30%" }}>정상 수치</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>공복혈당</td>
                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>70 ~ 99mg/dL</td>
                        </tr>
                        <tr>
                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>당화혈색소</td>
                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>5.7% 미만</td>
                        </tr>
                        <tr>
                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>총콜레스테롤</td>
                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>200mg/dL 미만</td>
                        </tr>
                        <tr>
                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>체질량지수</td>
                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>18.5 ~ 22.9</td>
                        </tr>
                        <tr>
                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>허리둘레</td>
                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>남성: 90cm / 여성: 85cm</td>
                        </tr>
                        <tr>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div className="card result-chart-card">
            <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                <h3 style={{ margin: 0 }}>분석 추이 그래프</h3>
                <button className="btn-outline mint" onClick={handleAnalysis} >예측하기</button>
            </div> 
            
            <div style={{ width: "95%", margin: "0 auto", padding: "20px", fontFamily: "sans-serif" }}>
            <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: "20px" }}>
                <h3>{user?.mem_name} 님의 분석 그래프 보기</h3>
            </div>

            {loading ? (
                <div>데이터를 불러오는 중입니다...</div>
            ) : chartData.length === 0 ? (
                <div>조회된 데이터가 없습니다. (데이터가 비어있음)</div>
            ) : (
                <>
                    {/* 그래프 영역 */}
                    <div style={{ width: "100%", height: 350, backgroundColor: "#f8f9fa", borderRadius: "8px", padding: "15px", boxSizing: "border-box" }}>
                        <ResponsiveContainer width="100%" height="100%">
                            <LineChart 
                                data={chartData} 
                                margin={{ top: 10, right: 30, left: 0, bottom: 0 }}
                                >
                                <CartesianGrid strokeDasharray="3 3" stroke="#e0e0e0" />
                                <XAxis dataKey="CHECK_DATE" tick={{ fontSize: 12 }} />
                                <YAxis domain={[0, 'auto']} tick={{ fontSize: 12 }} tickFormatter={(value) => `${(value * 100).toFixed(0)}%`}/>
                                <Tooltip wrapperStyle={{ pointerEvents: "none" }} />
                                <Line
                                    type="monotone"
                                    dataKey="PREDICT"
                                    name="예측 확률"
                                    stroke="#007bff"
                                    strokeWidth={3}
                                    dot={{r : 5}}
                                    activeDot={{ 
                                        r: 12, 
                                        style: { cursor: "pointer" },
                                        onClick: (e, payload) => handlePointClick(payload)
                                    }}
                                    style={{ cursor: "pointer" }} 
                                />
                            </LineChart>
                        </ResponsiveContainer>
                    </div>
                    <p style={{ fontSize: "13px", color: "#666", marginTop: "5px" }}>그래프 위의 🔵 점을 클릭하시면 하단에 해당 시점의 산출 원본 데이터가 표시됩니다.</p>

                    <hr style={{ margin: "30px 0", border: "0", borderTop: "1px solid #ddd" }} />

                    {/* 하단 상세 데이터 영역 (25개 전체 데이터 표 표시) */}
                    <div style={{ padding: "20px", border: "1px solid #e2e8f0", borderRadius: "8px", backgroundColor: "#fff" }}>
                        <h4 style={{ marginTop: 0, color: "#333", borderBottom: "2px solid #007bff", paddingBottom: "8px" }}>
                            선택된 시점의 예측 결과 및 산출 데이터 (Raw Data 25개 전체 변수)
                        </h4>

                        {selectedData ? (
                            <div>
                                <div style={{ display: "flex", gap: "30px", marginBottom: "20px", backgroundColor: "#ebf8ff", padding: "15px", borderRadius: "6px" }}>
                                    <p style={{ margin: 0 }}><strong>검사 일자 (CHECK_DATE):</strong> {selectedData.CHECK_DATE}</p>
                                    <p style={{ margin: 0 }}><strong>머신러닝 예측 확률 (PREDICT):</strong> <span style={{color: "#007bff", fontWeight: "bold"}}>{selectedData.PREDICT * 100}%</span></p>
                                </div>

                                <p style={{ fontWeight: "bold", marginBottom: "10px" }}>확률 산출 근거 전체 데이터 목록</p>
                                <table style={{ width: "100%", borderCollapse: "collapse", textAlign: "left", fontSize: "13px" }}>
                                    <thead>
                                        <tr style={{ backgroundColor: "#f1f3f5" }}>
                                            <th style={{ padding: "10px", border: "1px solid #dee2e6", width: "30%" }}>데이터 항목 (변수명)</th>
                                            <th style={{ padding: "10px", border: "1px solid #dee2e6", width: "20%" }}>측정 수치 (값)</th>
                                            <th style={{ padding: "10px", border: "1px solid #dee2e6", width: "30%" }}>데이터 항목 (변수명)</th>
                                            <th style={{ padding: "10px", border: "1px solid #dee2e6", width: "20%" }}>측정 수치 (값)</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {/* Row 1 */}
                                        {/* <tr>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>데이터 ID (DATA_ID)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{selectedData.DATA_ID}</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>회원 ID (mem_id)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{selectedData.mem_id}</td>
                                        </tr> */}
                                        {/* Row 2 */}
                                        <tr>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>나이 (AGE)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{selectedData.AGE}세</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>성별 (SEX)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{Number(selectedData.SEX) === 1 ? "남성" : "여성"}</td>
                                        </tr>
                                        {/* Row 3 */}
                                        <tr>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>공복혈당 (HE_GLU)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{selectedData.HE_GLU} mg/dL</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>당화혈색소 (HE_HBA1C)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{selectedData.HE_HBA1C} %</td>
                                        </tr>
                                        {/* Row 4 */}
                                        <tr>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>총콜레스테롤 (HE_CHOL)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{selectedData.HE_CHOL} mg/dL</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>비만도 (HE_BMI)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{selectedData.HE_BMI}</td>
                                        </tr>
                                        {/* Row 5 */}
                                        <tr>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>허리둘레 (HE_WC)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{selectedData.HE_WC} cm</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>고혈압 유병여부 (HE_HP)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{selectedData.HE_HP}(코드식)</td>
                                        </tr>
                                        {/* Row 6 */}
                                        <tr>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>유산소 운동 실천 여부 (PA_AEROBIC)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{selectedData.PA_AEROBIC}(코드식)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>흡연 여부 (BS1_1)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{selectedData.BS1_1}(코드식)</td>
                                        </tr>
                                        {/* Row 7 */}
                                        <tr>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>소득 수준 (INCM)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{selectedData.INCM} 분위</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>교육 수준 (EDU)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{selectedData.EDU} 단계</td>
                                        </tr>
                                        {/* Row 8 */}
                                        <tr>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>당뇨병 의사진단 여부 (DI1_DG)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{renderDiseaseStatus(selectedData.DI1_DG)}</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>고혈압 의사진단 여부 (DI2_DG)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{renderDiseaseStatus(selectedData.DI2_DG)}</td>
                                        </tr>
                                        {/* Row 9 */}
                                        <tr>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>이상지질혈증 의사진단 (DI3_DG)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{renderDiseaseStatus(selectedData.DI3_DG)}</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>뇌졸중 의사진단 여부 (DE1_DG)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{renderDiseaseStatus(selectedData.DE1_DG)}</td>
                                        </tr>
                                        {/* Row 10 */}
                                        <tr>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>1년간 음주 빈도 (BD1_11)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{selectedData.BD1_11} (코드식)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>한번에 마시는 음주량 (BD2_1)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{selectedData.BD2_1} (코드식)</td>
                                        </tr>
                                        {/* Row 11 */}
                                        <tr>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6", backgroundColor: "#fafafa" }}>일생동안의 흡연 상태 (BS1_1)</td>
                                            <td style={{ padding: "10px", border: "1px solid #dee2e6" }}>{selectedData.BS1_1} (코드식)</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        ) : (
                            <p style={{ color: "#999",  textAlign: "center", padding: "20px 0" }}>
                                위의 그래프에서 분석 결과인 파란색 점을 클릭하면 이 자리에 세부 원본 데이터 표가 나타납니다.
                            </p>
                        )}
                    </div>
                </>
            )}
            </div>
        </div>
        
        </section>
        <br/>
        <div className="card">
            <h3>해석 (그래프의 점을 클릭하면 해석이 나타납니다.)</h3>
            <div>
                {selectedData ? (
                    <div className="risk-comment-container">
                        <p>현재 위험도는 {riskColor.label} 입니다. </p>
                    {getCombinedRisk(
                        selectedData.DI1_DG,
                        selectedData.DI2_DG,
                        selectedData.DE1_DG,
                        selectedData.HE_HBA1C,
                        selectedData.HE_CHOL
                    ).map((comment, index) => (
                        // 문자열 배열이므로 text를 그대로 출력하되, 고유한 key를 줍니다.
                        <p key={index} style={{ marginBottom: '8px', lineHeight: '1.5' }}>
                        • {comment}
                        </p>
                    ))}
                    </div>
                ) : (
                    <p></p>
                )}
                </div>
        </div>
        
    </main>
    );
}

export default ResultPage;
