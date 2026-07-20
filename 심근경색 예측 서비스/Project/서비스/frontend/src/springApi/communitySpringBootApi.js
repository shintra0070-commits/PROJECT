import { springApi } from "../config/axiosInstance";

// 전체 조회와 내 게시글 조회
export const getCommunityList = (mem_id) => mem_id ? 
        springApi.get(`/community/list?mem_id=${mem_id}`) 
    : 
        springApi.get("/community/list");

// 상세 조회
export const getCommunityView =
    (com_id) => springApi.get(`/community/view/${com_id}`);

// 등록
export const setCommunityInsert =
    (community) => springApi.post("/community/insert",community);

// 수정
export const setCommunityUpdate =
    (community) => springApi.put("/community/update", community);

// 삭제
export const setCommunityDelete =
    (com_id) => springApi.delete(`/community/delete/${com_id}`);

// 좋아요
export const setCommunityLike =
    (com_id) =>springApi.post(`/community/like/${com_id}`);

// Paging
export const getCommunityPaging =
    (page, size) =>springApi.get(`/community/list_paging?page=${page}&size=${size}`);

// 좋아요수 정렬
export const getCommunityTopList = () => springApi.get("/community/top_list");


