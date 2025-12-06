const routes = [
	{
		path: "/",
		name: "Home",
		component: () => import("@/views/Home.vue"),
		meta: { title: "首页" },
	},
	{
		path: "/user",
		name: "User",
		component: () => import("@/views/user/UserList.vue"),
		meta: { title: "用户列表", keepAlive: true },
	},
	{
		path: "/user/add",
		name: "UserAdd",
		component: () => import("@/views/user/UserAdd.vue"),
		meta: { title: "添加用户" },
	},
	{
		path: "/role",
		name: "Role",
		component: () => import("@/views/Role.vue"),
		meta: { title: "角色管理", keepAlive: true },
	},
	{
		path: "/menu",
		name: "Menu",
		component: () => import("@/views/Menu.vue"),
		meta: { title: "菜单管理", keepAlive: true },
	},
];

const router = createRouter({
	history: createWebHistory(),
	routes,
});

export default router;
