import http from "@/utils/http";

const URL_PREFIX = "/system/message";

export const messageApi = {
	create: (data) => http.post(`${URL_PREFIX}/create`, data),

	list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

	page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

	remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),
	selectNotViewedCount: () => http.get(`${URL_PREFIX}/query/not-viewed-count`),

	setNotViewed: (messageReceiverIds) => http.post(`${URL_PREFIX}/set-not-viewed/${messageReceiverIds}`),

	setViewed: (messageReceiverIds) => http.post(`${URL_PREFIX}/set-viewed/${messageReceiverIds}`),

	update: (data) => http.post(`${URL_PREFIX}/update`, data),
};
