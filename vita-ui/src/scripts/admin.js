import { template } from "@/scripts/template.js";
import { userStorage } from "@/storage/session/userStorage.js";
import { utils } from "@/scripts/utils.js";
import useLogin from "@/api/login.js";

/** admin 组件 */
let admin = {

  /** 是否已登录 */
  isLogin: function () {
    return !utils.isEmpty(userStorage.get());
  },

  /** 登录 */
  login: function (data) {
    useLogin.login(data).then((r) => {
      userStorage.set(r.data);
      this.loadAdmin();
    });
  },

  /** 登出 */
  logout: function () {
    useLogin.logout().then((r) => {
      // 清空 sessionStorage
      sessionStorage.clear();
      // 跳转登录页
      this.loadLogin();
    });
  },

  /** 默认值为 undefined 时才触发 ES6 函数默认值的赋值，null 不会触发。*/
  loadAdmin: function () {
    template.load("#app", "src/views/admin.html");
  },

  loadLogin: function () {
    template.load("#app", "src/views/login.html");
  },

  loadHeader: function () {
    template.load(".layui-header", "src/views/layout/lay-header.html");
  },

  loadSide: function () {
    template.load(".layui-side", "src/views/layout/lay-side.html");
  },

  loadBody: function () {
    template.load(".layui-body", "src/views/layout/lay-body.html");
  },
  loadFooter: function () {
    template.load(".layui-footer", "src/views/layout/lay-footer.html");
  },
};

export { admin };
