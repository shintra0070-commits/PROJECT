import '../../styles/pages/AnalysisPage.css';
import { useNavigate } from 'react-router-dom';
import React, { useState } from 'react';
import { useAuth } from '../user/AuthContext';
import { getHeartPredict } from "../../flaskApi/predict";
import { checkTodayPredict, getTodayPredict  } from "../../springApi/modeldataSpringBootApi";


function AnalysisPage() {

    const navigate = useNavigate();
    const { user } = useAuth() || {}; // [수정] 수정 완료 후 전역 세션 갱신을 위해 login 함수 구독

    const [features, setFeatures] = useState([]);

    const handleChange = (index, value) => {
        const update = [...features];
        update[index] = Number(value);
        setFeatures(update);
    };

    const handleSubmit = async(e) => {
        e.preventDefault();
        const loginId = user.mem_id;
        try {
            const checkres = await checkTodayPredict(loginId);
            console.log("체크 응답:", checkres);
            console.log("체크 응답 data:", checkres.data);
            console.log("타입:", typeof checkres.data);
            
            if(checkres.data === true) {
                alert("오늘은 이미 예측을 완료했습니다.\n예측은 하루에 한 번만 가능합니다.\n결과창으로 이동합니다.");
                const todayRes = await getTodayPredict(loginId);
                navigate("/result", {state: { result: { probability: todayRes.data } }});
                return;
            }

            const requestData = { features: features, mem_id: loginId };
            const res = await getHeartPredict(requestData);
            alert("예측성공 : " + res.data.probability * 100  + "%" );
            navigate("/result", {state : {result : {probability  : res.data.probability }}});
            } catch(err){
                console.error("예측중 오류 발생 : " , err);
                alert("예측실패");
                }
    }

    

    return (
        <main className="page analysis-page">
            <section className="card form-card">
                <h2>심근경색 발생 확률 구하기</h2>

                <form onSubmit={handleSubmit} className="heart-form">
                <div className="form-two-column">
                    <div>
                        <div className="form-row radio-row"><label>고혈압 의사진단여부 :</label><RadioGroup name="highBloodPressureDoctor" value={features[0]} options={[{label : '있음', value : 1}, {label : '없음', value : 0}]} onChange={(value)=> handleChange(0, value)} /></div>
                        <div className="form-row radio-row"><label>이상지질혈증 의사진단여부 :</label><RadioGroup name="dyslipidemiaDoctor" value={features[1]} options={[{label : '있음', value : 1}, {label : '없음', value : 0}]} onChange={(value)=> handleChange(1, value)} /></div>
                        <div className="form-row radio-row"><label>당뇨병 의사진단여부 :</label><RadioGroup name="diabetesDoctor" value={features[2]} options={[{label : '있음', value : 1}, {label : '없음', value : 0}]} onChange={(value)=> handleChange(2, value)} /></div>
                        <div className="form-row radio-row"><label>뇌졸중 의사진단여부 :</label><RadioGroup name="strokeDoctor" value={features[3]} options={[{label : '있음', value : 1}, {label : '없음', value : 0}]} onChange={(value)=> handleChange(3, value)} /></div>
                        <div className="form-row radio-row"><label>고혈압 유형여부 :</label><RadioGroup name="bloodPressureType" value={features[4]} options={[{label : '정상', value : 1}, {label : '주의혈압', value : 2},{label : '고혈압전단계', value : 3}, {label : '고혈압', value : 4}]} onChange={(value)=> handleChange(4, value)} /></div>
                        <InputRow label="공복 혈당" name="fastingGlucose" value={features[5]} unit="mg/dL" onChange={(e)=> handleChange(5, e.target.value)} />
                        <InputRow label="당화혈색소" name="hba1c" value={features[6]} unit="%" onChange={(e)=> handleChange(6, e.target.value)} />
                        <InputRow label="총콜레스테롤" name="cholesterol" value={features[7]} unit="mg/dL" onChange={(e)=> handleChange(7, e.target.value)} />
                        <InputRow label="체질량지수(BMI)" name="bmi" value={features[8]} unit="kg/m²" onChange={(e)=> handleChange(8, e.target.value)} />
                        <InputRow label="허리둘레" name="waist" value={features[9]} unit="cm" onChange={(e)=> handleChange(9, e.target.value)} />
                    </div>
                    <div>
                        <div className="form-row radio-row vertical-radio"><label>평생 일반담배 흡연 여부 :</label><RadioGroup name="smoking" value={features[10]} options={[{label : '5갑(100개비) 미만', value : 1}, {label : '5갑(100개비) 이상', value : 2}, {label : '피운적 없음', value : 3}]} onChange={(value)=> handleChange(10, value)} /></div>
                        <div className="form-row radio-row vertical-radio"><label>1년간 음주 빈도 :</label><RadioGroup name="drinkingFrequency" value={features[11]} options={[{label : '최근 1년간 전혀 마시지 않았다.', value : 1}, {label : '월 1회미만', value : 2}, {label : '월 1회정도', value : 3}, {label : '월 2~4회정도', value : 4},{label : '주 2~3회정도', value : 5}, {label : '주 4회정도이상', value : 6}]} onChange={(value)=> handleChange(11, value)} /></div>
                        <div className="form-row radio-row vertical-radio"><label>한번에 마시는 음주량 :</label><RadioGroup name="drinkingAmount" value={features[12]} options={[{label : '1~2잔', value : 1},{label : '3~4잔', value : 2} , {label : '5~6잔', value : 3}, {label : '7~9잔', value : 4}, {label : '10잔 이상', value : 5}]} onChange={(value)=> handleChange(12, value)} /></div>
                        <InputRow label="하루에 앉아서 보내는 시간" name="sittingTime" value={features[13]} unit="시간" onChange={(e)=> handleChange(13, e.target.value)} />
                        <div className="form-row radio-row"><label>유산소 신체 활동 실천율 :</label><RadioGroup name="exercise" value={features[14]} options={[{label : '실천하지않음', value : 0}, {label : '실천함', value : 1}]} onChange={(value)=> handleChange(14, value)} /></div>
                        <div className="form-row radio-row"><label>성별 :</label><RadioGroup name="gender" value={features[15]} options={[{label : '남자', value : 1}, {label : '여자', value : 2}]} onChange={(value)=> handleChange(15, value)} /></div>
                        <InputRow label="나이" name="age" value={features[16]} unit="세" onChange={(e)=> handleChange(16, e.target.value)} />
                        <div className="form-row radio-row"><label>최종 학력 :</label><RadioGroup name="education" value={features[17]} options={[{label : '초졸 이하', value : 1}, {label : '중졸', value : 2}, {label : '고졸', value : 3}, {label : '대졸이상', value : 4}]} onChange={(value)=> handleChange(17, value)} /></div>
                        <div className="form-row radio-row"><label>소득 분위수 :</label><RadioGroup name="income" value={features[18]} options={[{label : '하', value : 1}, {label : '중하', value : 2}, {label : '중상', value : 3}, {label : '상', value : 4}]} onChange={(value)=> handleChange(18, value)} /></div>
                    </div>
                </div>

                <div className="form-button-area"> 
                    <button type="submit" className="btn-primary large">예측하기</button>
                </div>
                </form>
            </section>
        </main>
        );
    }


function InputRow({ label, name, value, unit, onChange }) {
    return (
        <div className="form-row input-row">
            <label>{label} :</label>
            <input  type="number" step="any" name={name} value={value === 0 ? '' : value} onChange={onChange} required />
            <span>{unit}</span>
        </div>
    );
}

function RadioGroup({ name, value, options, onChange }) {
    return (
        <div className="radio-group">
            {options.map((option) => (
                <label key={option.label}>
                    <input type="radio" name={name} value={option.value} checked={value === option.value} onChange={(e)=> onChange(Number(e.target.value))} required />
                    {option.label}
                </label>
            ))}
        </div>
    );
}


export default AnalysisPage;
