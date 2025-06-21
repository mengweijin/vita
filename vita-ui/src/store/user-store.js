import { loginApi } from '@/api/login-api';

const { VITE_APP_PREFIX } = import.meta.env;

export const useUserStore = defineStore(
  `${VITE_APP_PREFIX}-user`,
  () => {
    const user = ref(null);

    const initUser = async (token) => {
      // 先保存 token
      user.value = { token: token };
      // 这个请求就需要 token
      let loginUser = await loginApi.getLoginUser();
      // 重新赋值 user
      user.value = loginUser;
    };

    const getToken = () => user.value?.token;

    const getBearerToken = () => `Bearer ${getToken()}`;

    const getRoles = () => user.value?.roles;

    const getPermissions = () => user.value?.permissions;

    const isLogin = () => (user.value?.token ? true : false);

    const clear = () => (user.value = null);

    return { user, initUser, getToken, getBearerToken, getRoles, getPermissions, isLogin, clear };
  },
  {
    persist: {
      storage: localStorage,
    },
  },
);
