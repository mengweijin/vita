import http from '@/utils/http';

const URL_PREFIX = '/monitor/server';

export const serverApi = {
  serverInfo: () => http.get(`${URL_PREFIX}/info`),
};
