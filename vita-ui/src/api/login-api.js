import http from "@/utils/http.js";

const URL_PREFIX = "";

export const loginApi = {
    getCaptcha: () => http.get(`${URL_PREFIX}/captcha`, {params: {}}),

    getCaptchaEnabled: () => http.get(`${URL_PREFIX}/captchaEnabled`, {params: {}}),

    login: (data) => http.post(`${URL_PREFIX}/login`, data, {loading: false, message: false}),

    logout: () => http.post(`${URL_PREFIX}/logout`, null, {message: false}),

    tokenValid: (tokenValue) =>
        http.get(`${URL_PREFIX}/token-valid`, {params: {token: tokenValue}}),

    /**
     * @typedef {Object} R
     * @property {Number} code
     * @property {String} msg
     * @property {Object} data
     * @property {String} time
     */
    /**
     * 判断用户是否已登录
     * @description 该接口用于判断用户是否已登录，主要用于前端路由守卫中。
     * @returns {R} r
     */
    isLogin: () => http.get(`${URL_PREFIX}/isLogin`),
};
