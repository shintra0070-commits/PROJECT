const BMI_COLS = ["17.5-22.4", "22.5-27.4", "27.5-32.4", "32.5-37.4", "37.5-42.4"];

function getBmiRange(bmi) {
    if (bmi < 17.5)  return null;
    if (bmi <= 22.4) return "17.5-22.4";
    if (bmi <= 27.4) return "22.5-27.4";
    if (bmi <= 32.4) return "27.5-32.4";
    if (bmi <= 37.4) return "32.5-37.4";
    if (bmi <= 42.4) return "37.5-42.4";
    return null;
}

function getDecade(age) {
    if (age < 30)  return null;
    if (age < 40)  return "30";
    if (age < 50)  return "40";
    if (age < 60)  return "50";
    if (age < 70)  return "60";
    if (age <= 79) return "70";
    return null;
}

// 고혈압 진단 여부로 혈압 범위 추정
// DI2_DG: 0=정상 → "130-149", 1=고혈압 → "150-169"
function getBpRangeFromDiagnosis(di2_dg) {
    return Number(di2_dg) === 1 ? "150-169" : "130-149";
}

/**
 * selectedData 기반 심장 나이 계산
 *
 * 사용 필드:
 *   AGE    → 나이
 *   SEX    → 성별 (1=남성, 2=여성)
 *   HE_BMI → 체질량지수
 *   DI2_DG → 고혈압 의사진단 (0=없음, 1=있음)
 *   BS1_1  → 흡연 상태 (1=5갑 미만, 2=5갑 이상, 3=피운 적 없음)
 *   DE1_DG → 당뇨병 의사진단 (0=없음, 1=있음)
 */
export function calcHeartAgeFromSelectedData(selectedData, NON_SMOKER_TABLE, SMOKER_TABLE) {
    const age = Number(selectedData.AGE);
    const bmi = Number(selectedData.HE_BMI);
    const sex = Number(selectedData.SEX) === 1 ? "male" : "female";
    // 3=피운 적 없음만 비흡연, 1·2는 흡연자
    const smoking  = Number(selectedData.BS1_1) === 3 ? "non" : "smoker";
    const diabetic = Number(selectedData.DE1_DG) === 1;

    const decade   = getDecade(age);
    const bmiRange = getBmiRange(bmi);
    const bpRange  = getBpRangeFromDiagnosis(selectedData.DI2_DG);

    if (!decade || !bmiRange) return null;

    const table       = smoking === "smoker" ? SMOKER_TABLE : NON_SMOKER_TABLE;
    const diabeticKey = diabetic ? "yes" : "no";
    const bmiIdx      = BMI_COLS.indexOf(bmiRange);

    const offset = table?.[decade]?.[diabeticKey]?.[sex]?.[bpRange]?.[bmiIdx];

    if (offset === null || offset === undefined) return null;

    const heartAge = age + offset;
    return {
        heartAge: heartAge >= 100 ? 100 : heartAge,
        diff: heartAge - age,
    };
}