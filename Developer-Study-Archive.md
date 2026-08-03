# Developer Study Archive

AI 개발 전문가 과정과 개인 학습 내용을 정리한 학습 아카이브입니다.  
Python 기반 데이터 분석, 머신러닝, 딥러닝, 자연어 처리와 LLM 활용을 중심으로 Java, Oracle Database, Spring Boot, React, Git/GitHub까지 웹 서비스 개발 전반의 학습 기록과 프로젝트 경험을 정리합니다.

<br>

## Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Python & AI](#python--ai)
- [Java](#java)
- [Oracle Database](#oracle-database)
- [Spring Boot](#spring-boot)
- [React](#react)
- [Git & GitHub](#git--github)
- [Team Projects](#team-projects)
  - [온프레미스 AX/DX 기반의 민원 업무 자동화 플랫폼 구축](#1-온프레미스-axdx-기반의-민원-업무-자동화-플랫폼-구축)
  - [심근경색 발생 위험도 예측 웹 서비스](#2-심근경색-발생-위험도-예측-웹-서비스)
  - [BDI 운임지수 예측 프로젝트](#3-bdi-운임지수-예측-프로젝트)
- [AI 모델 개발 및 문제 해결 기록](#ai-모델-개발-및-문제-해결-기록)
- [Repository Purpose](#repository-purpose)

<br>

## Overview

이 저장소는 AI 개발 전문가 과정에서 학습한 프로그래밍, 데이터 분석, 인공지능, 웹 개발 내용을 정리하기 위한 공간입니다.

단순한 개념 학습에 그치지 않고, Python 기반 데이터 분석과 머신러닝·딥러닝·LLM 모델 구현, Spring Boot와 React를 활용한 웹 서비스 개발, Oracle Database 연동, Git/GitHub 협업 경험까지 실제 프로젝트 흐름에 맞춰 정리하는 것을 목표로 합니다.

<br>

## Tech Stack

| Category          | Skills                                              |
| ----------------- | --------------------------------------------------- |
| Language          | Python, Java, SQL, JavaScript                       |
| Data Analysis     | Pandas, NumPy                                       |
| Machine Learning  | Scikit-learn                                        |
| Deep Learning     | TensorFlow, Keras, ANN, DNN, RNN, LSTM, TextCNN     |
| NLP & Transformer | Keras Tokenizer, KLUE-BERT, KoELECTRA, KcELECTRA    |
| LLM               | Ollama, Gemma, Llama, LangChain, Prompt Engineering |
| Visualization     | Matplotlib, Seaborn                                 |
| Backend           | Spring Boot, Flask, REST API, JDBC                  |
| Frontend          | React, React Router, Axios                          |
| Database          | Oracle Database                                     |
| Collaboration     | Git, GitHub, Pull Request                           |

<br>

## Python & AI

### 학습 내용

- Python 기초 문법 및 객체지향 프로그래밍(OOP)
- Pandas, NumPy를 활용한 데이터 처리 및 분석
- Matplotlib, Seaborn을 활용한 데이터 시각화
- 웹 크롤링 및 데이터 수집
- 데이터 전처리 및 특성 선택(Feature Engineering)
- 머신러닝 모델 개발 및 성능 평가
- TensorFlow, Keras 기반 딥러닝 모델 개발
- 인공신경망(ANN), 심층신경망(DNN), 순환신경망(RNN), LSTM, CNN 학습
- 자연어 전처리, 토큰화, 임베딩, 패딩 처리
- Transformer와 사전 학습 언어 모델을 활용한 텍스트 분류
- LLM(Large Language Model)의 기본 개념과 로컬 모델 활용
- Prompt Engineering 및 Few-shot 분류 프롬프트 설계
- 이진 분류, 다중 클래스 분류, 다중 라벨 분류 모델 구현
- 정확도, 정밀도, 재현율, F1-score, Loss, 오차행렬 기반 성능 평가
- REST API를 활용한 AI 모델 호출 및 웹 서비스 연동

### 머신러닝과 딥러닝 이해

머신러닝은 데이터를 기반으로 패턴을 학습하여 새로운 데이터에 대한 결과를 예측하거나 데이터를 구조화하는 인공지능 기술입니다.  
본 학습 과정에서는 문제 유형에 따라 분류, 회귀, 군집 모델을 구분하여 학습하고, 각 모델의 목적에 맞는 성능 평가 지표를 활용했습니다.

#### 머신러닝 모델 유형

| 유형                 | 설명                                            | 활용 모델                                                               | 성능 평가                                               |
| -------------------- | ----------------------------------------------- | ----------------------------------------------------------------------- | ------------------------------------------------------- |
| 분류(Classification) | 데이터를 정해진 범주로 예측하는 방식            | Logistic Regression, Decision Tree, Random Forest, SVM, KNN             | Accuracy, Precision, Recall, F1-score, Confusion Matrix |
| 회귀(Regression)     | 연속적인 수치 값을 예측하는 방식                | Linear Regression, Random Forest Regressor, Gradient Boosting Regressor | MAE, MSE, RMSE, R2 Score                                |
| 군집(Clustering)     | 정답 라벨 없이 데이터의 유사한 그룹을 찾는 방식 | K-Means, DBSCAN, Hierarchical Clustering                                | Silhouette Score, 군집 분포 시각화                      |

분류 모델은 예측 결과가 실제 클래스와 얼마나 일치하는지뿐만 아니라, Precision과 Recall을 함께 확인하여 오탐과 미탐의 균형을 평가했습니다.  
회귀 모델은 실제 값과 예측 값의 차이를 MAE, MSE, RMSE로 비교하고, R2 Score를 통해 모델이 데이터를 얼마나 잘 설명하는지 확인했습니다.  
군집 모델은 데이터 간 유사도를 기반으로 그룹을 나누고, Silhouette Score와 시각화를 통해 군집이 적절하게 형성되었는지 분석했습니다.

#### 딥러닝 모델 이해

딥러닝은 인공신경망을 기반으로 복잡한 데이터의 특징을 자동으로 학습하는 머신러닝의 한 분야입니다.  
TensorFlow와 Keras를 활용하여 ANN, DNN, CNN 모델의 구조를 학습하고, 데이터 특성에 따라 분류와 회귀 문제에 적용했습니다. 또한 LLM의 기본 개념과 자연어 처리 분야에서의 활용 방식을 함께 학습했습니다.

| 유형             | 설명                                                                 | 주요 개념                                                        | 성능 평가                                          |
| ---------------- | -------------------------------------------------------------------- | ---------------------------------------------------------------- | -------------------------------------------------- |
| ANN 분류 모델    | 입력 데이터를 여러 클래스 중 하나로 예측하는 신경망 모델             | Dense Layer, Activation Function, Cross Entropy, Softmax/Sigmoid | Accuracy, Precision, Recall, F1-score, Loss        |
| ANN 회귀 모델    | 입력 데이터를 기반으로 연속적인 수치 값을 예측하는 신경망 모델       | Dense Layer, ReLU, MSE Loss, Optimizer                           | MAE, MSE, RMSE, Loss                               |
| DNN 모델         | 여러 은닉층을 쌓아 복잡한 비선형 패턴을 학습하는 심층신경망 모델     | Hidden Layer, Backpropagation, Dropout, Batch Normalization      | Accuracy, Loss, MAE, RMSE                          |
| RNN/LSTM 모델    | 텍스트처럼 순서가 있는 데이터의 문맥과 흐름을 학습하는 모델          | Embedding, Sequence, Hidden State, LSTM Unit                     | Accuracy, Precision, Recall, F1-score, Loss        |
| CNN/TextCNN 모델 | 이미지 또는 텍스트의 지역적 패턴과 핵심 특징을 추출하는 모델         | Convolution Layer, Pooling, Filter, Feature Map                  | Accuracy, Precision, Recall, F1-score, Loss        |
| Transformer 모델 | Attention을 기반으로 문장 전체의 관계와 문맥을 학습하는 모델         | Self-Attention, Tokenizer, Fine-tuning                           | Accuracy, Macro F1-score, Loss                     |
| LLM              | 대규모 텍스트 데이터를 기반으로 자연어를 이해하고 생성하는 언어 모델 | Token, Embedding, Transformer, Prompt Engineering                | 응답 정확성, 일관성, 문맥 이해도, 활용 목적 적합성 |

딥러닝 모델 학습 과정에서는 손실 함수(Loss Function), 활성화 함수(Activation Function), 옵티마이저(Optimizer), Epoch, Batch Size 등의 개념을 학습했습니다.  
DNN은 은닉층을 깊게 구성하여 복잡한 패턴을 학습하는 구조로 이해했으며, CNN은 Convolution과 Pooling 과정을 통해 이미지 데이터의 특징을 추출하는 방식으로 학습했습니다.  
또한 학습 데이터와 검증 데이터의 성능 차이를 비교하여 과적합 여부를 확인하고, Dropout, Early Stopping 등의 방법을 통해 모델의 일반화 성능을 개선하는 과정을 실습했습니다. 이진 분류에서는 Sigmoid와 Binary Cross Entropy를, 다중 클래스 분류에서는 Softmax와 Sparse/Categorical Cross Entropy를 사용하도록 출력층과 손실 함수를 구분했습니다.

LLM은 대규모 언어 데이터를 기반으로 문장을 이해하고 생성하는 모델로, Transformer 구조와 Prompt Engineering의 기본 개념을 학습했습니다.  
특히 LLM은 정량적인 정확도뿐만 아니라 응답의 정확성, 일관성, 문맥 이해도, 활용 목적에 맞는 결과 생성 여부를 함께 고려하여 평가할 수 있음을 이해했습니다.

### 자연어 처리 모델 학습

민원 분류와 비속어 탐지 실습을 통해 문장 데이터를 다루는 전체 과정을 학습했습니다.

- 정규표현식을 이용한 텍스트 정제와 공백 처리
- Tokenizer를 이용한 정수 인코딩과 Padding
- Embedding Layer를 이용한 단어 표현 학습
- RNN/LSTM, TextCNN, DNN 기반 텍스트 분류
- KLUE-BERT, KoELECTRA, KcELECTRA 기반 Transformer 분류
- 이진 분류와 9개 클래스 분류에 맞는 출력층 및 손실 함수 설정
- 클래스 불균형, 과적합, 재현성 문제 확인
- 검증 데이터와 테스트 데이터를 분리한 모델 비교

### 모델 성능 평가 방법

모델은 단일 지표만으로 결정하지 않고 여러 평가 결과를 함께 확인했습니다.

| 평가 항목                | 확인 목적                                            |
| ------------------------ | ---------------------------------------------------- |
| Training/Validation Loss | 학습 수렴 여부와 과적합 확인                         |
| Accuracy                 | 전체 예측 중 정답의 비율 확인                        |
| Precision                | 특정 클래스로 예측한 결과의 정확성 확인              |
| Recall                   | 실제 클래스 데이터를 놓치지 않는지 확인              |
| Macro F1-score           | 클래스별 성능을 동일한 비중으로 종합 평가            |
| Confusion Matrix         | 어떤 클래스끼리 자주 혼동하는지 분석                 |
| 처리 시간                | 전체 실행 시간과 민원 1건당 평균·최소·최대 시간 확인 |

Best Model은 Accuracy만 보지 않고 Validation Loss, Macro F1-score, 클래스별 Recall, 과적합 여부와 처리 시간을 종합하여 선정했습니다.

### LLM 및 프롬프트 설계 학습

로컬 환경에서 Ollama 기반 Gemma·Llama 모델을 실행하고, 모든 모델에 동일한 공통 프롬프트를 적용하여 분류 성능을 비교했습니다.

- 역할, 작업 목표, 허용 라벨, 출력 형식을 명확하게 작성
- 모호할 때 사용할 기본 라벨과 금지 조합을 절대 규칙으로 지정
- 기본은 단일 라벨로 판단하고, 서로 다른 행정 조치가 필요한 경우에만 다중 라벨 허용
- Few-shot 예시는 문장 표면 유사도보다 대상과 요청 조치의 유사성을 우선
- 출력 이후 허용 라벨 확인, 공백 제거, 중복 제거 등의 후처리 적용
- `temperature=0`을 사용하여 결과 변동을 줄이고 반복 실행으로 안정성 확인
- 실제 서비스에서는 요청당 한 번 예측하고, 평가 단계에서는 여러 번 실행하여 일관성 점검

학습한 머신러닝 및 딥러닝 개념은 심근경색 발생 위험도 예측, BDI 운임지수 예측, AI 기반 민원 자동 분류 프로젝트에 적용하여 실제 데이터를 기반으로 모델을 구현하고 서비스와 연결하는 경험으로 확장했습니다.

### 활용 프로젝트

- 온프레미스 AX/DX 기반의 민원 업무 자동화 플랫폼 구축
- 심근경색 발생 위험도 예측 웹 서비스
- BDI 운임지수 예측 프로젝트

<br>

## Java

### 학습 내용

- Java 기초 문법
- 객체지향 프로그래밍(OOP)
- 클래스 및 상속
- 인터페이스 및 다형성
- 예외 처리(Exception Handling)
- 컬렉션 프레임워크(Collection Framework)
- 파일 입출력 및 JDBC 활용

<br>

## Oracle Database

### 학습 내용

- 데이터베이스 설계 및 데이터 모델링
- ERD(Entity Relationship Diagram) 작성
- SQL 기본 문법
- JOIN 및 SubQuery 활용
- 제약조건(Constraint) 설정
- 데이터 정규화
- Oracle DB 연동 및 관리

<br>

## Spring Boot

### 학습 내용

- Spring Boot 기반 웹 애플리케이션 개발
- REST API 설계 및 구현
- Controller, Service, Repository 계층 구조 이해
- Oracle DB 연동
- CRUD 기능 구현
- 로그인 및 회원 관리 기능 개발
- 게시판 기능 구현
- API 테스트 및 예외 처리

<br>

## React

### 학습 내용

- 컴포넌트 기반 개발(Component-Based Architecture)
- State 및 Props 활용
- React Hooks 활용
- React Router를 활용한 페이지 관리
- Axios를 활용한 REST API 연동
- 사용자 인터페이스(UI) 구현
- SPA(Single Page Application) 구조 이해

<br>

## Git & GitHub

### 학습 내용

- Git 기본 명령어 활용
- 로컬 및 원격 저장소 관리
- 브랜치 생성 및 관리
- Merge 및 충돌 해결
- Pull Request(PR) 활용
- GitHub 협업 경험
- 형상관리 및 버전 관리

### 여러 컴퓨터에서 같은 저장소 작업하기

처음 사용하는 컴퓨터에서는 원격 저장소를 Clone하고, 이후부터는 작업 전에 Pull, 작업 후 Commit과 Push를 진행합니다.

```bash

# 처음 한 번만 실행
git clone https://github.com/사용자명/저장소명.git
cd 저장소명

# 작업 시작 전
git pull origin main

# 작업 완료 후
git add .
git commit -m "작업 내용"
git push origin main
```

개인 브랜치를 사용하는 경우에는 `main` 대신 자신의 브랜치로 이동하여 최신 내용을 받은 뒤 작업합니다.

```bash
git switch 브랜치명
git pull origin 브랜치명
git add .
git commit -m "작업 내용"
git push origin 브랜치명
```

### Git 사용 시 확인한 문제

- Git 저장소 안에 또 다른 `.git` 폴더가 있으면 `embedded git repository` 경고가 발생할 수 있습니다.
- 일반 폴더로 올릴 목적이라면 내부 저장소의 `.git`을 제거한 뒤 다시 추가해야 합니다.
- 별도의 저장소로 연결할 목적이라면 Submodule을 사용합니다.
- 다른 컴퓨터에서 작업할 때는 같은 폴더를 다시 `git init`하는 것보다 `git clone`을 사용하는 것이 안전합니다.
- Push 전에 Pull을 실행하여 원격 저장소와 로컬 저장소의 차이를 먼저 확인합니다.

<br>

## Team Projects

### 1. 온프레미스 AX/DX 기반의 민원 업무 자동화 플랫폼 구축

민원 접수부터 분류, 담당 부서 배정, 처리 및 답변까지의 전 과정을 자동화하여 민원인과 담당 공무원의 업무 효율성을 높이는 지능형 민원 처리 플랫폼 개발입니다.
팀 프로젝트를 통해 데이터베이스 설계부터 백엔드 REST API 개발, 프론트엔드 화면 구현까지 전반적인 시스템을 구축하였으며,
딥러닝·Transformer·LLM 모델의 성능을 비교·분석하고, 최종 선정한 LLM 모델을 기반으로 민원 업무 자동화 기능을 구현하였습니다

#### 주요 내용

- Oracle DB 기반 민원·사용자·부서·처리 이력 데이터 설계 및 관리
- Spring Boot 기반 민원 처리 및 사용자 관리 REST API 개발
- React 기반 민원인·공무원·관리자 사용자 인터페이스 구현
- 비표준어 변환, 유해 표현 탐지 및 민원 대분류 머신러닝 모델 개발
- AI 기반 민원 다중 라벨 분류 및 담당 부서 추천 기능 연동
- 민원 배정·이관·반려·답변·처리 완료 업무 프로세스 구현
- 온프레미스 환경에서 로컬 LLM을 활용한 민원 분석 시스템 구축
- Git/GitHub 기반 형상 관리 및 팀 협업 진행

#### 기술 스택

`Python` `Pandas` `TensorFlow` `Keras` `Transformers` `Ollama` `LangChain` `Flask` `Spring Boot` `React` `Oracle Database` `Git` `GitHub`

<br>

### 2. 심근경색 발생 위험도 예측 웹 서비스

사용자의 건강 관련 데이터를 기반으로 심근경색 발생 위험도를 예측하는 웹 서비스입니다.  
데이터베이스 설계부터 백엔드 API, 프론트엔드 화면, 머신러닝 예측 기능 연동까지 전체 서비스 흐름을 팀 프로젝트로 구현했습니다.

#### 주요 내용

- Oracle DB 설계 및 데이터 관리
- Spring Boot REST API 개발
- React 기반 사용자 인터페이스 구현
- 머신러닝 모델 학습 및 예측 기능 연동
- Git/GitHub 기반 협업 진행

#### 기술 스택

`Python` `Pandas` `Numpy` `Scikit-learn` `Matplotlib` `Seaborn` `Spring Boot` `xgboost` `React` `Oracle Database` `Git` `GitHub`

<br>

### 3. BDI 운임지수 예측 프로젝트

BDI(Baltic Dry Index) 운임지수 데이터를 수집하고 전처리한 뒤, 다양한 머신러닝 모델을 비교하여 운임지수를 예측한 데이터 분석 프로젝트입니다.

#### 주요 내용

- 데이터 수집 및 전처리
- 머신러닝 모델 비교 및 성능 평가
- 운임지수 예측 모델 개발
- 예측 결과 분석 및 시각화
- 데이터 기반 의사결정 지원

#### 기술 스택

`Python` `Pandas` `NumPy` `Scikit-learn` `Matplotlib` `Seaborn`

<br>

## Repository Purpose

이 저장소는 학습 과정에서 다룬 개념, 실습 코드, 프로젝트 경험을 체계적으로 정리하기 위한 공간입니다.
각 기술을 단순히 학습하는 데 그치지 않고, 실제 웹 서비스와 데이터 예측 프로젝트에 적용하며 개발 역량을 확장하는 것을 목표로 합니다. 또한 학습 내용을 꾸준히 기록하여 개인 포트폴리오와 성장 기록으로 활용하고자 합니다.
