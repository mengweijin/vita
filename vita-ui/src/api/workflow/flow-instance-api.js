import http from "@/utils/http.js";

const URL_PREFIX = "/workflow/instance";

export const flowInstanceApi = {
  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  pageMyFlow: (args) => http.get(`${URL_PREFIX}/page/myFlow`, { params: args }),

  start: (businessId, flowParams) =>
    http.post(`${URL_PREFIX}/start`, null, { params: { ...flowParams, businessId: businessId } }),
};
