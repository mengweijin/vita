import { createRouter, createWebHashHistory } from 'vue-router'

import { useUserStore } from '@/stores/userStore.js'

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

// 捕获所有未匹配路径，跳转 404 页面。这个一定要放在路由列表的最后面！
routes.push({
  path: '/:pathMatch(.*)*',
  redirect: '/error/404',
})

/** 路由实例 */
const router = createRouter({
  history: createWebHashHistory(),
  routes: routes,
  // 严格匹配模式
  strict: true,
})

// 全局前置守卫 https://router.vuejs.org/zh/guide/advanced/navigation-guards.html
router.beforeEach((to, from) => {
  const userStore = useUserStore()
  const { isLogin } = userStore

  if (!isLogin() && to.fullPath !== '/login') {
    // 未登录且访问受保护路由，强制跳转登录页
    return { path: '/login' }
  }
  if (isLogin() && to.fullPath === '/login') {
    // 已登录但访问登录页。强制跳转首页
    return '/'
  }
  // 其它情况默认放行路由
})

export default router
