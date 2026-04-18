import http from "@/utils/http";

const URL_PREFIX = "/system/category";

export const categoryApi = {
  create: (data) => http.post(`${URL_PREFIX}/create`, data),

  list: (args) => http.get(`${URL_PREFIX}/list`, { params: args }),

  listChildrenByParentCode: (code) =>
    http.get(`${URL_PREFIX}/list/children/by/parentCode/${code}`),

  listChildrenByParentId: (parentId) =>
    http.get(`${URL_PREFIX}/list/children/by/parentId/${parentId}`),

  listChildrenWithParentByCode: (code) =>
    http.get(`${URL_PREFIX}/list/childrenWithParent/by/code/${code}`),

  pageRoot: (args) => http.get(`${URL_PREFIX}/page/root`, { params: args }),

  remove: (ids) => http.post(`${URL_PREFIX}/remove/${ids}`),

  update: (data) => http.post(`${URL_PREFIX}/update`, data),
};
