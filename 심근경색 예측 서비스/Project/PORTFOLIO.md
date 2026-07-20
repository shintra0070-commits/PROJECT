# Full-Stack Project Portfolio

## 1. 프로젝트 개요

### 프로젝트명

심근경색 발생 확률 예측 및 건강 관리 웹 서비스

### 프로젝트 한 줄 소개

사용자의 건강검진 및 생활습관 데이터를 기반으로 심근경색 발생 확률을 예측하고, 예측 이력 관리와 커뮤니티 기능을 제공하는 AI 헬스케어 웹 서비스입니다.

### 프로젝트 목적

본 프로젝트는 건강검진 데이터와 생활습관 데이터를 활용하여 사용자가 자신의 심혈관 질환 위험도를 쉽게 확인하고, 예측 결과를 지속적으로 관리할 수 있도록 돕는 것을 목표로 합니다. 단순한 예측 기능에 그치지 않고, 사용자별 예측 이력 시각화, 건강 정보 제공, 커뮤니티 기능을 함께 제공하여 건강 관리 서비스로 확장할 수 있는 구조를 구현했습니다.

### 핵심 기능 요약

- 건강 데이터 입력 기반 심근경색 발생 확률 예측
- Flask 기반 머신러닝 예측 API 연동
- Spring Boot 기반 회원, 게시판, 댓글, 문의, 예측 이력 관리
- Oracle Database를 활용한 사용자 및 예측 결과 저장
- React 기반 사용자 화면과 라우팅 구성
- Recharts를 활용한 사용자별 예측 이력 그래프 시각화
- Google OAuth 로그인 연동
- 커뮤니티 게시글, 댓글, 좋아요, 인기글 기능
- 고객 문의 및 정책 페이지 제공

## 2. 프로젝트 배경

심혈관 질환은 조기 관리와 생활습관 개선이 중요한 질환입니다. 하지만 일반 사용자가 건강검진 수치를 직접 해석하고 위험도를 판단하기는 어렵습니다. 이 프로젝트는 사용자가 자신의 건강 관련 수치를 입력하면 머신러닝 모델이 심근경색 발생 확률을 예측하고, 결과를 누적 관리할 수 있도록 설계되었습니다.

또한 예측 결과를 단발성으로 제공하는 데서 끝나지 않고, 사용자의 과거 예측 이력을 차트로 보여주어 건강 상태 변화를 확인할 수 있도록 했습니다. 커뮤니티 기능을 통해 건강 관리 경험과 정보를 공유할 수 있는 서비스 형태로 확장했습니다.

## 3. 기술 스택

### Frontend

| 기술               | 사용 목적                        |
| ------------------ | -------------------------------- |
| React              | 사용자 인터페이스 구현           |
| React Router DOM   | 페이지 라우팅                    |
| Axios              | REST API 통신                    |
| Recharts           | 예측 결과 차트 시각화            |
| React OAuth Google | Google 로그인 연동               |
| CSS                | 화면 스타일링 및 반응형 레이아웃 |
| Create React App   | React 개발 환경 구성             |

### Backend

| 기술            | 사용 목적                         |
| --------------- | --------------------------------- |
| Java 17         | Spring Boot 백엔드 개발 언어      |
| Spring Boot     | REST API 서버 구축                |
| Spring Web      | Controller 기반 HTTP API 제공     |
| Spring Data JPA | Oracle DB와 객체 매핑             |
| Lombok          | Entity 및 DTO 코드 간소화         |
| Gradle          | 빌드 및 의존성 관리               |
| Spring DevTools | 개발 중 자동 재시작 및 LiveReload |

### AI / Prediction Server

| 기술              | 사용 목적                |
| ----------------- | ------------------------ |
| Python            | 머신러닝 예측 서버 개발  |
| Flask             | AI 예측 API 서버 구축    |
| Flask-CORS        | 프론트엔드와의 CORS 처리 |
| joblib            | 학습된 모델 파일 로드    |
| numpy             | 입력 데이터 배열 처리    |
| Extra Trees Model | 심근경색 발생 확률 예측  |
| scaler            | 입력 데이터 전처리       |

### Database

| 기술            | 사용 목적                                  |
| --------------- | ------------------------------------------ |
| Oracle Database | 회원, 건강 데이터, 게시판, 댓글, 문의 저장 |
| Oracle JDBC     | Spring Boot와 Oracle DB 연결               |
| python-oracledb | Flask 예측 서버와 Oracle DB 연결           |
| JPA Entity      | Java 객체와 DB 테이블 매핑                 |

### 협업 및 산출물

| 항목             | 내용                     |
| ---------------- | ------------------------ |
| Git              | 버전 관리                |
| ERD              | 데이터베이스 구조 설계   |
| 테이블 정의서    | 테이블 및 컬럼 명세 관리 |
| SQL 스크립트     | DB 생성 및 데이터 관리   |
| Jupyter Notebook | 모델 개발 및 검증        |
| 오차행렬 이미지  | 모델 성능 확인 자료      |

## 4. 시스템 아키텍처

```text
사용자
  |
  v
React Frontend
  |-- /spring/* --> Spring Boot Backend
  |                  |-- Member API
  |                  |-- Data API
  |                  |-- Model Data API
  |                  |-- Community API
  |                  |-- Comment API
  |                  |-- Inquiry API
  |                  |
  |                  v
  |                Oracle DB
  |
  |-- /flask/* --> Flask AI Server
                     |-- Model Load
                     |-- Scaling
                     |-- Prediction
                     |-- Save Result
                     |
                     v
                   Oracle DB
```

### 구조 설명

