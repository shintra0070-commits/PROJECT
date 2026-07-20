# AI 기반 심근경색 발생 확률 예측 웹 서비스

## 1. 프로젝트 소개

사용자의 건강검진 및 생활습관 데이터를 기반으로 심근경색 발생 확률을 예측하고, 예측 이력 관리와 커뮤니티 기능을 제공하는 풀스택 AI 헬스케어 웹 서비스입니다.

React 프론트엔드, Spring Boot 백엔드, Flask AI 예측 서버, Oracle Database를 분리하여 구성했으며, 머신러닝 모델을 실제 웹 서비스 흐름에 연동한 프로젝트입니다.

## 2. 주요 기능

- 회원 가입, 회원 조회, 회원 수정, 회원 삭제
- Google OAuth 로그인 연동
- 건강검진 및 생활습관 데이터 입력
- Flask 기반 AI 예측 API를 통한 심근경색 발생 확률 예측
- 사용자별 예측 결과 DB 저장
- 하루 1회 예측 여부 확인
- 사용자별 예측 이력 조회
- Recharts 기반 예측 결과 그래프 시각화
- 커뮤니티 게시글 CRUD
- 댓글 작성, 수정, 삭제
- 게시글 좋아요 및 인기글 조회
- 고객 문의 등록, 조회, 수정, 삭제
- 개인정보처리방침, 이용약관, 고객센터 페이지 제공

## 3. 기술 스택

### Frontend

| 기술                |   버전 | 사용 목적                  |
| ------------------- | -----: | -------------------------- |
| React               | 19.2.5 | UI 구현                    |
| React DOM           | 19.2.5 | DOM 렌더링                 |
| React Router DOM    | 7.15.1 | SPA 라우팅                 |
| Axios               | 1.16.0 | REST API 통신              |
| Recharts            |  3.8.1 | 차트 시각화                |
| React Scripts       |  5.0.1 | Create React App 개발/빌드 |
| @react-oauth/google | 0.13.5 | Google OAuth 로그인        |
| Web Vitals          |  2.1.4 | 웹 성능 측정               |

### Backend

| 기술                |         버전 | 사용 목적             |
| ------------------- | -----------: | --------------------- |
| Java                |           17 | 백엔드 개발 언어      |
| Spring Boot         |        4.0.6 | REST API 서버         |
| Spring Framework    |        7.0.7 | Spring Core, Web, MVC |
| Spring Data JPA     |        4.0.5 | JPA Repository        |
| Hibernate Core      | 7.2.12.Final | ORM                   |
| Oracle JDBC ojdbc11 |     21.1.0.0 | Oracle DB 연결        |
| Lombok              |      1.18.46 | 코드 간소화           |
| Gradle Wrapper      |        9.4.1 | 빌드 도구             |
| Tomcat Embed Core   |      11.0.21 | 내장 WAS              |
| HikariCP            |        7.0.2 | DB Connection Pool    |

### AI / Flask Server

현재 Python 의존성은 별도 `requirements.txt`로 버전이 고정되어 있지 않습니다.

| 기술            | 버전    | 사용 목적                     |
| --------------- | ------- | ----------------------------- |
| Python          | 3.13.11 | AI 예측 서버 개발 언어        |
| Flask           | 미고정  | 예측 API 서버                 |
| Flask-CORS      | 미고정  | CORS 처리                     |
| joblib          | 미고정  | 학습 모델 로드                |
| numpy           | 미고정  | 입력 데이터 배열 처리         |
| python-oracledb | 미고정  | Flask 서버에서 Oracle DB 저장 |
| scikit-learn    | 미고정  | 모델 학습 및 예측             |
| pandas          | 미고정  | 모델 개발 데이터 처리         |
| matplotlib      | 미고정  | 모델 개발 시각화              |
| seaborn         | 미고정  | 모델 개발 시각화              |
| xgboost         | 미고정  | 비교 모델 학습                |

### Database

| 기술        | 내용                                                                         |
| ----------- | ---------------------------------------------------------------------------- |
| DBMS        | Oracle Database                                                              |
| 접속 정보   | `210.119.12.109:1521/xe`                                                     |
| 주요 테이블 | `member_test`, `data_test`, `community_test`, `comment_test`, `INQUIRY_TEST` |

## 4. 개발 환경

| 구분             | 내용              |
| ---------------- | ----------------- |
| OS               | Windows 개발 환경 |
| Node.js          | 24.14.1           |
| npm              | 11.11.0           |
| Java Runtime     | 17.0.18           |
| Python           | 3.13.11           |
| Frontend Port    | 3000              |
| Spring Boot Port | 8080              |
| Flask Port       | 5000              |
| API 형식         | REST API, JSON    |

