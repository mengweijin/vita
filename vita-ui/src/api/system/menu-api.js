import http from "@/utils/http.js";

const URL_PREFIX = "/system/menu";

export const menuApi = {
  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  getMenuIdsByRoleId: (roleId) =>
    http.get(`${URL_PREFIX}/get-menu-id-by-role/${roleId}`),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  listSideMenus: () => http.get(`${URL_PREFIX}/listSideMenus`),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),
};
