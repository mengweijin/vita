import http from '@/utils/http.js'

const BASE_URL = ''

export const getCaptchaEnabled = () => http.get(`${BASE_URL}/captchaEnabled`, { params: {} })

export const getCaptcha = () => http.get(`${BASE_URL}/captcha`, { params: {} })

export const login = (data) => http.post(`${BASE_URL}/login`, data)

export const logout = () => http.post(`${BASE_URL}/logout`)
