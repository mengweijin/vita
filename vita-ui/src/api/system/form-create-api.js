import http from "@/utils/http.js";

const URL_PREFIX = "/system/form-create";

export const formCreateApi = {
  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  getById: (id) => http.get(`${URL_PREFIX}/${id}`),

  getByCode: (code) => http.get(`${URL_PREFIX}/query/by/code/${code}`),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),
};
