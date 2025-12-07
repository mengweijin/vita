export default {
	children: [
		{
			component: () => import("@/views/home/home-view.vue"),
			meta: {
				// 首页标签页不可关闭
				closable: false,
				title: "首页",
			},
			name: "HomeView",
			path: "/home",
		},
	],
	component: () => import("@/layout/lay-index.vue"),
	name: "Layout",
	path: "/",
	redirect: "/home",
};
