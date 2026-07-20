from flask import Flask, request, jsonify
from flask_cors import CORS
from pred.heart_predict import getheartPredict
from db import save_prediction

app = Flask(__name__)
CORS(app)

@app.route("/")
def index():
    return "Flask test"

@app.route("/heart_predict", methods=["POST"])
def getheartPred():
    print("===========getheartPredict============")

    data = request.get_json()
    print(">>>>>>전송받은 파라메터 request.get_json() : ", data)

    if isinstance(data, list):
        features = data
        mem_id = "no_enter"
    elif isinstance(data, dict) and "features" in data:
        features = data["features"]
        mem_id = data.get("mem_id", "no_enter")
    else:
        return jsonify({"error": "잘못된 입력 형식입니다."}), 400

    # 19개 데이터를 받아 내부적으로 35개 확장 후 예측 확률 반환
    result = getheartPredict(features)
    print(f"예측 결과 : {result}")

    # DB 저장 (원본 19개와 결과 확률값 1개 저장)
    try:
        save_prediction(mem_id, features, result)
    except Exception as e:
        print(f"저장 오류 : {e}")

    return jsonify({"probability": result})

if __name__ == "__main__":
    app.run(debug=True)