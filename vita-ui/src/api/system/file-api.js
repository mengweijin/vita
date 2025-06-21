import http from '@/utils/http';

const URL_PREFIX = '/system/file';

export const fileApi = {
  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  upload: () => http.post(`${URL_PREFIX}/upload`),

  download: (id, fileName = undefined) => http.download(`${URL_PREFIX}/download/${id}`, fileName),
};
