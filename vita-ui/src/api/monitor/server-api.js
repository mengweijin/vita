import http from '@/utils/http';

const BASE_URL = '/monitor/server';

export const serverApi = {
  serverInfo: () => http.get(`${BASE_URL}/info`),
};