프론트엔드는 React로 구성되어 있으며, 사용자의 입력과 화면 전환을 담당합니다. API 통신은 Axios를 사용하며, 개발 환경에서는 React Proxy를 통해 Spring Boot 서버와 Flask 서버로 요청을 분기합니다.

Spring Boot는 회원, 커뮤니티, 댓글, 문의, 예측 이력 조회 등 일반적인 서비스 로직을 담당합니다. JPA Repository, Service, Controller 계층으로 구성되어 있으며 Oracle DB와 연동합니다.

Flask 서버는 머신러닝 예측을 전담합니다. React에서 전달받은 건강 데이터를 학습 당시 사용한 scaler로 전처리한 뒤, Extra Trees 기반 모델을 통해 심근경색 발생 확률을 계산합니다. 예측 결과는 Oracle DB에 저장됩니다.

## 5. 개발 환경

| 구분                  | 내용               |
| --------------------- | ------------------ |
| 운영체제              | Windows 개발 환경  |
| Frontend 실행         | `npm start`        |
| Frontend 기본 포트    | `3000`             |
| Spring Boot 실행      | Gradle 기반 실행   |
| Spring Boot 기본 포트 | `8080`             |
| Flask 실행            | `python manage.py` |
| Flask 기본 포트       | `5000`             |
| DB                    | Oracle Database    |
| Java 버전             | 17                 |
| API 통신 방식         | REST API, JSON     |

### 개발환경 버전 정의

아래 표는 프로젝트 저장소의 설정 파일과 의존성 트리를 기준으로 정리한 개발환경 정의입니다. 프론트엔드는 `package-lock.json`, 백엔드는 `build.gradle` 및 Gradle dependency tree, 실행 도구는 현재 로컬 개발 환경 기준으로 확인했습니다.

### 프로그래밍 언어 및 런타임

| 구분               | 이름         | 버전            | 확인 기준                |
| ------------------ | ------------ | --------------- | ------------------------ |
| Frontend Runtime   | Node.js      | `24.14.1`       | 로컬 `node -v`           |
| Package Manager    | npm          | `11.11.0`       | 로컬 `npm.cmd -v`        |
| Frontend Language  | JavaScript   | ECMAScript 기반 | React 프로젝트 소스      |
| Backend Language   | Java         | `17`            | `build.gradle` toolchain |
| Backend Local JDK  | Java Runtime | `17.0.18`       | 로컬 `java -version`     |
| AI Server Language | Python       | `3.13.11`       | 로컬 `python --version`  |

### Frontend 라이브러리 및 프레임워크

| 라이브러리 / 프레임워크     |      선언 버전 | 실제 설치 버전 | 사용 목적                       |
| --------------------------- | -------------: | -------------: | ------------------------------- |
| React                       |      `^19.2.5` |       `19.2.5` | UI 컴포넌트 구현                |
| React DOM                   |      `^19.2.5` |       `19.2.5` | React DOM 렌더링                |
| React Router DOM            |      `^7.15.1` |       `7.15.1` | SPA 라우팅                      |
| React Scripts               |        `5.0.1` |        `5.0.1` | Create React App 개발/빌드 도구 |
| Axios                       |      `^1.16.0` |       `1.16.0` | HTTP API 통신                   |
| Recharts                    |       `^3.8.1` |        `3.8.1` | 예측 이력 차트 시각화           |
| @react-oauth/google         |      `^0.13.5` |       `0.13.5` | Google OAuth 로그인             |
| React YouTube               |      `^10.1.0` |       `10.1.0` | YouTube 컴포넌트 연동           |
| Web Vitals                  |       `^2.1.4` |        `2.1.4` | 웹 성능 측정                    |
| @testing-library/dom        |      `^10.4.1` |       `10.4.1` | 프론트엔드 테스트 유틸          |
| @testing-library/jest-dom   |       `^6.9.1` |        `6.9.1` | Jest DOM Matcher                |
| @testing-library/react      |      `^16.3.2` |       `16.3.2` | React 컴포넌트 테스트           |
| @testing-library/user-event |      `^13.5.0` |       `13.5.0` | 사용자 이벤트 테스트            |
| http-proxy-middleware       | 직접 선언 없음 |        `2.0.9` | React 개발 서버 Proxy 설정      |

참고: `setupProxy.js`에서 `http-proxy-middleware`를 직접 import하고 있으므로, 재현 가능한 설치를 위해 `package.json`의 dependencies에 명시하는 것을 권장합니다.

### Backend 프레임워크 및 라이브러리

| 라이브러리 / 프레임워크             |       버전 | 사용 목적                             |
| ----------------------------------- | ---------: | ------------------------------------- |
| Gradle Wrapper                      |    `9.4.1` | Java 프로젝트 빌드                    |
| Spring Boot Gradle Plugin           |    `4.0.6` | Spring Boot 빌드 플러그인             |
| Spring Dependency Management Plugin |    `1.1.7` | 의존성 버전 관리                      |
| Spring Boot Starter                 |    `4.0.6` | Spring Boot 기본 구성                 |
| Spring Boot Starter Web             |    `4.0.6` | REST API 및 내장 WAS 구성             |
| Spring Boot Starter Data JPA        |    `4.0.6` | JPA 기반 DB 연동                      |
| Spring Boot DevTools                |    `4.0.6` | 개발 중 자동 재시작                   |
| Spring Boot Starter Test            |    `4.0.6` | 백엔드 테스트 구성                    |
| Oracle JDBC Driver ojdbc11          | `21.1.0.0` | Oracle DB 연결                        |
| Lombok                              |  `1.18.46` | Getter, Setter, 생성자 등 코드 간소화 |
| JUnit Platform Launcher             |    `6.0.3` | JUnit 테스트 실행                     |

