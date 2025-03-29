import { http } from "@/scripts/http.js";

let useLogin = {
  login: (data) => http.post({ url: "/login", data: data }),
  logout: () => http.post({ url: "/logout"}),
  captcha: () => http.get({ url: "/captcha"}),
  captchaEnabled: () => http.get({ url: "/captchaEnabled"}),
};

export { useLogin };
