import http from "@/utils/http.js";

const URL_PREFIX = "/home";

export const homeApi = {
  queryConsoleChart: () => http.get(`${URL_PREFIX}/query/console/chart`, { params: {} }),
  queryConsoleStatistic: () => http.get(`${URL_PREFIX}/query/console/statistic`, { params: {} }),
};
