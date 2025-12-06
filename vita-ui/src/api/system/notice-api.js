import http from "@/utils/http";

const URL_PREFIX = "/system/notice";

export const noticeApi = {
	create: (data) => http.post(`${URL_PREFIX}/create`, data),

	list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),
	page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

	release: (id) => http.post(`${URL_PREFIX}/release/${id}`),

	remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

	revoke: (id) => http.post(`${URL_PREFIX}/revoke/${id}`),

	update: (data) => http.post(`${URL_PREFIX}/update`, data),
};
