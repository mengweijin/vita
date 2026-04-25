import http from "@/utils/http.js";

const URL_PREFIX = "";

export const loginApi = {
  getCaptcha: () => http.get(`${URL_PREFIX}/captcha`, { params: {} }),

  getCaptchaEnabled: () => http.get(`${URL_PREFIX}/captchaEnabled`, { params: {} }),

  login: (data) => http.post(`${URL_PREFIX}/login`, data, { loading: false, message: false }),

  logout: () => http.post(`${URL_PREFIX}/logout`, null, { message: false }),

  tokenValid: (tokenValue) =>
    http.get(`${URL_PREFIX}/token-valid`, { params: { token: tokenValue } }),
};
