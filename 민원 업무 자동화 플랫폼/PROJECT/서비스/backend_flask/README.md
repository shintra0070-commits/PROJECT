# Gemma 민원 처리 Flask 백엔드

`unit_model_욕설부분수정전.ipynb`의 욕설/무의미 규칙과 두 Gemma 프롬프트가 `complaint_backend/model_service.py`에 직접 포함되어 있습니다. 실행 시 노트북이나 별도 프롬프트 파일을 읽지 않습니다. DB DDL이나 `create_all()`은 수행하지 않으므로 기존 Oracle 스키마를 변경하지 않습니다.

## 실행

1. `.env.example`을 `.env`로 복사하고 DB/Ollama 값을 확인합니다.
2. Ollama에 `gemma` 모델이 준비되어 있어야 합니다.
3. `pip install -r requirements.txt`
4. `python app.py` (기본 포트 5001)

`FLASK_ENV=development`에서는 Flask 개발 서버를 사용하고, 그 외 환경에서는 Waitress WSGI 서버로 실행됩니다. 프론트엔드는 `REACT_APP_COMPLAINT_API_URL`로 이 서비스 주소를 지정합니다.

정상 민원만 `REFINED_CONTENT`가 채워지고 `SEPARATED_COMPLAINT` 행이 생성됩니다. `WARNING`, `NONSENSE`, `NONSENTENCE`는 `REFINED_CONTENT=NULL`이며 분리 민원을 만들지 않습니다.

프론트 요청: `POST /api/complaints`

```json
{"title":"제목", "content":"내용", "isPublic":"private", "password":"1234", "name":"홍길동", "phone":"010-0000-0000"}
```

회원은 `name`, `phone` 대신 기존 `USER_INFO.ACCOUNT_ID` 값인 `userId`를 보냅니다. 공무원 목록은 `GET /api/complaints/officer`에서 조회합니다.
