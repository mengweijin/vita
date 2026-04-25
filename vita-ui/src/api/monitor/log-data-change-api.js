import http from "@/utils/http";

const URL_PREFIX = "/monitor/log-data-change";

export const logDataChangeApi = {
  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  listTableNames: () => http.get(`${URL_PREFIX}/list/tableNames`),

  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  pageByLoginUser: (args) => http.get(`${URL_PREFIX}/page-by-login-user`, { params: args }),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),
};
