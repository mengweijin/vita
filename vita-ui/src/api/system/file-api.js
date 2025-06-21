import http from '@/utils/http';

const BASE_URL = '/system/file';

export const fileApi = {
  page: (args) => http.get(`${BASE_URL}/page`, { params: args }),

  list: (args) => http.get(`${BASE_URL}/list`, { params: args }),

  create: (data) => http.post(`${BASE_URL}/create`, data),

  update: (data) => http.post(`${BASE_URL}/update`, data),

  remove: (ids) => http.post(`${BASE_URL}/remove/${ids}`),

  upload: () => http.post(`${BASE_URL}/upload`),

  download: (id, fileName = undefined) => http.download(`${BASE_URL}/download/${id}`, fileName),
};