### Backend 주요 Transitive Dependency

| 라이브러리              |           버전 | 포함 경로 / 사용 목적             |
| ----------------------- | -------------: | --------------------------------- |
| Spring Framework        |        `7.0.7` | Spring Core, Context, Web, WebMVC |
| Spring Data JPA         |        `4.0.5` | JPA Repository                    |
| Spring Data Commons     |        `4.0.5` | Spring Data 공통 기능             |
| Hibernate Core          | `7.2.12.Final` | ORM 구현체                        |
| Tomcat Embed Core       |      `11.0.21` | Spring Boot 내장 WAS              |
| HikariCP                |        `7.0.2` | DB Connection Pool                |
| Jackson Databind        |        `3.1.2` | JSON 직렬화/역직렬화              |
| Jakarta Persistence API |        `3.2.0` | JPA 표준 API                      |
| Jakarta Transaction API |        `2.0.1` | 트랜잭션 API                      |
| Logback Classic         |       `1.5.32` | 로깅 구현체                       |
| SLF4J API               |       `2.0.17` | 로깅 추상화                       |
| JUnit Jupiter           |        `6.0.3` | 테스트 프레임워크                 |
| Mockito Core            |       `5.20.0` | 테스트 Mock 라이브러리            |
| AssertJ Core            |       `3.27.7` | 테스트 Assertion 라이브러리       |

### AI / Flask 서버 라이브러리

현재 `flask_backend`에는 `requirements.txt`, `pyproject.toml`, `Pipfile`, `poetry.lock` 같은 Python 의존성 버전 고정 파일이 없습니다. 따라서 아래 항목은 코드에서 사용 중인 라이브러리이며, 저장소 기준으로 정확한 설치 버전은 미고정 상태입니다.

| 라이브러리      | 저장소 내 버전 명시 여부 | 사용 위치               | 사용 목적                     |
| --------------- | ------------------------ | ----------------------- | ----------------------------- |
| Flask           | 미명시                   | `manage.py`             | AI 예측 API 서버              |
| Flask-CORS      | 미명시                   | `manage.py`             | CORS 허용                     |
| joblib          | 미명시                   | `pred/heart_predict.py` | 학습된 모델 파일 로드         |
| numpy           | 미명시                   | `pred/heart_predict.py` | 입력 데이터 배열 처리         |
| python-oracledb | 미명시                   | `db.py`                 | Flask 서버에서 Oracle DB 저장 |

### 모델 개발 라이브러리

모델 개발용 Jupyter Notebook에서 사용된 라이브러리입니다. 이 역시 별도 의존성 파일이 없어 저장소 기준 버전은 고정되어 있지 않습니다.

| 라이브러리   | 저장소 내 버전 명시 여부 | 사용 목적               |
| ------------ | ------------------------ | ----------------------- |
| pandas       | 미명시                   | 데이터 로드 및 전처리   |
| matplotlib   | 미명시                   | 그래프 시각화           |
| seaborn      | 미명시                   | 통계 시각화             |
| scikit-learn | 미명시                   | 전처리, 모델 학습, 평가 |
| xgboost      | 미명시                   | 비교 모델 학습          |

### 버전 관리 개선 권장 사항

Python 기반 Flask 서버와 모델 개발 환경은 현재 버전이 고정되어 있지 않으므로, 재현 가능한 개발환경을 위해 다음과 같은 `requirements.txt` 추가를 권장합니다.

```text
Flask==버전명
Flask-Cors==버전명
joblib==버전명
numpy==버전명
oracledb==버전명
pandas==버전명
matplotlib==버전명
seaborn==버전명
scikit-learn==버전명
xgboost==버전명
```

포트폴리오 제출 시에는 “프론트엔드와 백엔드는 lock 파일 및 Gradle 의존성 트리 기준으로 버전이 확인되며, Python AI 서버는 requirements 파일 추가를 통해 재현성을 보완할 예정”이라고 설명할 수 있습니다.

### 개발 서버 구성

| 서버        | 주소                     |
| ----------- | ------------------------ |
| React       | `http://localhost:3000`  |
| Spring Boot | `http://localhost:8080`  |
| Flask       | `http://localhost:5000`  |
| Oracle DB   | `210.119.12.109:1521/xe` |

### React Proxy 설정

| React 호출 경로 | 실제 전달 서버          |
| --------------- | ----------------------- |
| `/spring`       | `http://localhost:8080` |
| `/flask`        | `http://localhost:5000` |

## 6. 주요 기능 상세

### 6.1 회원 기능

- 회원 등록
- 회원 정보 조회
- 회원 정보 수정
- 회원 삭제
- 마이페이지 제공
- Google 로그인 연동

### 6.2 건강 데이터 입력 및 예측

- 건강검진 항목 입력
- 생활습관 항목 입력
- 사용자 ID와 입력 데이터를 Flask 서버로 전송
- 머신러닝 모델을 통한 심근경색 발생 확률 예측
- 하루 1회 예측 제한 로직
- 오늘 예측 여부 확인
- 오늘 예측 결과 재조회

### 6.3 예측 결과 관리

- 예측 결과 DB 저장
- 사용자별 예측 이력 조회
- 날짜별 예측 확률 확인
- Recharts 기반 선 그래프 시각화
- 결과 페이지 및 메인 화면에서 예측 상태 확인

### 6.4 커뮤니티 기능

- 게시글 목록 조회
- 게시글 상세 조회
- 게시글 작성
- 게시글 수정
- 게시글 삭제
- 좋아요 기능
- 인기 게시글 조회
- 내가 작성한 게시글 조회
- 카테고리 기반 게시글 관리

### 6.5 댓글 기능

