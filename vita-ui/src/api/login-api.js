import http from '@/utils/http';

const BASE_URL = '';

export const loginApi = {
  getCaptchaEnabled: () => http.get(`${BASE_URL}/captchaEnabled`, { params: {} }),

  getCaptcha: () => http.get(`${BASE_URL}/captcha`, { params: {} }),

  login: (data) => http.post(`${BASE_URL}/login`, data),

  logout: () => http.post(`${BASE_URL}/logout`),
};
