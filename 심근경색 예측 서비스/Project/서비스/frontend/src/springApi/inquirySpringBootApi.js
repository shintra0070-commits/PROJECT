import { springApi } from "../config/axiosInstance";

/**
 * 전체 조회
*/
export const getInquiryList = () =>

    springApi.get("/inquiry/list");

/**
 * 상세 조회
*/
export const getInquiryView = (inq_id) =>

    springApi.get(`/inquiry/view/${inq_id}`);

/**
 * 등록
*/
export const setInquiryInsert = (inquiry) =>

    springApi.post("/inquiry/insert", inquiry);

/**
 * 수정
*/
export const setInquiryUpdate = (inquiry) =>

    springApi.put("/inquiry/update",inquiry);

/**
 * 삭제
*/
export const setInquiryDelete = (inq_id) =>

    springApi.delete(`/inquiry/delete/${inq_id}`);