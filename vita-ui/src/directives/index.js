/** 动态注册指令。只有使用 Vite 的 glob 导入才不会在打包后路径失效 */
const directives = import.meta.glob("./modules/**/*.js", { eager: true });

export default {
  install(app) {
    Object.entries(directives).forEach(([path, module]) => {
      const name = path.match(/([^/]+).js$/) || null;
      if (name && module.default) {
        app.directive(name[1], module.default);
      }
    });
  },
};
