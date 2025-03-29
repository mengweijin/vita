import { http } from "@/scripts/http.js";

const BASE_URL = "/system/menu";

let useMenu = {
  page: (params) => http.get({ url: `${BASE_URL}/page`, data: params }),
  list: (params) => http.get({ url: `${BASE_URL}/list`, data: params }),
  create: (data) => http.post({ url: `${BASE_URL}/create`, data: data }),
  update: (data) => http.post({ url: `${BASE_URL}/update`, data: data }),
  delete: (ids) => http.post({ url: `${BASE_URL}/delete/${ids}` }),
};

export { useMenu };
