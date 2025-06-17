import http from '@/utils/http';

const BASE_URL = '/system/message';

export const messageApi = {
  selectUnviewedCount: () => http.get(`${BASE_URL}/query/unviewed-count`),

  page: (args) => http.get(`${BASE_URL}/page`, { params: args }),

  list: (args) => http.get(`${BASE_URL}/list`, { params: args }),

  create: (data) => http.post(`${BASE_URL}/create`, data),

  update: (data) => http.post(`${BASE_URL}/update`, data),

  remove: (ids) => http.post(`${BASE_URL}/remove/${ids}`),
};
