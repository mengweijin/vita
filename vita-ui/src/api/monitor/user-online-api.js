import http from '@/utils/http';

const BASE_URL = '/monitor/user-online';

export const userOnlineApi = {
  page: (args) => http.get(`${BASE_URL}/page`, { params: args }),

  terminalList: (sessionId) => http.get(`${BASE_URL}//terminal-list/${sessionId}`),

  kickOutByUsername: (username) => http.post(`${BASE_URL}/kick-out-by-username/${username}`),

  kickOutByToken: (data) => http.post(`${BASE_URL}/kick-out-by-token`, data),
};
