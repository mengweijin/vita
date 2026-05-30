import http from "@/utils/http.js";

const URL_PREFIX = "/workflow/definition";

export const flowDefinitionApi = {
  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  copy: (id) => http.post(`${URL_PREFIX}/copy/${id}`),

  publish: (id) => http.post(`${URL_PREFIX}/publish/${id}`),

  unpublish: (id) => http.post(`${URL_PREFIX}/unpublish/${id}`),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),
};
