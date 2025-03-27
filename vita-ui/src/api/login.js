import { http } from "@/scripts/http.js";

export const loginApi = (data) => http.post({ url: "/login", data: data });

export const logoutApi = () => http.post({ url: "/logout"});

export const captchaApi = () => http.get({ url: "/captcha"});

export const captchaEnabledApi = () => http.get({ url: "/captchaEnabled"});
