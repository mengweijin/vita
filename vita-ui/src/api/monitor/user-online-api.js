import http from "@/utils/http";

const URL_PREFIX = "/monitor/user-online";

export const userOnlineApi = {
	kickOutByToken: (token) => http.post(`${URL_PREFIX}/kick-out-by-token`, { encryptTokenValue: token }),

	kickOutByUsername: (username) => http.post(`${URL_PREFIX}/kick-out-by-username/${username}`),
	page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),
};
