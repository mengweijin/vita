import http from "@/utils/http";

const URL_PREFIX = "/monitor/scheduling-task";

export const schedulingTaskApi = {
  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  getTaskBeanNames: () => http.get(`${URL_PREFIX}/getTaskBeanNames`),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  run: (id) => http.post(`${URL_PREFIX}/run/${id}`),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),
};

const LOG_URL_PREFIX = "/monitor/scheduling-task-log";

export const schedulingTaskLogApi = {
  create: (data) => http.post(`${LOG_URL_PREFIX}/create`, data),

  list: (args) => http.get(`${LOG_URL_PREFIX}/list`, { params: args }),
  page: (args) => http.get(`${LOG_URL_PREFIX}/page`, { params: args }),

  remove: (ids) => http.post(`${LOG_URL_PREFIX}/remove/${ids}`),

  update: (data) => http.post(`${LOG_URL_PREFIX}/update`, data),
};
