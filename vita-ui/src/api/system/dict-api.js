import http from '@/utils/http'

const BASE_URL_DICT_TYPE = '/system/dict-type'

const BASE_URL_DICT_DATA = '/system/dict-data'

export const dictTypeApi = {
  page: (args) => http.get(`${BASE_URL_DICT_TYPE}/page`, { params: args }),

  list: (args) => http.get(`${BASE_URL_DICT_TYPE}/list`, { params: args }),

  create: (data) => http.post(`${BASE_URL_DICT_TYPE}/create`, data),

  update: (data) => http.post(`${BASE_URL_DICT_TYPE}/update`, data),

  remove: (ids) => http.post(`${BASE_URL_DICT_TYPE}/remove/${ids}`),
}

export const dictDataApi = {
  page: (args) => http.get(`${BASE_URL_DICT_DATA}/page`, { params: args }),

  list: (args) => http.get(`${BASE_URL_DICT_DATA}/list`, { params: args }),

  create: (data) => http.post(`${BASE_URL_DICT_DATA}/create`, data),

  update: (data) => http.post(`${BASE_URL_DICT_DATA}/update`, data),

  remove: (ids) => http.post(`${BASE_URL_DICT_DATA}/remove/${ids}`),
}
