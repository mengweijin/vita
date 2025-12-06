import http from "@/utils/http";

const URL_PREFIX = "/monitor/sse";

export const sseApi = {
	close: () => http.post(`${URL_PREFIX}/close`),
};
