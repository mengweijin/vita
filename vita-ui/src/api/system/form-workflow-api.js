import http from "@/utils/http.js";

const URL_PREFIX = "/system/form-workflow";

/**
 * @typedef {Object} FormDO
 * @property {number} id - 表单ID
 * @property {string} name - 表单名称
 * @property {string} routePath - 表单路由路径
 * @property {string} remark - 备注
 */

export const formApi = {
  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  getById: (id) => http.get(`${URL_PREFIX}/${id}`),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),
};
