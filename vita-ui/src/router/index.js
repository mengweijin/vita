import { useLoginStore } from "@/store/login-store.js";
import NProgress from "@/utils/nprogress.js";
import utils from "@/utils/utils.js";
import { createRouter, createWebHashHistory } from "vue-router";
import { handleHotUpdate, routes } from "vue-router/auto-routes";

const { VITE_APP_TITLE } = import.meta.env;

/**
 * 将多级嵌套路由处理成一维数组
 * @param {Array} routeList 传入路由对象
 * @returns 返回处理后的一维路由
 */
const flattenRoutes = (routeList = []) => {
  const flatRoutes = [];

  routeList.forEach((route) => {
    processRoute(route);
  });

  function processRoute(route = {}, parentPath = "", parentMeta = {}) {
    // 处理路径
    parentPath = utils.trimSpecified(parentPath, "/");
    const childPath = utils.trimSpecified(route.path, "/");
    const fullPath = `/${utils.join("/", true, parentPath, childPath)}`;

    // 合并 meta 信息
    const mergedMeta = {
      ...parentMeta,
      ...route.meta,
    };
    // 设置 layout 布局
    mergedMeta.layout = mergedMeta.layout ? mergedMeta.layout : "default";

    // 递归处理子路由
    if (route.children && route.children.length > 0) {
      route.children.forEach((child) => {
        processRoute(child, fullPath, mergedMeta);
      });
    } else {
      // 没有子路由时才添加当前路由
      const flatRoute = {
        ...route,
        meta: mergedMeta,
        path: fullPath,
      };
      // 移除 children
      delete flatRoute.children;

      flatRoutes.push(flatRoute);
    }
  }

  return flatRoutes;
};

const allFlatRoutes = flattenRoutes(routes);

const noneLayoutFlatRoutes = allFlatRoutes.filter((route) => {
  return route.meta.layout === "none";
});

const defaultLayoutFlatRoutes = allFlatRoutes.filter((route) => {
  return route.meta.layout === "default";
});

// 全部作为 Layout 下的二级路由
const defaultLayoutRoutes = [
  {
    children: defaultLayoutFlatRoutes,
    component: () => import("@/layout/lay-index.vue"),
    name: "Layout",
    path: "/",
    redirect: "/home",
  },
];

const customRoutes = [
  // 捕获所有未匹配路径，跳转 404 页面。这个一定要放在路由列表的最后面！
  {
    path: "/:pathMatch(.*)*",
    redirect: "/error/404",
  },
];

/** 路由实例 */
const router = createRouter({
  history: createWebHashHistory(),
  routes: [...noneLayoutFlatRoutes, ...defaultLayoutRoutes, ...customRoutes],
  // 刷新时，还原滚动条位置
  scrollBehavior(_to, _from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    }
    return { top: 0 };
  },
  // 严格匹配模式
  strict: true,
});

// This will update routes at runtime without reloading the page
if (import.meta.hot) {
  handleHotUpdate(router);
}

// 全局前置守卫 https://router.vuejs.org/zh/guide/advanced/navigation-guards.html
router.beforeEach(async (to, _from) => {
  NProgress.start();
  // 设置标题
  const title = to?.meta?.title;
  if (title) {
    document.title = `${title} | ${VITE_APP_TITLE}`;
  } else {
    document.title = `${VITE_APP_TITLE}`;
  }

  const loginStore = useLoginStore();
  const isLogin = await loginStore.isLogin();

  if (isLogin) {
    // 已登录但访问登录页。强制跳转到参数页或首页
    if (to.fullPath.startsWith("/login")) {
      return { path: to.query.redirect || "/" };
    }
  } else {
    // 未登录且访问受保护路由，强制跳转登录页，并携带访问路径。（ to.fullPath = '/login?redirect=/home' ）
    if (!to.fullPath.startsWith("/login")) {
      return {
        path: "/login",
        query: {
          redirect: to.fullPath.startsWith("/error") ? "/" : to.fullPath,
        },
      };
    }
  }

  // 其它情况默认放行路由
});

router.afterEach(() => {
  NProgress.done();
});

export default router;
