import { utils } from "@/scripts/utils.js";

let $ = layui.jquery;

/** 左侧菜单 */
let sideMenu = {
  isCollapse: function () {
    return $(".layui-side").width() === 0;
  },

  // 折叠
  collapse: function (width) {
    $(".layui-side").animate(
      { width: width },
      { queue: false, duration: 300 },
      "linear"
    );
    
    if(utils.getCssVar("--vt-menu-width") === width && utils.isMobile()) {
      return;
    }
    
    $(".layui-body").animate(
      { left: width },
      { queue: false, duration: 300 },
      "linear"
    );
  },

  // 折叠/展开 切换
  toggle: function () {
    this.isCollapse() ? this.collapse(utils.getCssVar("--vt-menu-width")) : this.collapse("0px");
  },
};

export { sideMenu };
