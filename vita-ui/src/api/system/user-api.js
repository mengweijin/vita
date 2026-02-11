import http from "@/utils/http.js";

const URL_PREFIX = "/system/user";

export const userApi = {
  /**
   * @typedef {Object} PasswordChangeBO
   * @property {string} password - 旧密码
   * @property {string} newPassword - 新密码
   */

  /**
   * 修改密码
   * @param {PasswordChangeBO} data - 修改密码数据对象
   * @returns
   */
  changePassword: (data) => http.post(`${URL_PREFIX}/change-password`, data),

  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  validateTotpCode: (code) => http.post(`${URL_PREFIX}/validate/totp/${code}`),

  /**
   * @typedef {Object} TotpVO
   * @property {string} key - 密钥
   * @property {string} qrcode - 二维码 base64 字符串
   */
  /**
   * 生成数字口令二维码
   * @returns {TotpVO}
   */
  generateTotpQrcode: () => http.get(`${URL_PREFIX}/generate/totpQrcode`),

  getSaTerminalInfoList: () =>
    http.get(`${URL_PREFIX}/get-sa-terminal-info-list`),

  getUserBOById: (id) => http.get(`${URL_PREFIX}/get-user-bo-by-id/${id}`),

  getUserProfileVO: () => http.get(`${URL_PREFIX}/get-user-profile-vo`),

  getUserStoreVO: () => http.get(`${URL_PREFIX}/get-user-store-vo`),

  /**
   * 判断用户是否已绑定数字口令
   * @returns boolean
   */
  hasTotpKey: () => http.get(`${URL_PREFIX}/has/totpKey`),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  pageByPost: (postId, args) =>
    http.get(`${URL_PREFIX}/pageByPost/${postId}`, { params: args }),

  pageByRole: (roleId, args) =>
    http.get(`${URL_PREFIX}/pageByRole/${roleId}`, { params: args }),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  /**
   * @typedef {Object} PasswordResetBO
   * @property {string} username - 用户名
   * @property {string} password - 密码
   */

  /**
   * 重置密码
   * @param {PasswordResetBO} data
   * @returns
   */
  resetPassword: (data) => http.post(`${URL_PREFIX}/reset-password`, data),

  /**
   * @typedef {Object} TotpBO
   * @property {string} key - 密钥
   * @property {Number} code - 数字口令
   */
  /**
   * 保存 totp 绑定
   * @param {TotpBO} data
   */
  saveTotp: (data) => http.post(`${URL_PREFIX}/save/totp`, data),

  /**
   * @typedef {Object} UserRoleBO
   * @property {number} userId - 用户ID
   * @property {number[]} roleIds - 角色 ID 数组
   */

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
