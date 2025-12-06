import http from "@/utils/http";

const URL_PREFIX = "/system/file";

export const fileApi = {
	create: (data) => http.post(`${URL_PREFIX}/create`, data),

	download: (id, fileName = undefined) => http.download(`${URL_PREFIX}/download/${id}`, fileName),

	list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),
	page: (args) => http.get(`${URL_PREFIX}/page`, { params: args }),

	remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

	update: (data) => http.post(`${URL_PREFIX}/update`, data),

	upload: () => http.post(`${URL_PREFIX}/upload`),
};
