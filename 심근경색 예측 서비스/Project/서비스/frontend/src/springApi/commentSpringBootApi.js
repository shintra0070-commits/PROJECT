import { springApi } from "../config/axiosInstance";

/**
 * 댓글 전체 조회
*/
export const getCommentList = (comid) =>
    springApi.get(`/comment/list/${comid}`);

/**
 * 댓글 등록
*/
export const setCommentInsert = (comment) =>
    springApi.post("/comment/insert", comment);

/**
 * 댓글 수정
*/
export const setCommentUpdate = (comment) =>
    springApi.put("/comment/update",comment);

/**
 * 댓글 삭제
*/
export const setCommentDelete =
    (comid, memid, commentcreated) =>
        springApi.delete(`/comment/delete?comid=${comid}&memid=${memid}&commentcreated=${commentcreated}`);
