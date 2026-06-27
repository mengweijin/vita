import http from "@/utils/http.js";

const URL_PREFIX = "/workflow/task";

export const flowTaskApi = {
  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  pass: (taskId, message, variable = {}) =>
    http.post(`${URL_PREFIX}/pass/${taskId}`, variable, {
      params: { message: encodeURIComponent(message) },
    }),

  reject: (taskId, message, variable = {}) =>
    http.post(`${URL_PREFIX}/reject/${taskId}`, variable, {
      params: { message: encodeURIComponent(message) },
    }),

  revoke: (instanceId, flowParams = {}) =>
    http.post(`${URL_PREFIX}/revoke/${instanceId}`, flowParams),

  depute: (taskId, flowParams = {}) => http.post(`${URL_PREFIX}/depute/${taskId}`, flowParams),

  addSignature: (taskId, flowParams = {}) =>
    http.post(`${URL_PREFIX}/addSignature/${taskId}`, flowParams),

  reductionSignature: (taskId, flowParams = {}) =>
    http.post(`${URL_PREFIX}/reductionSignature/${taskId}`, flowParams),

  updateHandler: (taskId, flowParams = {}) =>
    http.post(`${URL_PREFIX}/updateHandler/${taskId}`, flowParams),
};
