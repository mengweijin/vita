import http from "@/utils/http.js";

const URL_PREFIX = "/system/form";

/**
 * @typedef {Object} FormDO
 * @property {number} id - 表单ID
 * @property {number} parentId - 父级ID
 * @property {string} name - 表单名称
 * @property {string} type - 表单类型
 * @property {string} staticFormPath - 静态表单路径
 * @property {number} dynamicFormId - 动态表单ID
 * @property {string} remark - 备注
 */

export const formApi = {
  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  listChildrenByParentId: (parentId) =>
    http.get(`${URL_PREFIX}/list/children/by/parentId/${parentId}`),

  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  pageRoot: (args) => http.get(`${URL_PREFIX}/page/root`, { params: args }),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),
};
