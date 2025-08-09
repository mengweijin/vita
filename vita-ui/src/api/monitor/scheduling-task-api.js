import http from '@/utils/http';

const URL_PREFIX = '/monitor/scheduling-task';

export const schedulingTaskApi = {
  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  run: (id) => http.post(`${URL_PREFIX}/run/${id}`),
};

const LOG_URL_PREFIX = '/monitor/scheduling-task-log';

export const schedulingTaskLogApi = {
  page: (args) => http.get(`${LOG_URL_PREFIX}/page`, { params: args }),

  list: (args) => http.get(`${LOG_URL_PREFIX}/list`, { params: args }),

  create: (data) => http.post(`${LOG_URL_PREFIX}/create`, data),

  update: (data) => http.post(`${LOG_URL_PREFIX}/update`, data),

  remove: (ids) => http.post(`${LOG_URL_PREFIX}/remove/${ids}`),
};
