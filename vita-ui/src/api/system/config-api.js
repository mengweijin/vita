import http from "@/utils/http";

const URL_PREFIX = "/system/config";

export const configApi = {
	create: (data) => http.post(`${URL_PREFIX}/create`, data),
	getByCode: (code) => http.get(`${URL_PREFIX}/get-by-code/${code}`, {}),

	list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

	page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

	remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

	update: (data) => http.post(`${URL_PREFIX}/update`, data),
};
