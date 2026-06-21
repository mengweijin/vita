import http from "@/utils/http.js";

const URL_PREFIX = "/workflow/task";

export const flowTaskApi = {
  pageBacklog: (args) => http.get(`${URL_PREFIX}/page/backlog`, { params: args }),

  pageDone: (args) => http.get(`${URL_PREFIX}/page/done`, { params: args }),

  pass: (taskId, message, variable) =>
    http.post(`${URL_PREFIX}/pass`, null, {
      params: { ...variable, taskId: taskId, message: message },
    }),

  reject: (taskId, message, variable) =>
    http.post(`${URL_PREFIX}/reject`, null, {
      params: { ...variable, taskId: taskId, message: message },
    }),

  revoke: (instanceId, flowParams) =>
    http.post(`${URL_PREFIX}/revoke`, null, {
      params: { ...flowParams, instanceId: instanceId },
    }),

  depute: (taskId, flowParams) =>
    http.post(`${URL_PREFIX}/depute`, null, {
      params: { ...flowParams, taskId: taskId },
    }),

  addSignature: (taskId, flowParams) =>
    http.post(`${URL_PREFIX}/addSignature`, null, {
      params: { ...flowParams, taskId: taskId },
    }),

  reductionSignature: (taskId, flowParams) =>
    http.post(`${URL_PREFIX}/reductionSignature`, null, {
      params: { ...flowParams, taskId: taskId },
    }),
  updateHandler: (taskId, flowParams) =>
    http.post(`${URL_PREFIX}/updateHandler`, null, {
      params: { ...flowParams, taskId: taskId },
    }),
};
