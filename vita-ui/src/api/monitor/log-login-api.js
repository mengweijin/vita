import http from "@/utils/http.js";

const URL_PREFIX = "/monitor/log-login";

export const logLoginApi = {
  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),
  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  pageByLoginUser: (args) => http.get(`${URL_PREFIX}/page/by/loginUser`, { params: args }),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),
};