## 5. 시스템 아키텍처

```text
User
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
  |                  v
  |                Oracle DB
  |
  |-- /flask/* --> Flask AI Server
                     |-- Load ML Model
                     |-- Scale Input Data
                     |-- Predict Probability
                     |-- Save Result
                     v
                   Oracle DB
```

## 6. 프로젝트 폴더 구조

```text
Project2_1
  |-- frontend
  |   |-- public
  |   |-- src
  |   |   |-- assets
  |   |   |-- components
  |   |   |-- config
  |   |   |-- flaskApi
  |   |   |-- pages
  |   |   |-- router
  |   |   |-- springApi
  |   |   `-- styles
  |   |-- package.json
  |   `-- package-lock.json
  |
  |-- backend
  |   |-- src/main/java/com/pknu/backend
  |   |   |-- controller
  |   |   |-- model
  |   |   |-- repository
  |   |   `-- service
  |   |-- src/main/resources/application.properties
  |   |-- build.gradle
  |   `-- gradlew.bat
  |
  |-- flask_backend
  |   |-- manage.py
  |   |-- db.py
  |   |-- config.py
  |   |-- pred
  |   `-- models
  |
  |-- model_developer
  |   |-- label.ipynb
  |   |-- model_graph.ipynb
  |   |-- 이미지들
  |   `-- 데이터사용지침
  |
  `-- DB산출물
      |-- ERD
      |-- 테이블정의서
      `-- SQL 스크립트
```

## 7. 실행 방법

### Frontend 실행

```bash
cd Project2_1/frontend
npm install
npm start
```

접속 주소:

```text
http://localhost:3000
```

### Spring Boot Backend 실행

Windows:

```bash
cd Project2_1/backend
gradlew.bat bootRun
```

Linux / macOS:

```bash
cd Project2_1/backend
./gradlew bootRun
```

기본 주소:

```text
http://localhost:8080
```

### Flask AI Server 실행

```bash
cd Project2_1/flask_backend
python manage.py
```

기본 주소:

```text
http://localhost:5000
```

## 8. React Proxy 설정

프론트엔드는 개발 환경에서 `src/setupProxy.js`를 통해 Spring Boot와 Flask 서버로 요청을 분기합니다.

| React 호출 경로 | 실제 서버               |
| --------------- | ----------------------- |
| `/spring`       | `http://localhost:8080` |
| `/flask`        | `http://localhost:5000` |

예시:

```text
/spring/member/list  ->  http://localhost:8080/member/list
/flask/heart_predict ->  http://localhost:5000/heart_predict
```

## 9. 주요 API

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

### AI 예측 API

| 기능               | Method | URL              |
| ------------------ | ------ | ---------------- |
| Flask 연결 확인    | GET    | `/`              |
| 심근경색 확률 예측 | POST   | `/heart_predict` |

요청 예시:

```json
{
  "mem_id": "user01",
  "features": [
    0, 0, 0, 0, 2, 115.0, 6.1, 220.0, 180.0, 25.5, 88.0, 2, 3, 2, 8, 2, 1, 55,
    3, 2
  ]
}
```

응답 예시:

```json
{
  "probability": 0.7123
}
```

### 커뮤니티 API

| 기능                | Method | URL                            |
| ------------------- | ------ | ------------------------------ |
| 게시글 목록 조회    | GET    | `/community/list`              |
| 게시글 상세 조회    | GET    | `/community/view/{com_id}`     |
| 게시글 등록         | POST   | `/community/insert`            |
| 게시글 수정         | PUT    | `/community/update`            |
| 게시글 삭제         | DELETE | `/community/delete/{com_id}`   |
| 좋아요              | POST   | `/community/like/{com_id}`     |
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

## 10. 데이터베이스 구성

| 테이블           | 설명                     |
| ---------------- | ------------------------ |
| `member_test`    | 회원 정보                |
| `data_test`      | 건강 데이터 및 예측 결과 |
| `community_test` | 커뮤니티 게시글          |
| `comment_test`   | 게시글 댓글              |
| `INQUIRY_TEST`   | 고객 문의                |

### 주요 데이터 필드

| 구분        | 주요 필드                                                                                             |
| ----------- | ----------------------------------------------------------------------------------------------------- |
| 회원        | `mem_id`, `mem_phone`, `mem_name`, `mem_nickname`                                                     |
| 건강 데이터 | `data_id`, `mem_id`, `check_date`, `sex`, `age`, `he_glu`, `he_hba1c`, `he_chol`, `he_bmi`, `predict` |
| 커뮤니티    | `com_id`, `mem_id`, `com_title`, `com_content`, `com_view`, `com_like`, `com_category`, `com_created` |
| 댓글        | `comid`, `memid`, `commentcreated`, `commentcontent`                                                  |
| 문의        | `inq_id`, `mem_id`, `inq_title`, `inq_content`, `inq_created`                                         |

