import http from "@/utils/http.js";

const URL_PREFIX = "/system/user";

export const userApi = {
  /**
   * @typedef {Object} PasswordChangeBO
   * @property {string} password - 新密码
   */
  /**
   * 修改密码
   * @param {PasswordChangeBO} data - 修改密码数据对象
   * @returns
   */
  changePassword: (data) => http.post(`${URL_PREFIX}/change/password`, data),

  /**
   * 检查当前会话是否已通过二级认证，如未通过则抛出异常。前端异常拦截后自动拉起二级认证弹框。
   */
  checkSafe: () =>
    http.post(`${URL_PREFIX}/check/safe`, null, {
      message: false,
    }),

  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  /**
   * @typedef {Object} TotpVO
   * @property {string} key - 密钥
   * @property {string} qrcode - 二维码 base64 字符串
   */
  /**
   * 生成数字口令二维码
   * @returns {TotpVO}
   */
  generateTotpQrcode: () => http.get(`${URL_PREFIX}/generate/totp/qrcode`),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  pageByPost: (postId, args) =>
    http.get(`${URL_PREFIX}/page/by/post/${postId}`, { params: args }),

  pageByRole: (roleId, args) =>
    http.get(`${URL_PREFIX}/page/by/role/${roleId}`, { params: args }),

  queryTerminalInfo: () => http.get(`${URL_PREFIX}/query/terminalInfo`),

  queryUserBO: (id) => http.get(`${URL_PREFIX}/query/bo/${id}`),

  queryUserProfileVO: () => http.get(`${URL_PREFIX}/query/userProfileVO`),

  queryUserStoreVO: () => http.get(`${URL_PREFIX}/query/userStoreVO`),

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
  resetPassword: (data) => http.post(`${URL_PREFIX}/reset/password`, data),

  /**
   * @typedef {Object} OpenSafeBO
   * @property {string} safeMode - 二级认证模式，关联字典 vt_safe_mode
   * @property {string} value - 值，密码或动态口令
   */
  /**
   * 二级认证
   * @param {OpenSafeBO} data
   */
  secondaryAuth: (data) =>
    http.post(`${URL_PREFIX}/auth/secondary`, data, {
      loading: false,
      message: false,
    }),

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
  setRoles: (data) => http.post(`${URL_PREFIX}/set/roles`, data),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),

  updateBasicInformation: (data) =>
    http.post(`${URL_PREFIX}/update/basicInformation`, data),

  validateTotpCode: (code) =>
    http.post(`${URL_PREFIX}/validate/totp/${code}`, null, {
      message: false,
    }),
};
