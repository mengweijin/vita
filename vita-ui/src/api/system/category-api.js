import http from "@/utils/http.js";

const URL_PREFIX = "/system/category";

export const categoryApi = {
  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  pageRoot: (args) => http.get(`${URL_PREFIX}/page/root`, { params: args }),

  listChildrenByCode: (code, withSelf = true, withDisabled = false) =>
    http.get(`${URL_PREFIX}/list/children/by/code/${code}`, {
      withSelf: withSelf,
      withDisabled: withDisabled,
    }),

  enable: (id) => http.post(`${URL_PREFIX}/enable/${id}`),

  disable: (id) => http.post(`${URL_PREFIX}/disable/${id}`),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),
};
