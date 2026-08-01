import http from "@/utils/http.js";

const URL_PREFIX = "/workflow/instance";

export const flowInstanceApi = {
  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  pageMyFlow: (args) => http.get(`${URL_PREFIX}/page/myFlow`, { params: args }),

  /**
   * @typedef {Object} FlowInstanceVO
   * @property {string} id - 流程实例 ID
   * @property {string} definitionId - 流程定义 ID
   * @property {string} flowName - 流程实例名称
   * @property {string} businessId - 业务 ID
   * @property {Number} nodeType - 节点类型
   * @property {string} nodeCode - 节点编码
   * @property {string} nodeName - 节点名称
   * @property {Number} flowStatus - 流程状态
   */
  /**
   * 根据 ID 查询流程实例
   * @returns {FlowInstanceVO}
   */
  queryById: (id) => http.get(`${URL_PREFIX}/query/${id}`),

  submit: (id) => http.post(`${URL_PREFIX}/submit/${id}`),

  revoke: (id, flowParams = {}) => http.post(`${URL_PREFIX}/revoke/${id}`, flowParams),

  termination: (id, flowParams) => http.post(`${URL_PREFIX}/termination/${id}`, flowParams),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),
};