- 게시글별 댓글 조회
- 댓글 작성
- 댓글 수정
- 댓글 삭제
- 게시글 ID, 작성자 ID, 작성일시를 조합한 복합키 기반 댓글 관리

### 6.6 고객 문의 기능

- 문의 등록
- 문의 전체 조회
- 문의 상세 조회
- 문의 수정
- 문의 삭제
- 사용자별 문의 페이징 조회

### 6.7 정보 제공 기능

- 심혈관 건강 관련 외부 정보 링크 제공
- 건강 정보 카드 슬라이드
- 커뮤니티 인기글 미리보기
- 개인정보처리방침
- 이용약관
- 고객센터 페이지

## 7. AI 예측 모델

### 모델 개요

심근경색 발생 가능성을 예측하기 위해 건강검진 및 생활습관 데이터를 입력값으로 사용하는 머신러닝 모델을 적용했습니다. Flask 서버에서는 저장된 `best_extra_trees_model.pkl` 파일을 로드하며, 해당 파일에는 학습된 모델과 scaler가 함께 포함되어 있습니다.

### 예측 처리 흐름

```text
1. 사용자가 React 화면에서 건강 데이터를 입력
2. React가 Flask API `/heart_predict`로 데이터 전송
3. Flask 서버가 입력 데이터를 numpy 배열로 변환
4. scaler를 사용하여 입력값 전처리
5. Extra Trees 모델의 `predict_proba()`로 확률 계산
6. 심근경색 양성 클래스 확률 반환
7. 예측 결과를 Oracle DB에 저장
8. React가 결과 페이지에서 예측 확률 표시
```

### 주요 입력 변수

- 고혈압 의사 진단 여부
- 이상지질혈증 의사 진단 여부
- 당뇨병 의사 진단 여부
- 뇌졸중 의사 진단 여부
- 혈압 유형
- 공복 혈당
- 당화혈색소
- 총 콜레스테롤
- 중성지방
- BMI
- 허리둘레
- 흡연 여부
- 음주 빈도
- 1회 음주량
- 하루 앉아서 보내는 시간
- 유산소 신체활동 여부
- 성별
- 나이
- 최종 학력
- 소득 분위

### 예측 응답 예시

```json
{
  "probability": 0.7123
}
```

## 8. 데이터베이스 설계

### 주요 테이블

| 테이블           | 설명                     |
| ---------------- | ------------------------ |
| `member_test`    | 회원 정보                |
| `data_test`      | 건강 데이터 및 예측 결과 |
| `community_test` | 커뮤니티 게시글          |
| `comment_test`   | 게시글 댓글              |
| `INQUIRY_TEST`   | 고객 문의                |

### 주요 Entity

#### Member

| 필드           | 설명     |
| -------------- | -------- |
| `mem_id`       | 회원 ID  |
| `mem_phone`    | 전화번호 |
| `mem_name`     | 이름     |
| `mem_nickname` | 닉네임   |

#### Data / Model

| 필드         | 설명                   |
| ------------ | ---------------------- |
| `data_id`    | 데이터 ID              |
| `mem_id`     | 회원 ID                |
| `check_date` | 검사 또는 예측 날짜    |
| `sex`        | 성별                   |
| `age`        | 나이                   |
| `edu`        | 학력                   |
| `incm`       | 소득 분위              |
| `di1_dg`     | 고혈압 진단 여부       |
| `di2_dg`     | 이상지질혈증 진단 여부 |
| `de1_dg`     | 당뇨병 진단 여부       |
| `di3_dg`     | 뇌졸중 진단 여부       |
| `he_hp`      | 혈압 유형              |
| `he_glu`     | 공복 혈당              |
| `he_hba1c`   | 당화혈색소             |
| `he_chol`    | 총 콜레스테롤          |
| `he_tg`      | 중성지방               |
| `he_wc`      | 허리둘레               |
| `he_bmi`     | BMI                    |
| `bs1_1`      | 흡연 여부              |
| `bd1_11`     | 음주 빈도              |
| `bd2_1`      | 1회 음주량             |
| `pa_aerobic` | 유산소 신체활동        |
| `be8_1`      | 앉아서 보내는 시간     |
| `predict`    | 예측 확률              |

#### Community

| 필드           | 설명      |
| -------------- | --------- |
| `com_id`       | 게시글 ID |
| `mem_id`       | 작성자 ID |
| `com_title`    | 제목      |
| `com_content`  | 내용      |
| `com_view`     | 조회수    |
| `com_like`     | 좋아요 수 |
| `com_category` | 카테고리  |
| `com_created`  | 작성일    |

#### Comment

| 필드             | 설명          |
| ---------------- | ------------- |
| `comid`          | 게시글 ID     |
| `memid`          | 작성자 ID     |
| `commentcreated` | 댓글 작성일시 |
| `commentcontent` | 댓글 내용     |

#### Inquiry

| 필드          | 설명      |
| ------------- | --------- |
| `inq_id`      | 문의 ID   |
| `mem_id`      | 작성자 ID |
| `inq_title`   | 문의 제목 |
| `inq_content` | 문의 내용 |
| `inq_created` | 작성일    |

## 9. API 인터페이스 요약

### 회원 API

| 기능             | Method | URL                       |
| ---------------- | ------ | ------------------------- |
| 회원 목록 조회   | GET    | `/member/list`            |
| 회원 상세 조회   | GET    | `/member/view/{mem_id}`   |
| 회원 등록        | POST   | `/member/insert`          |
| 회원 수정        | PUT    | `/member/update`          |
| 회원 삭제        | DELETE | `/member/delete/{mem_id}` |
| 회원 페이징 조회 | GET    | `/member/list_paging`     |

