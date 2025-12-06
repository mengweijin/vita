export default {
	children: [
		{
			component: () => import("@/views/home/home-view.vue"),
			meta: {
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
