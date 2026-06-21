import http from "@/utils/http.js";

const URL_PREFIX = "/workflow/instance";

export const flowInstanceApi = {
  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  pageMyFlow: (args) => http.get(`${URL_PREFIX}/page/myFlow`, { params: args }),

  termination: (id, flowParams) => http.post(`${URL_PREFIX}/termination/${id}`, flowParams),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),
};