### 건강 데이터 API

| 기능               | Method | URL                      |
| ------------------ | ------ | ------------------------ |
| 데이터 목록 조회   | GET    | `/data/list`             |
| 데이터 상세 조회   | GET    | `/data/view/{data_id}`   |
| 데이터 등록        | POST   | `/data/insert`           |
| 데이터 삭제        | DELETE | `/data/delete/{data_id}` |
| 데이터 페이징 조회 | GET    | `/data/list_paging`      |

### 예측 이력 API

| 기능                    | Method | URL                         |
| ----------------------- | ------ | --------------------------- |
| 사용자별 예측 이력 조회 | GET    | `/modeldata/view/{mem_id}`  |
| 오늘 예측 여부 확인     | GET    | `/modeldata/check/{mem_id}` |
| 오늘 예측값 조회        | GET    | `/modeldata/today/{mem_id}` |

### Flask AI API

| 기능               | Method | URL              |
| ------------------ | ------ | ---------------- |
| Flask 연결 확인    | GET    | `/`              |
| 심근경색 확률 예측 | POST   | `/heart_predict` |

### 커뮤니티 API

| 기능                | Method | URL                            |
| ------------------- | ------ | ------------------------------ |
| 게시글 목록 조회    | GET    | `/community/list`              |
| 게시글 상세 조회    | GET    | `/community/view/{com_id}`     |
| 게시글 등록         | POST   | `/community/insert`            |
| 게시글 수정         | PUT    | `/community/update`            |
| 게시글 삭제         | DELETE | `/community/delete/{com_id}`   |
| 좋아요              | POST   | `/community/like/{com_id}`     |
| 게시글 페이징 조회  | GET    | `/community/list_paging`       |
| 인기 게시글 조회    | GET    | `/community/top_list`          |
| 내가 쓴 게시글 조회 | GET    | `/community/board/my/{mem_id}` |

### 댓글 API

| 기능      | Method | URL                     |
| --------- | ------ | ----------------------- |
| 댓글 조회 | GET    | `/comment/list/{comid}` |
| 댓글 등록 | POST   | `/comment/insert`       |
| 댓글 수정 | PUT    | `/comment/update`       |
| 댓글 삭제 | DELETE | `/comment/delete`       |

### 문의 API

| 기능             | Method | URL                        |
| ---------------- | ------ | -------------------------- |
| 문의 목록 조회   | GET    | `/inquiry/list`            |
| 문의 상세 조회   | GET    | `/inquiry/view/{inq_id}`   |
| 문의 등록        | POST   | `/inquiry/insert`          |
| 문의 수정        | PUT    | `/inquiry/update`          |
| 문의 삭제        | DELETE | `/inquiry/delete/{inq_id}` |
| 문의 페이징 조회 | GET    | `/inquiry/list_paging`     |

## 10. 프론트엔드 구성

### 주요 페이지

| 페이지                        | 설명                                         |
| ----------------------------- | -------------------------------------------- |
| HomePage                      | 메인 화면, 예측 이력 차트, 건강 정보, 인기글 |
| LoginPage                     | 로그인 화면                                  |
| SignupPage / MemberInsertPage | 회원 가입                                    |
| MyPage                        | 회원 정보 및 내 활동 관리                    |
| AnalysisPage                  | 건강 데이터 입력 및 예측 요청                |
| ResultPage                    | 예측 결과 확인                               |
| CommunityPage                 | 커뮤니티 목록                                |
| CommunityDetailPage           | 게시글 상세 및 댓글                          |
| CommunityWritePage            | 게시글 작성                                  |
| CommunityEditPage             | 게시글 수정                                  |
| CustomerCenterPage            | 문의 작성                                    |
| PrivacyPolicyPage             | 개인정보처리방침                             |
| TermsPage                     | 이용약관                                     |

### 라우팅 구조

| 경로                      | 페이지           |
| ------------------------- | ---------------- |
| `/`                       | 홈               |
| `/login`                  | 로그인           |
| `/signup`                 | 회원가입         |
| `/analysis`               | 건강 데이터 입력 |
| `/result`                 | 예측 결과        |
| `/mypage`                 | 마이페이지       |
| `/community`              | 커뮤니티 목록    |
| `/community/write`        | 글 작성          |
| `/community/edit/:postId` | 글 수정          |
| `/community/:postId`      | 글 상세          |
| `/privacy-policy`         | 개인정보처리방침 |
| `/terms`                  | 이용약관         |
| `/customer-center`        | 고객센터         |

## 11. 백엔드 구조

Spring Boot 백엔드는 Controller, Service, Repository, Model 계층으로 구성되어 있습니다.

```text
controller
  |-- MemberController
  |-- DataController
  |-- ModelController
  |-- CommunityController
  |-- CommentController
  |-- InquiryController

service
  |-- MemberService
  |-- DataService
  |-- ModelService
  |-- CommunityService
  |-- CommentService
  |-- InquiryService

repository
  |-- MemberRepository
  |-- DataRepository
  |-- ModelRepository
  |-- CommunityRepository
  |-- CommentRepository
  |-- InquiryRepository

model
  |-- Member
  |-- Data
  |-- Model
  |-- Community
  |-- Comment
  |-- Inquiry
```

### 설계 특징

- REST API 중심의 백엔드 설계
- Entity와 Repository를 활용한 JPA 기반 DB 접근
- Service 계층을 통해 비즈니스 로직 분리
- ResponseEntity를 활용한 HTTP 상태 코드 반환
- 페이징 API 일부 구현
- Oracle DB 테이블과 Java Entity 매핑

## 12. 개발 방법론

### 개발 접근 방식

