import { createRouter, createWebHashHistory } from "vue-router";
import { useLoginStore } from "@/store/login-store.js";
import { useMenuStore } from "@/store/menu-store.js";
import NProgress from "@/utils/nprogress.js";

const { VITE_APP_TITLE } = import.meta.env;

/** 路由数据 */
const routes = [];

/** 自动导入全部静态路由：https://cn.vitejs.dev/guide/features.html#negative-patterns */
const modules = import.meta.glob(["./modules/**/*.js"], {
	// 急切加载，立即导入所有匹配模块，返回模块对象而非异步函数
	eager: true,
});

// 导入静态路由
Object.keys(modules).forEach((key) => {
	routes.push(modules[key].default);
});

// 捕获所有未匹配路径，跳转 404 页面。这个一定要放在路由列表的最后面！
routes.push({
	path: "/:pathMatch(.*)*",
	redirect: "/error/404",
});

// 路由组件列表（只有使用 Vite 的 glob 导入才不会在打包后路径失效）
const components = import.meta.glob("../views/**/*.vue");

/**
 * 动态注册路由​（全部作为 Layout 下的二级路由）
 * 将接口数据转换为 Vue Router 可识别的路由对象，并用 router.addRoute() 动态添加
 */
const addDynamicRoutes = (menuList = [], parentRouteName = "Layout") => {
	menuList
		.filter((menu) => "DIR" === menu.type || "MENU" === menu.type)
		.forEach((menu) => {
			const config = {
				children: () => (menu.children ? addDynamicRoutes(menu.children, menu.routeName) : []),
				component: components[`../views/${menu.component}`],
				meta: {
					title: menu.title,
				},
				name: menu.routeName,
				path: menu.routePath,
			};

			if (!router.hasRoute(menu.routeName)) {
				// 添加到父路由或根路由
				parentRouteName ? router.addRoute(parentRouteName, config) : router.addRoute(config);
			}
		});
};

export const initDynamicRoutes = () => {
	const menuStore = useMenuStore();
	const menus = menuStore.get();
	addDynamicRoutes(menus);
};

/** 路由实例 */
const router = createRouter({
	history: createWebHashHistory(),
	routes: routes,
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
	const menuStore = useMenuStore();
	const { isDynamicRoutesAdded, setDynamicRoutesAdded } = menuStore;

	if (isLogin) {
		// 增加动态路由
		if (!isDynamicRoutesAdded()) {
			initDynamicRoutes();
			setDynamicRoutesAdded(true);
			// 保持在当前页面，需要返回一个与 to 完全相同的路由对象，以确保 addRoute 之后，新的路由规则生效
			return { ...to, replace: true };
		}

		// 已登录但访问登录页。强制跳转到参数页或首页
		if (to.fullPath.startsWith("/login")) {
			return { path: to.query.redirect || "/" };
		}
	} else {
		// 未登录且访问受保护路由，强制跳转登录页，并携带访问路径。（ to.fullPath = '/login?redirect=/home' ）
		if (!to.fullPath.startsWith("/login")) {
			return { path: "/login", query: { redirect: to.fullPath.startsWith("/error") ? "/" : to.fullPath } };
		}
	}

	// 其它情况默认放行路由
});

router.afterEach(() => {
	NProgress.done();
});

export default router;
