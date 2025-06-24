import http from '@/utils/http';

const URL_PREFIX = '/system/menu';

export const menuApi = {
  listSideMenus: () => http.get(`${URL_PREFIX}/listSideMenus`),

  getMenuIdsByRoleId: (roleId) => http.get(`${URL_PREFIX}/get-menu-id-by-role/${roleId}`),

  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),
};
