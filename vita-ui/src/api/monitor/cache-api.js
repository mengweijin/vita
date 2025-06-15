import http from '@/utils/http';

const BASE_URL = '/monitor/cache';

export const monitorCacheApi = {
  names: () => http.get(`${BASE_URL}/names`),

  query: (cacheName) => http.get(`${BASE_URL}/query?cacheName=${cacheName}`),

  queryCacheByNameAndKey: (cacheName, cacheKey) =>
    http.get(`${BASE_URL}/queryCacheByNameAndKey?cacheName=${cacheName}&cacheKey=${cacheKey}`),

  remove: (cacheName, cacheKey) => http.post(`${BASE_URL}/remove?cacheName=${cacheName}&cacheKey=${cacheKey}`),

  clear: () => http.post(`${BASE_URL}/clear`),
};
