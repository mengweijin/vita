import http from "@/utils/http.js";

const URL_PREFIX = "/system/user";

/**
 * @typedef {Object} PasswordChangeBO
 * @property {string} password - 旧密码
 * @property {string} newPassword - 新密码
 */

/**
 * @typedef {Object} PasswordResetBO
 * @property {string} username - 用户名
 * @property {string} password - 密码
 */

/**
 * @typedef {Object} UserRoleBO
 * @property {number} userId - 用户ID
 * @property {number[]} roleIds - 角色 ID 数组
 */

export const userApi = {
  /**
   * 修改密码
   * @param {PasswordChangeBO} data - 修改密码数据对象
   * @returns
   */
  changePassword: (data) => http.post(`${URL_PREFIX}/change-password`, data),

  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  enableTotp: (code) => http.post(`${URL_PREFIX}/enable-totp/${code}`, {}),

  generateTotpQrCodeBase64: () =>
    http.get(`${URL_PREFIX}/generate-totp-qrcode`),

  getSaTerminalInfoList: () =>
    http.get(`${URL_PREFIX}/get-sa-terminal-info-list`),

  getTotpEnabled: () => http.get(`${URL_PREFIX}/get-totp-enabled`),

  getUserBOById: (id) => http.get(`${URL_PREFIX}/get-user-bo-by-id/${id}`),

  getUserProfileVO: () => http.get(`${URL_PREFIX}/get-user-profile-vo`),

  getUserStoreVO: () => http.get(`${URL_PREFIX}/get-user-store-vo`),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  pageByPost: (postId, args) =>
    http.get(`${URL_PREFIX}/pageByPost/${postId}`, { params: args }),

  pageByRole: (roleId, args) =>
    http.get(`${URL_PREFIX}/pageByRole/${roleId}`, { params: args }),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  /**
   * 重置密码
   * @param {PasswordResetBO} data
   * @returns
   */
  resetPassword: (data) => http.post(`${URL_PREFIX}/reset-password`, data),

  /**
   * 设置用户角色
   * @param {UserRoleBO} data
   * @returns
   */
  setRoles: (data) => http.post(`${URL_PREFIX}/set-roles`, data),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),

  updateBasicInformation: (data) =>
    http.post(`${URL_PREFIX}/updateBasicInformation`, data),
};
