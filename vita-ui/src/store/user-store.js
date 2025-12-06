import { loginApi } from "@/api/login-api.js";

const { VITE_APP_PREFIX } = import.meta.env;

export const useUserStore = defineStore(
	`${VITE_APP_PREFIX}-user`,
	() => {
		const user = ref(null);

		const initUser = async () => {
			// 这个请求就需要 token
			user.value = await loginApi.getLoginUser();
		};

		const getRoles = () => user.value?.roles;

		const getPermissions = () => user.value?.permissions;

		const clear = () => {
			user.value = null;
		};

		return { clear, getPermissions, getRoles, initUser, user };
	},
	{
		persist: {
			storage: sessionStorage,
		},
	},
);
