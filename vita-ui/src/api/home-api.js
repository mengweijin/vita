import http from '@/utils/http';

const URL_PREFIX = '/home';

export const homeApi = {
  getConsoleStatistic: () => http.get(`${URL_PREFIX}/get-console-statistic`, { params: {} }),

  getConsoleChart: () => http.get(`${URL_PREFIX}/get-console-chart`, { params: {} }),
};
