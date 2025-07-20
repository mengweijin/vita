import http from '@/utils/http';

const URL_PREFIX = '/system/notice';

export const noticeApi = {
  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  release: (id) => http.post(`${URL_PREFIX}/release/${id}`),

  revoke: (id) => http.post(`${URL_PREFIX}/revoke/${id}`),
};
