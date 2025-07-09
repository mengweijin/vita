import http from '@/utils/http';

const URL_PREFIX = '/system/role';

export const roleApi = {
  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  removeByRoleIdInUserIds: (roleId, userIds) => http.post(`${URL_PREFIX}/removeByRoleIdInUserIds/${roleId}/${userIds}`),

  setPermission: (id, menuIdList = []) =>
    http.post(`${URL_PREFIX}/set-permission`, { roleId: id, menuIds: menuIdList }),

  getDefaultRole: () => http.get(`${URL_PREFIX}/get-default-role`, {}),
};
