import http from '@/utils/http';

const URL_PREFIX = '/system/message';

export const messageApi = {
  selectUnviewedCount: () => http.get(`${URL_PREFIX}/query/unviewed-count`),

  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),
};
