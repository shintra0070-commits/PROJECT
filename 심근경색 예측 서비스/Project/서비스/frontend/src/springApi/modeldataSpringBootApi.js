import { springApi } from "../config/axiosInstance";

export const getDataView = (mem_id) => 
    springApi.get(`/modeldata/view/${mem_id}`)

export const checkTodayPredict = (memId) =>
    springApi.get(`/modeldata/check/${memId}`);

export const getTodayPredict = (memId) =>
    springApi.get(`/modeldata/today/${memId}`);

export const setAnalysisDelete = (data_id) =>
    springApi.delete(`/modeldata/delete/${data_id}`);

