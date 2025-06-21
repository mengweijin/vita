import http from '@/utils/http';

const URL_PREFIX = '/monitor/cache';

export const cacheApi = {
  names: () => http.get(`${URL_PREFIX}/names`),

  query: (cacheName) => http.get(`${URL_PREFIX}/query?cacheName=${cacheName}`),

  queryCacheByNameAndKey: (cacheName, cacheKey) =>
    http.get(`${URL_PREFIX}/queryCacheByNameAndKey?cacheName=${cacheName}&cacheKey=${cacheKey}`),

  remove: (cacheName, cacheKey) => http.post(`${URL_PREFIX}/remove?cacheName=${cacheName}&cacheKey=${cacheKey}`),

  clearByName: (cacheName) => http.post(`${URL_PREFIX}/clear-by-name/${cacheName}`),

  clear: () => http.post(`${URL_PREFIX}/clear`),
};
