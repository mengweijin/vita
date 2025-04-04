import { createRouter, createWebHistory } from 'vue-router'

/** 路由数据 */
const routes = []

/** 自动导入全部静态路由：https://cn.vitejs.dev/guide/features.html#negative-patterns */
const modules = import.meta.glob(['./modules/**/*.js'], {
  // 急切加载，立即导入所有匹配模块，返回模块对象而非异步函数
  eager: true,
})

// 导入静态路由
Object.keys(modules).forEach((key) => {
  routes.push(modules[key].default)
})

/** 路由实例 */
export const router = createRouter({
  history: createWebHistory(),
  routes: routes,
  // 严格匹配模式
  strict: true,
})

export default router
