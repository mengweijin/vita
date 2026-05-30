import http from "@/utils/http.js";

const URL_PREFIX = "/workflow/instance";

export const flowInstanceApi = {
  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),
};