본 프로젝트는 프론트엔드, 백엔드, AI 예측 서버, 데이터베이스가 함께 동작하는 풀스택 프로젝트이므로 각 영역을 기능 단위로 나누고, API를 기준으로 점진적으로 통합하는 방식으로 개발했습니다.

### 적용한 개발 방법론

| 방법론           | 적용 내용                                                                                |
| ---------------- | ---------------------------------------------------------------------------------------- |
| 반복적 개발      | 회원, 예측, 커뮤니티, 댓글, 문의 기능을 순차적으로 구현하고 화면과 API를 반복적으로 연결 |
| 점진적 통합      | React 화면, Spring Boot API, Flask 예측 API, Oracle DB를 단계적으로 연동                 |
| 기능 중심 개발   | 로그인, 예측, 결과 조회, 게시판, 댓글 등 사용자 기능 단위로 개발 범위 분리               |
| API 중심 개발    | 프론트엔드와 백엔드 간 통신 규격을 REST API 중심으로 구성                                |
| 계층 분리 개발   | Controller, Service, Repository, Entity 계층을 분리하여 유지보수성 확보                  |
| 데이터 중심 개발 | DB 테이블 정의서, ERD, SQL 스크립트를 기반으로 Entity와 API 설계                         |
| 모델 연동형 개발 | Jupyter Notebook에서 개발한 머신러닝 모델을 Flask API 서버로 분리하여 웹 서비스에 통합   |

### 개발 흐름

```text
1. 요구 기능 정의
2. DB 테이블 및 Entity 설계
3. Spring Boot CRUD API 구현
4. React 화면 및 라우팅 구성
5. Axios를 통한 API 연동
6. Flask 예측 API 구현
7. 머신러닝 모델 연동
8. 예측 결과 DB 저장
9. 사용자별 이력 조회 및 시각화
10. 기능 테스트 및 화면 개선
```

### 개발 방법론 관점의 특징

- 화면, API, DB, AI 서버를 한 번에 구현하지 않고 기능 단위로 나누어 개발했습니다.
- React와 Spring Boot는 REST API를 기준으로 연결하여 프론트엔드와 백엔드의 역할을 분리했습니다.
- AI 모델은 Flask 서버로 분리하여 일반 백엔드와 예측 서버의 책임을 구분했습니다.
- DB 산출물을 기반으로 Entity와 Repository를 구성하여 데이터 구조와 백엔드 코드의 일관성을 유지했습니다.
- 예측 결과 저장, 이력 조회, 차트 시각화까지 연결하여 단일 기능이 실제 서비스 흐름으로 이어지도록 구현했습니다.

## 13. 디자인 패턴 및 설계 패턴

### 전체 설계 관점

이 프로젝트는 React 프론트엔드, Spring Boot 백엔드, Flask AI 서버, Oracle DB를 분리한 구조로 설계되었습니다. 각 영역은 독립적인 책임을 가지며 REST API를 통해 데이터를 주고받습니다.

### 적용된 주요 패턴

| 패턴                         | 적용 위치           | 설명                                                                                       |
| ---------------------------- | ------------------- | ------------------------------------------------------------------------------------------ |
| MVC Pattern                  | Spring Boot Backend | Controller가 요청을 받고, Service가 비즈니스 로직을 처리하며, Model/Entity가 데이터를 표현 |
| Layered Architecture         | Backend 전체        | Controller, Service, Repository, Entity 계층을 분리하여 역할과 책임을 명확히 구분          |
| Repository Pattern           | Spring Data JPA     | DB 접근 로직을 Repository 인터페이스로 분리하여 데이터 접근 계층 추상화                    |
| Dependency Injection         | Spring Boot         | Service, Repository 객체를 직접 생성하지 않고 Spring Container가 주입                      |
| Singleton Bean Pattern       | Spring Boot Bean    | Controller, Service, Repository가 Spring Bean으로 관리되어 애플리케이션 내에서 재사용      |
| RESTful API Pattern          | Spring Boot, Flask  | HTTP Method와 URL을 기준으로 자원 중심 API 구성                                            |
| Component-Based Architecture | React Frontend      | Navbar, Footer, LoginButton, Page 등 UI를 컴포넌트 단위로 분리                             |
| Routing Pattern              | React Router        | 기능별 페이지를 URL 경로에 매핑하여 SPA 구조 구성                                          |
| Context Provider Pattern     | React AuthContext   | 로그인 사용자 상태를 전역에서 공유할 수 있도록 Context 구조 사용                           |
| Proxy Pattern                | React 개발 환경     | `/spring`, `/flask` 경로를 각각 Spring Boot와 Flask 서버로 전달                            |

### MVC Pattern 적용

Spring Boot 백엔드는 MVC 구조를 기반으로 구현되었습니다.

```text
Controller
  사용자의 HTTP 요청을 수신하고 응답 반환

Service
  비즈니스 로직 처리

Repository
  DB 접근 처리

Entity / Model
  DB 테이블과 매핑되는 데이터 객체
```

예시:

```text
MemberController
  -> MemberService
    -> MemberRepository
      -> member_test Table
```

### Layered Architecture 적용

백엔드는 기능별로 Controller, Service, Repository, Model 패키지를 분리했습니다. 이 구조는 각 계층의 책임을 명확히 하여 기능 수정 시 영향 범위를 줄이고, 유지보수성을 높이는 데 도움이 됩니다.

```text
controller: API 요청과 응답 처리
service: 핵심 비즈니스 로직 처리
repository: 데이터베이스 접근
model: Entity 및 데이터 구조 정의
```

### Repository Pattern 적용

Spring Data JPA Repository를 사용하여 DB 접근 로직을 추상화했습니다. Controller나 Service가 SQL을 직접 다루지 않고 Repository 메서드를 통해 데이터 조회, 저장, 수정, 삭제를 수행합니다.

