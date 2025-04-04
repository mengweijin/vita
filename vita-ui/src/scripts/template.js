import { utils } from "@/scripts/utils.js";
import { http } from "@/scripts/http.js";
import { routerStorage } from "@/storage/session/routerStorage.js";

let $ = layui.jquery;
let layer = layui.layer;

/**
 * 模板组件
 */
let template = {
  /**
   * 加载页面
   * @param {String} elem 元素选择器，新页面会被加载到选择的元素下面。推荐使用 #id 选择器。
   * @param {String} src 模板的 URL。比如：'src/views/admin.html'
   * @param {Object} data 模板渲染所需要的数据对象
   */
  load: function (elem, src, data = {}) {
    http.get({ url: src, dataType: "html" }).then((template) => {
      if (!utils.isEmpty(data)) {
        routerStorage.set(src, data);
      }
      // 先清空内容以及绑定的事件，避免残留元素导致事件叠加
      $(elem).empty().html(template);
      $(elem).children("div").first().attr("data-src", src);
      this.reloadScripts(elem);
    });
  },

  /**
   * 重新加载 html 中的 <script> 脚本，以解决动态加载页面时，<script> 脚本不执行的问题。
   * @param {String} elem
   */
  reloadScripts: function (elem) {
    let targetElement = document.querySelector(elem);
    let scripts = targetElement.getElementsByTagName("script");
    Array.from(scripts).forEach((oldScript) => {
      let newScript = document.createElement("script");
      newScript.type = oldScript.type ? oldScript.type : "text/javascript";
      if (oldScript.src) {
        newScript.src = oldScript.src + "?t=" + Date.now(); // 防缓存
      } else {
        newScript.textContent = oldScript.innerHTML;
      }
      // 移除旧脚本
      oldScript.remove();
      // 追加新脚本
      targetElement.appendChild(newScript);
    });
  },
};

export { template };
