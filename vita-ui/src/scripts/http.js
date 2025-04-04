import { userStorage } from "@/storage/session/userStorage.js";
import { admin } from "@/scripts/admin.js";
import { utils } from "@/scripts/utils.js";

const API_BASE = import.meta.env.VITE_API_BASE;

let $ = layui.$;
let layer = layui.layer;

let http = {
  get: function (opts) {
    opts.method = "GET";
    opts.processData = true;
    return this.request(opts);
  },

  post: function (opts) {
    opts.method = "POST";
    opts.processData = false;
    return this.request(opts);
  },

  request: function (opts) {
    return new Promise(function (resolve, reject) {
      $.ajax({
        loading: opts.loading ?? true,
        method: opts.method ?? "POST",
        url: opts.url,
        data: opts.data,
        async: opts.async ?? true,
        cache: opts.cache ?? true,
        // 请求数据类型
        contentType: opts.contentType ?? "application/json;charset=UTF-8",
        // processData: false; 关闭 data 参数自动转换为查询字符串格式（如 key1=value1&key2=value2）
        processData: opts.processData ?? false,
        // 响应数据类型
        dataType: opts.dataType ?? "json",
        headers: opts.header ?? {
          // 认证令牌
          Authorization: "Bearer " + userStorage.getToken(),
        },
        beforeSend: function (xhr) {
          if (this.loading && loadingIndex === -1) {
            layer.load(2, { shade: [0.5, "#393D49"] });
            ++loadingIndex;
          }

          if (this.dataType !== "html") {
            let delimiter = "/";
            let apiUrl = utils.removePrefix(this.url, delimiter);
            let apiBase = utils.removeSuffix(API_BASE, delimiter);
            this.url = `${apiBase}/${apiUrl}`;
          }

          if (this.data && this.method.toUpperCase() === "POST") {
            this.data = JSON.stringify(this.data);
          }
        },
        success: function (response) {
          resolve(response);
        },
        error: function (xhr, textStatus, errorThrown) {
          let message = xhr?.responseJSON?.msg;
          console.error(xhr?.responseJSON);
          switch (xhr.status) {
            case 400:
              layer.msg(message, { icon: 0 });
              break;
            case 401:
              message = "未登录或会话已过期！";
              layer.alert(
                message,
                { icon: 0, closeBtn: 0, title: "会话过期提醒！" },
                function (index) {
                  layer.close(index);
                  // 清空 sessionStorage
                  sessionStorage.clear();
                  // 跳转登录页
                  admin.loadLogin();
                }
              );
              break;
            case 403:
              layer.open({
                icon: 4,
                title: "无权限！",
                content: message,
                closeBtn: 0,
                yes: function (index, layero, that) {
                  layer.close(index);
                },
              });
              break;
            case 404:
              layer.msg(message, { icon: 2, title: "找不到资源！" });
              break;
            case 408:
              layer.msg("请求超时！", { icon: 2 });
              break;
            default:
              layer.msg("服务器发生异常，请联系管理员！", { icon: 2 });
              break;
          }

          reject(errorThrown);
        },
        // 请求完成时运行的函数（在请求成功或失败之后均调用，即在 success 和 error 函数之后）。
        complete: function (xhr, textStatus) {
          if(--loadingIndex === -1) {
            // 关闭所有loading
            layer.closeAll('loading');
          }
          if (this.url.endsWith("/login")) {
            return;
          } else if (this.method.toUpperCase() !== "GET" && xhr.status == 200) {
            layer.msg("操作成功！", { icon: 1, time: 800 });
          }
        },
      });
    });
  },
};

export { http };
