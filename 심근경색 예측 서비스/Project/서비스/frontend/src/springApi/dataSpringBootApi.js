// axiosInstance.js 불러들이기
//  - springApi 변수 사용
import {springApi} from "../config/axiosInstance";

// 회원 전체 목록 조회를 위한 백엔드 URL 패턴 정의 및 전송방식 함수 정의
export const getDataList = () => 
    // get() 방식으로 전송
    // SpringBoot Controller에서 사용할 URL 패턴 정의
    // - 현재 URL : http://localhost:3000/member/list
    springApi.get("/data/list")

// 회원 상세 조회를 위한 백엔드 URL 패턴 정의 및 전송방식 함수 정의
export const getDataView = (data_id) => 
    springApi.get(`/data/view/${data_id}`)


// 회원 신규 입력 처리를 위한 백엔드 URL 패턴 정의 및 전송방식 함수 정의
export const setDataInsert = (data) => 
    springApi.post("/data/insert", data)

// 회원 삭제 처리를 위한 백엔드 URL 패턴 정의 및 전송방식 함수 정의
export const setDataDelete = (data_id) =>
    springApi.delete(`/data/delete/${data_id}`)

// 회원 수정 처리를 위한 백엔드 URL 패턴 정의 및 전송방식 함수 정의
export const setDataUpdate = (data) =>
    springApi.put("/data/update", data)

/* --------------- Paging 처리 ---------------- */
// 페이징처리가 포함된 처리를 위한 백엔드 URL 패턴 정의 및 전송방식 함수 정의
export const getDataListPaging = (page, size) =>
    // SpringBoot에서 Page 클래스 라이브러리 사용
    //  - Page 클래스 라이브러리에서 page 데이터를 리스트로 관리함
    //  - 넘겨주는 page값은 Page 클래스 라이브러리 리스트 내에서 인덱스번호로 관리됨
    //  - 따라서, 1을 빼준값으로 넘겨주면 됨(리스트의 인덱스번호는 0부터 시작됨)
    springApi.get(`/data/list_paging?page=${page-1}&size=${size}`);