export default {
	children: [
		{
			component: () => import("@/views/error/403.vue"),
			meta: {
				title: "禁止访问",
			},
			name: "403",
			path: "/error/403",
		},
		{
			component: () => import("@/views/error/404.vue"),
			meta: {
				title: "页面走丢了",
			},
			name: "404",
			path: "/error/404",
		},
	],
	name: "error",
	path: "/error",
	redirect: "/error/403",
};
