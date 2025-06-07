import http from '@/utils/http';

const BASE_URL = '';

export const loginApi = {
  getCaptchaEnabled: () => http.get(`${BASE_URL}/captchaEnabled`, { params: {} }),

  getCaptcha: () => http.get(`${BASE_URL}/captcha`, { params: {} }),

  login: (data) => http.post(`${BASE_URL}/login`, data, { loading: false }),

  logout: () => http.post(`${BASE_URL}/logout`),

  getLoginUser: () => http.get(`${BASE_URL}/get/login-user`, { params: {} }),
};