적용 예시:

- `MemberRepository`
- `DataRepository`
- `ModelRepository`
- `CommunityRepository`
- `CommentRepository`
- `InquiryRepository`

### Dependency Injection 적용

Spring Boot에서 `@RequiredArgsConstructor`, `@Autowired` 등을 사용하여 필요한 객체를 외부에서 주입받도록 구성했습니다. 이를 통해 객체 간 결합도를 낮추고 테스트와 유지보수에 유리한 구조를 만들었습니다.

예시:

```java
private final MemberService memberService;
```

### RESTful API 설계

HTTP Method를 기능에 맞게 구분하여 API를 설계했습니다.

| Method | 사용 목적                    |
| ------ | ---------------------------- |
| GET    | 목록 조회, 상세 조회         |
| POST   | 등록, 예측 요청, 좋아요 처리 |
| PUT    | 수정                         |
| DELETE | 삭제                         |

예시:

```text
GET    /member/list
GET    /member/view/{mem_id}
POST   /member/insert
PUT    /member/update
DELETE /member/delete/{mem_id}
```

### React 컴포넌트 기반 설계

프론트엔드는 화면과 기능을 컴포넌트 단위로 분리했습니다.

```text
components
  공통 UI 컴포넌트

pages
  실제 페이지 화면

router
  페이지 라우팅 관리

springApi / flaskApi
  API 호출 함수 분리

styles
  화면별 CSS 관리
```

이 구조를 통해 UI, 라우팅, API 호출, 스타일을 분리하여 화면 수정과 기능 확장이 쉬운 형태로 구성했습니다.

### 서버 분리 설계

일반 서비스 API와 AI 예측 API를 하나의 서버에 모두 넣지 않고 Spring Boot와 Flask로 분리했습니다.

| 서버        | 책임                                     |
| ----------- | ---------------------------------------- |
| React       | 사용자 화면, 입력, 결과 표시             |
| Spring Boot | 회원, 게시판, 댓글, 문의, 예측 이력 관리 |
| Flask       | 머신러닝 모델 로드 및 예측 수행          |
| Oracle DB   | 서비스 데이터 저장                       |

이 구조는 AI 모델이 변경되더라도 Spring Boot의 일반 서비스 로직과 분리하여 관리할 수 있다는 장점이 있습니다.

### 포트폴리오에 강조할 수 있는 설계 문장

본 프로젝트는 MVC 패턴과 계층형 아키텍처를 기반으로 Spring Boot 백엔드를 구성했으며, Repository Pattern과 Dependency Injection을 활용해 데이터 접근 로직과 비즈니스 로직의 결합도를 낮췄습니다. 또한 React는 컴포넌트 기반 구조로 화면을 분리하고, Flask 예측 서버를 별도로 구성하여 AI 모델과 일반 서비스 API의 책임을 명확히 나누었습니다.

## 14. 프로젝트에서 담당하거나 강조할 수 있는 구현 포인트

포트폴리오에서 다음 항목을 강점으로 강조할 수 있습니다.

- React, Spring Boot, Flask를 분리한 풀스택 구조 설계
- 머신러닝 모델을 웹 서비스에 API 형태로 통합
- React Proxy를 활용하여 서로 다른 서버의 API 연동
- 사용자 입력 데이터를 기반으로 예측 API 호출 및 결과 페이지 연결
- 예측 결과를 DB에 저장하고 사용자별로 이력 조회
- Recharts를 활용한 예측 결과 시각화
- Spring Data JPA를 활용한 CRUD API 구현
- 커뮤니티, 댓글, 문의 기능을 포함한 서비스형 웹 애플리케이션 구현
- Oracle DB 기반의 테이블 설계 및 연동
- Google 소셜 로그인 연동 시도 및 구현

## 15. 문제 해결 경험

### 13.1 React와 Spring Boot, Flask 간 CORS 문제

프론트엔드, Spring Boot, Flask가 각각 다른 포트에서 실행되기 때문에 브라우저 CORS 문제가 발생할 수 있었습니다. 이를 해결하기 위해 React의 `setupProxy.js`를 사용하여 `/spring`, `/flask` 경로를 각각 Spring Boot와 Flask 서버로 전달하도록 설정했습니다.

### 13.2 AI 모델과 웹 서비스 연동

학습된 모델을 단순히 Notebook에서 사용하는 것이 아니라 Flask API 서버로 분리하여 웹 서비스에서 호출할 수 있도록 구현했습니다. 입력 데이터를 배열 형태로 전달하고, Flask 서버에서 scaler와 모델을 로드하여 예측 확률을 반환하도록 구성했습니다.

### 13.3 예측 결과 저장

예측 결과를 사용자에게 보여주는 것뿐 아니라 Oracle DB에 저장하여 이후 사용자별 예측 이력 조회와 차트 시각화에 활용했습니다. 이를 통해 AI 예측 기능과 일반적인 웹 서비스 기능을 연결했습니다.

### 13.4 하루 1회 예측 제한

동일 사용자가 하루에 여러 번 예측을 수행하지 않도록 Spring Boot의 `/modeldata/check/{mem_id}` API를 통해 오늘 예측 여부를 확인했습니다. 이미 예측한 경우 기존 예측 결과를 조회하여 결과 페이지로 이동하도록 처리했습니다.

## 16. 보완 및 개선 가능 사항

### 기능 개선

- JWT 기반 인증 및 인가 적용
- 로그인 상태 관리 안정화
- 관리자 페이지 추가
- 예측 결과 해석 문구 제공
- 위험도별 건강 관리 가이드 제공
- 게시글 검색 및 필터링 강화
- 댓글 좋아요 및 대댓글 기능 추가

