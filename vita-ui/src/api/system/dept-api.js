import http from "@/utils/http.js";

const URL_PREFIX = "/system/dept";

export const deptApi = {
  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  enable: (id) => http.post(`${URL_PREFIX}/enable/${id}`),

  disable: (id) => http.post(`${URL_PREFIX}/disable/${id}`),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),
};
