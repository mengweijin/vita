import http from '@/utils/http';

const URL_PREFIX = '/monitor/user-online';

export const userOnlineApi = {
  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  terminalList: (sessionId) => http.get(`${URL_PREFIX}//terminal-list/${sessionId}`),

  kickOutByUsername: (username) => http.post(`${URL_PREFIX}/kick-out-by-username/${username}`),

  kickOutByToken: (data) => http.post(`${URL_PREFIX}/kick-out-by-token`, data),
};
