import axios from "axios";

const API = axios.create({
  baseURL: import.meta.env.VITE_API_URL
});

export const getRisks = (page = 0, size = 5, sort = "id,asc") => {
  return API.get(`/all?page=${page}&size=${size}&sort=${sort}`);
};