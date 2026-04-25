import http from "@/utils/http";

const URL_PREFIX = "/monitor/cache-local";

export const cacheLocalApi = {
  clear: () => http.post(`${URL_PREFIX}/clear`),

  clearByName: (cacheName) => http.post(`${URL_PREFIX}/clear/by/name/${cacheName}`),

  query: (cacheName) => http.get(`${URL_PREFIX}/query?cacheName=${cacheName}`),

  queryCacheByNameAndKey: (cacheName, cacheKey) =>
    http.get(`${URL_PREFIX}/query/cache/by/nameAndKey?cacheName=${cacheName}&cacheKey=${cacheKey}`),
  queryCacheNames: () => http.get(`${URL_PREFIX}/query/cacheNames`),

  remove: (cacheName, cacheKey) =>
    http.post(`${URL_PREFIX}/remove?cacheName=${cacheName}&cacheKey=${cacheKey}`),
};
