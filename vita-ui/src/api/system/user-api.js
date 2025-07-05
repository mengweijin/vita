import http from '@/utils/http';

const URL_PREFIX = '/system/user';

export const userApi = {
  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  getSensitiveUserById: (id) => http.get(`${URL_PREFIX}/get-sensitive-info/${id}`),

  /**
   *
   * @param {UserRoleBO} data
   * @returns
   */
  setRoles: (data) => http.post(`${URL_PREFIX}/set-roles`, data),

  /**
   *
   * @param {PasswordChangeBO} data
   * @returns
   */
  changePassword: (data) => http.post(`${URL_PREFIX}/change-password`, data),

  /**
   *
   * @param {PasswordResetBO} data
   * @returns
   */
  resetPassword: (data) => http.post(`${URL_PREFIX}/reset-password`, data),
};

class UserRoleBO {
  userId = null;
  roleIds = [];
}

class PasswordChangeBO {
  password = null;
  newPassword = null;
}

class PasswordResetBO {
  username = null;
  password = null;
}
