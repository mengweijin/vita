import http from "@/utils/http.js";

const URL_PREFIX_DICT_TYPE = "/system/dict-type";

export const dictTypeApi = {
  create: (data) => http.post(`${URL_PREFIX_DICT_TYPE}/create`, data),

  getById: (id) => http.get(`${URL_PREFIX_DICT_TYPE}/${id}`),

  list: (args) => http.get(`${URL_PREFIX_DICT_TYPE}/list`, { params: args }),
  page: (args) => http.get(`${URL_PREFIX_DICT_TYPE}/page`, { params: args }),

  remove: (ids) => http.post(`${URL_PREFIX_DICT_TYPE}/remove/${ids}`),

  update: (data) => http.post(`${URL_PREFIX_DICT_TYPE}/update`, data),
};

const URL_PREFIX_DICT_DATA = "/system/dict-data";

export const dictDataApi = {
  create: (data) => http.post(`${URL_PREFIX_DICT_DATA}/create`, data),

  list: (args) => http.get(`${URL_PREFIX_DICT_DATA}/list`, { params: args }),
  page: (args) => http.get(`${URL_PREFIX_DICT_DATA}/page`, { params: args }),

  remove: (ids) => http.post(`${URL_PREFIX_DICT_DATA}/remove/${ids}`),

  update: (data) => http.post(`${URL_PREFIX_DICT_DATA}/update`, data),
};
