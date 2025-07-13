import http from '@/utils/http';

const URL_PREFIX = '/monitor/user-online';

export const userOnlineApi = {
  page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

  kickOutByUsername: (username) => http.post(`${URL_PREFIX}/kick-out-by-username/${username}`),
};