### 기술 개선

- 환경변수 기반 DB 접속 정보 관리
- API 응답 DTO 분리
- 전역 예외 처리 추가
- Swagger/OpenAPI 문서 자동화
- Spring Security 적용
- CI/CD 파이프라인 구성
- 테스트 코드 보강

### AI 모델 개선

- 모델 성능 지표 정리
- Feature importance 시각화
- 모델 재학습 파이프라인 구성
- 입력값 검증 및 이상치 처리
- 예측 결과의 신뢰도 및 설명 가능성 강화

## 17. 보안 및 운영 관점

현재 프로젝트는 개발 환경 중심으로 구성되어 있으므로 실제 서비스 운영을 위해서는 다음 항목의 개선이 필요합니다.

- DB 계정, 비밀번호, OAuth Client ID 등 민감 정보 환경변수화
- HTTPS 적용
- 인증 토큰 기반 API 보호
- CORS 허용 범위 제한
- 입력값 검증 및 서버 측 Validation 추가
- SQL 및 API 오류 응답 표준화
- 개인정보 저장 항목 최소화
- 로그에 개인정보가 노출되지 않도록 처리

## 18. 포트폴리오에 넣기 좋은 문장

### 프로젝트 소개 문장

React, Spring Boot, Flask, Oracle DB를 활용하여 사용자의 건강 데이터를 기반으로 심근경색 발생 확률을 예측하고, 예측 이력을 시각화하는 AI 헬스케어 웹 서비스를 개발했습니다.

### 기술적 강조 문장

프론트엔드, 서비스 백엔드, AI 예측 서버를 분리한 구조로 설계하여 각 서버의 역할을 명확히 나누었고, React Proxy와 REST API를 통해 서로 다른 서버 간 통신을 구현했습니다.

### AI 연동 강조 문장

Jupyter Notebook에서 개발한 머신러닝 모델을 Flask API 서버로 배포 가능한 형태로 분리하고, React 화면에서 입력한 건강 데이터를 실시간으로 예측 서버에 전달하여 예측 확률을 반환받도록 구현했습니다.

### 데이터 관리 강조 문장

예측 결과를 단순히 화면에 표시하는 데 그치지 않고 Oracle DB에 저장하여 사용자별 예측 이력을 조회하고, Recharts 기반 그래프로 건강 상태 변화를 확인할 수 있도록 구현했습니다.

### 백엔드 강조 문장

Spring Boot와 Spring Data JPA를 활용해 회원, 건강 데이터, 커뮤니티, 댓글, 문의 기능의 CRUD API를 구현했으며, Controller-Service-Repository 계층 구조를 적용했습니다.

## 19. 이력서용 요약

### 프로젝트명

AI 기반 심근경색 발생 확률 예측 웹 서비스

### 기간

프로젝트 진행 기간 입력

### 인원

프로젝트 참여 인원 입력

### 사용 기술

React, Java, Spring Boot, Spring Data JPA, Python, Flask, Oracle Database, Axios, Recharts, Gradle, npm

### 주요 구현 내용

- React 기반 건강 데이터 입력 및 예측 결과 화면 구현
- Axios와 Proxy를 활용한 Spring Boot, Flask API 연동
- Spring Boot 기반 회원, 커뮤니티, 댓글, 문의 CRUD API 구현
- Flask 기반 머신러닝 예측 API 구현
- Extra Trees 모델을 활용한 심근경색 발생 확률 예측
- Oracle DB에 사용자 정보 및 예측 결과 저장
- 사용자별 예측 이력 조회 및 차트 시각화

## 20. README용 실행 방법

### Frontend 실행

```bash
cd Project2_1/frontend
npm install
npm start
```

### Spring Boot 실행

```bash
cd Project2_1/backend
./gradlew bootRun
```

Windows 환경:

```bash
cd Project2_1/backend
gradlew.bat bootRun
```

### Flask 실행

```bash
cd Project2_1/flask_backend
python manage.py
```

### 접속 주소

```text
React: http://localhost:3000
Spring Boot: http://localhost:8080
Flask: http://localhost:5000
```

## 21. 발표용 핵심 흐름

```text
1. 사용자가 회원가입 또는 로그인
2. 건강 데이터 입력 페이지에서 검진 및 생활습관 정보 입력
3. React가 Flask 예측 API로 입력값 전달
4. Flask가 모델을 통해 심근경색 발생 확률 계산
5. 예측 결과를 Oracle DB에 저장
6. React 결과 페이지에서 예측 확률 표시
7. 메인 화면과 마이페이지에서 예측 이력을 그래프로 확인
8. 커뮤니티에서 건강 정보와 경험 공유
```

## 22. 최종 프로젝트 요약

이 프로젝트는 단순 CRUD 중심 웹 애플리케이션을 넘어, 머신러닝 예측 모델을 실제 웹 서비스 흐름에 통합한 풀스택 프로젝트입니다. React는 사용자 인터페이스와 데이터 시각화를 담당하고, Spring Boot는 일반 서비스 API와 DB 관리를 담당하며, Flask는 AI 예측 API를 담당합니다. Oracle DB를 중심으로 회원 정보, 예측 결과, 커뮤니티 데이터를 저장하여 서비스의 지속성을 확보했습니다.

프로젝트를 통해 프론트엔드, 백엔드, 데이터베이스, AI 모델 서버를 연결하는 전체 흐름을 경험했으며, REST API 설계, 서버 간 통신, 데이터 저장, 예측 결과 시각화 등 풀스택 개발에 필요한 핵심 역량을 종합적으로 구현했습니다.
