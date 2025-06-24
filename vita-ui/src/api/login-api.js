import http from '@/utils/http';

const URL_PREFIX = '';

export const loginApi = {
  getCaptchaEnabled: () => http.get(`${URL_PREFIX}/captchaEnabled`, { params: {} }),

  getLoginOtpEnabled: () => http.get(`${URL_PREFIX}/loginOtpEnabled`, { params: {} }),

  getCaptcha: () => http.get(`${URL_PREFIX}/captcha`, { params: {} }),

  login: (data) => http.post(`${URL_PREFIX}/login`, data, { loading: false }),

  logout: () => http.post(`${URL_PREFIX}/logout`),

  getLoginUser: () => http.get(`${URL_PREFIX}/get/login-user`, { params: {} }),
};
