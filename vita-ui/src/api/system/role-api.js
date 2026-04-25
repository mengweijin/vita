import http from "@/utils/http.js";

const URL_PREFIX = "/system/role";

export const roleApi = {
  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  queryDefaultRole: () => http.get(`${URL_PREFIX}/query/defaultRole`, {}),

  queryRoleIdsByUserId: (userId) => http.get(`${URL_PREFIX}/query/roleIds/by/userId/${userId}`, {}),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  removeByRoleIdInUserIds: (roleId, userIds) =>
    http.post(`${URL_PREFIX}/remove/by/roleId/in/userIds/${roleId}/${userIds}`, null, {
      loading: false,
    }),

  setPermissions: (id, menuIdList = []) =>
    http.post(`${URL_PREFIX}/set/permissions`, {
      menuIds: menuIdList,
      roleId: id,
    }),

  setUsers: (roleId, userIds) => http.post(`${URL_PREFIX}/set/users/${roleId}/${userIds}`),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),
};
