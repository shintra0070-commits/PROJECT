import { flaskApi } from "../config/axiosInstance";

export const getHeartPredict = (features) =>
    flaskApi.post("/heart_predict", features)