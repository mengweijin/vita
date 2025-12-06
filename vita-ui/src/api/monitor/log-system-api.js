import http from "@/utils/http";

const URL_PREFIX = "/monitor/log-system";

export const logSystemApi = {
	create: (data) => http.post(`${URL_PREFIX}/create`, data),

	list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),
	page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

	remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

	update: (data) => http.post(`${URL_PREFIX}/update`, data),
};
