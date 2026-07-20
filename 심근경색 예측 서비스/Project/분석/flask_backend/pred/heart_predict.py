import joblib
import numpy as np
import pandas as pd

# 1. 모델과 웹 서버용 35개 컬럼 리스트 로드
heart_model = joblib.load("./models/heart_model_last.pkl")
model_features = joblib.load("./models/model_features.pkl") # 저장한 35개 컬럼 리스트

def getheartPredict(features):
    print(">>>>>> 예측 시작 (확률 출력 모드)")
    
    feature_names_19 = [
        'di1_dg', 'di2_dg', 'de1_dg', 'di3_dg', 'he_hp', 'he_glu', 'he_hba1c', 'he_chol',
        'he_bmi', 'he_wc', 'bs1_1', 'bd1_11', 'bd2_1', 'pa_aerobic', 'be8_1',
        'sex', 'age', 'edu', 'incm'
    ]

    df_raw = pd.DataFrame([features], columns=feature_names_19)
    
    # 3. 범주형 변수들을 원핫 인코딩 (pd.get_dummies)
    # 국건영 범주형 데이터 변수들 지정
    cate_cols = ['di1_dg', 'di2_dg', 'de1_dg', 'di3_dg', 'he_hp', 'bs1_1', 'bd1_11', 'bd2_1', 'pa_aerobic', 'sex', 'edu', 'incm']
    categories_dict = {"di1_dg" : [0, 1],"di2_dg" : [0, 1],"de1_dg" : [0, 1],"di3_dg" : [0, 1],"he_hp" : [1,2,3,4],"bs1_1" : [1,2,3],
                       "bd1_11" : [1,2,3,4,5,6],"bd2_1" : [1,2,3,4,5],"pa_aerobic" : [0, 1],"sex" : [1, 2],"edu" : [1, 2, 3, 4],"incm" : [1, 2, 3, 4]}
    for col in cate_cols:
        if col in categories_dict:
            df_raw[col] = pd.Categorical(df_raw[col], categories=categories_dict[col])

    df_encoded = pd.get_dummies(df_raw, columns=cate_cols, drop_first=True)
    
    # 4. [핵심] 35개 컬럼 틀에 강제로 맞추기
    # 누락된 범주형 컬럼은 0으로 채우고 순서를 완전히 일치시킵니다.
    df_final = df_encoded.reindex(columns=model_features, fill_value=0)
    
    heart_prob = heart_model.predict_proba(df_final)[0]
    
    heart_positive_prob = round(float(heart_prob[1]), 4)
    
    print(">>>>>>> 예측 확률 결과 (1일 확률) : ", heart_positive_prob)
    return heart_positive_prob