import http from "@/utils/http.js";

const URL_PREFIX = "/oa/employee-leave";

export const employeeLeaveApi = {
  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  saveWorkflow: (data) => http.post(`${URL_PREFIX}/saveWorkflow`, data),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  getById: (id) => http.get(`${URL_PREFIX}/${id}`),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),
};