## 11. AI 예측 흐름

```text
1. 사용자가 건강 데이터 입력
2. React가 Flask `/heart_predict` API 호출
3. Flask 서버가 입력 데이터를 numpy 배열로 변환
4. 저장된 scaler로 입력 데이터 전처리
5. Extra Trees 모델로 심근경색 발생 확률 계산
6. 예측 결과를 JSON으로 반환
7. 예측 결과를 Oracle DB에 저장
8. React에서 결과 페이지와 차트로 표시
```

## 12. 개발 방법론

| 방법론           | 적용 내용                                                   |
| ---------------- | ----------------------------------------------------------- |
| 반복적 개발      | 회원, 예측, 커뮤니티, 문의 기능을 순차적으로 구현           |
| 점진적 통합      | React, Spring Boot, Flask, Oracle DB를 단계적으로 연동      |
| 기능 중심 개발   | 사용자 기능 단위로 화면과 API를 분리하여 개발               |
| API 중심 개발    | REST API를 기준으로 프론트엔드와 백엔드 통신 구성           |
| 계층 분리 개발   | Controller, Service, Repository, Entity 계층 분리           |
| 데이터 중심 개발 | ERD, 테이블 정의서, SQL 산출물을 기반으로 DB 및 Entity 구성 |

## 13. 디자인 패턴 및 설계 구조

| 패턴 / 구조                  | 적용 위치          | 설명                                              |
| ---------------------------- | ------------------ | ------------------------------------------------- |
| MVC Pattern                  | Spring Boot        | Controller, Service, Model 중심 구조              |
| Layered Architecture         | Backend            | Controller, Service, Repository, Entity 계층 분리 |
| Repository Pattern           | Spring Data JPA    | DB 접근 로직 추상화                               |
| Dependency Injection         | Spring Boot        | Service, Repository 객체 주입                     |
| RESTful API                  | Spring Boot, Flask | HTTP Method 기반 API 설계                         |
| Component-Based Architecture | React              | UI를 컴포넌트 단위로 분리                         |
| Routing Pattern              | React Router       | URL 경로별 페이지 매핑                            |
| Context Provider Pattern     | React AuthContext  | 로그인 사용자 상태 공유                           |
| Proxy Pattern                | React 개발환경     | `/spring`, `/flask` 요청 분기                     |

## 14. 문제 해결 경험

### CORS 문제 해결

React, Spring Boot, Flask가 각각 다른 포트에서 실행되기 때문에 CORS 문제가 발생할 수 있었습니다. 이를 해결하기 위해 React `setupProxy.js`를 사용하여 `/spring`, `/flask` 경로를 각각 Spring Boot와 Flask 서버로 전달했습니다.

### AI 모델 웹 서비스 연동

Jupyter Notebook에서 개발한 모델을 Flask API 서버로 분리하여 React 화면에서 실시간으로 예측 요청을 보낼 수 있도록 구현했습니다.

### 예측 결과 저장 및 시각화

예측 결과를 화면에 표시하는 데 그치지 않고 Oracle DB에 저장하여 사용자별 예측 이력 조회와 차트 시각화에 활용했습니다.

## 15. 개선 가능 사항

- Python AI 서버용 `requirements.txt` 추가
- DB 접속 정보 및 OAuth Key 환경변수화
- Swagger/OpenAPI 기반 API 문서 자동화
- Spring Security 및 JWT 인증 적용
- 전역 예외 처리 및 응답 형식 표준화
- DTO 분리 및 Validation 추가
- Docker 기반 배포 환경 구성
- CI/CD 파이프라인 구성
- 모델 성능 지표 및 Feature Importance 문서화
- 테스트 코드 보강

## 16. 프로젝트 요약

이 프로젝트는 React, Spring Boot, Flask, Oracle DB를 활용한 풀스택 웹 애플리케이션입니다. 건강 데이터 기반 심근경색 발생 확률 예측 기능을 중심으로 회원 관리, 예측 이력 관리, 데이터 시각화, 커뮤니티, 댓글, 문의 기능을 구현했습니다.

특히 머신러닝 모델을 Flask API 서버로 분리하고, Spring Boot 서비스 API와 함께 React 프론트엔드에서 통합 호출하도록 구성하여 일반 웹 서비스와 AI 예측 기능을 함께 제공하는 구조를 구현했습니다.
