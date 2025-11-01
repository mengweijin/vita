import http from '@/utils/http';

const URL_PREFIX = '/system/message';

export const messageApi = {
  selectNotViewedCount: () => http.get(`${URL_PREFIX}/query/not-viewed-count`),

  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  setViewed: (messageReceiverIds) => http.post(`${URL_PREFIX}/set-viewed/${messageReceiverIds}`),

  setNotViewed: (messageReceiverIds) => http.post(`${URL_PREFIX}/set-not-viewed/${messageReceiverIds}`),
};
