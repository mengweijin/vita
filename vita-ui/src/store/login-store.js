import { useDictStore } from "@/store/dict-store.js";
import { useMenuStore } from "@/store/menu-store.js";
import { useTabsStore } from "@/store/tabs-store.js";
import { useUserStore } from "@/store/user-store.js";
import utils from "@/utils/utils.js";

const { VITE_APP_PREFIX } = import.meta.env;

const TOKEN_KEY = `${VITE_APP_PREFIX}-token`;

export const useLoginStore = defineStore(
	`${VITE_APP_PREFIX}-login`,
	() => {
		const token = ref(null);

		const setToken = (newToken) => {
			token.value = newToken;
		};

		const getToken = () => {
			return token.value;
		};

		const getBearerToken = () => {
			return `Bearer ${getToken()}`;
		};

		const removeToken = () => {
			token.value = null;
		};

		const setLocalStorageToken = (newToken) => {
			localStorage.setItem(TOKEN_KEY, newToken);
		};

		const getLocalStorageToken = () => {
			return localStorage.getItem(TOKEN_KEY);
		};

		const removeLocalStorageToken = () => {
			localStorage.removeItem(TOKEN_KEY);
		};

		const isLogin = async () => {
			let token = getToken();
			if (utils.isNotBlank(token)) {
				return true;
			}

			token = getLocalStorageToken();
			if (utils.isNotBlank(token)) {
				setToken(token);
				await initData();
				return true;
			}
			return false;
		};

		/**
		 * 初始化用户数据
		 */
		const initData = async () => {
			// 初始化用户基本信息、角色、权限等
			await useUserStore().initUser();
			// 加载菜单
			await useMenuStore().refresh();
			// 加载字典
			await useDictStore().refresh();
		};

		/**
		 * 前端登出
		 */
		const logout = () => {
			removeToken();
			removeLocalStorageToken();
			useUserStore().clear();
			useDictStore().clear();
			useMenuStore().clear();
			useTabsStore().clear();
		};

		return {
			getBearerToken,
			getLocalStorageToken,
			getToken,
			initData,
			isLogin,
			logout,
			removeLocalStorageToken,
			removeToken,
			setLocalStorageToken,
			setToken,
			token,
		};
	},
	{
		persist: {
			storage: sessionStorage,
		},
	},
);
