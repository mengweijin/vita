import http from '@/utils/http';

const BASE_URL = '/system/menu';

export const menuApi = {
  listSideMenus: () => http.get(`${BASE_URL}/listSideMenus`),

  page: (args) => http.get(`${BASE_URL}/page`, { params: args }),

  list: (args) => http.get(`${BASE_URL}/list`, { params: args }),

  create: (data) => http.post(`${BASE_URL}/create`, data),

  update: (data) => http.post(`${BASE_URL}/update`, data),

  remove: (ids) => http.post(`${BASE_URL}/remove/${ids}`),
};
