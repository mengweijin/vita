import http from '@/utils/http';

const URL_PREFIX_DICT_TYPE = '/system/dict-type';

export const dictTypeApi = {
  page: (args) => http.get(`${URL_PREFIX_DICT_TYPE}/page`, { params: args }),

  list: (args) => http.get(`${URL_PREFIX_DICT_TYPE}/list`, { params: args }),

  create: (data) => http.post(`${URL_PREFIX_DICT_TYPE}/create`, data),

  update: (data) => http.post(`${URL_PREFIX_DICT_TYPE}/update`, data),

  remove: (ids) => http.post(`${URL_PREFIX_DICT_TYPE}/remove/${ids}`),
};

const URL_PREFIX_DICT_DATA = '/system/dict-data';

export const dictDataApi = {
  page: (args) => http.get(`${URL_PREFIX_DICT_DATA}/page`, { params: args }),

  list: (args) => http.get(`${URL_PREFIX_DICT_DATA}/list`, { params: args }),

  create: (data) => http.post(`${URL_PREFIX_DICT_DATA}/create`, data),

  update: (data) => http.post(`${URL_PREFIX_DICT_DATA}/update`, data),

  remove: (ids) => http.post(`${URL_PREFIX_DICT_DATA}/remove/${ids}`),
};
