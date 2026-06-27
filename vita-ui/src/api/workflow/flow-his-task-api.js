import http from "@/utils/http.js";

const URL_PREFIX = "/workflow/his-task";

export const flowHisTaskApi = {
  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),
};
