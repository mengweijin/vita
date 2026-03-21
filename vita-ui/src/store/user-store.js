import { userApi } from "@/api/system/user-api.js";

const { VITE_APP_PREFIX } = import.meta.env;

export const useUserStore = defineStore(
  `${VITE_APP_PREFIX}-user`,
  () => {
    const user = ref(null);

    const initUser = async () => {
      // 这个请求就需要 token
      user.value = await userApi.queryUserStoreVO();
    };

    const getRoles = () => user.value?.roles || [];

    const getPermissions = () => user.value?.permissions || [];

    /**
     * 指定权限单个字符串：v-permission="'system:user:create'"
     * 指定权限字符串数组：v-permission="['system:user:create','system:user:update']"
     * @param {string | string[]} value
     * @param {boolean} anyMatch true: 任意一个匹配到即可。false: 需要所有匹配才行。
     * @returns
     */
    const hasPermission = (value, anyMatch = false) => {
      const permissionList = getPermissions();
      const valueArray = Array.isArray(value) ? value : [value];
      if (anyMatch) {
        return valueArray.some((perm) => permissionList.includes(perm));
      }
      return valueArray.every((perm) => permissionList.includes(perm));
    };

    /**
     * 指定角色单个字符串：v-role="'role_admin'"
     * 指定角色字符串数组：v-role="['role_admin','role_guest']"
     * @param {string | string[]} value
     *@param {boolean} anyMatch true: 任意一个匹配到即可。false: 需要所有匹配才行。
     * @returns
     */
    const hasRole = (value, anyMatch) => {
      const roleList = getRoles();
      const valueArray = Array.isArray(value) ? value : [value];
      if (anyMatch) {
        return valueArray.some((role) => roleList.includes(role));
      }
      return valueArray.every((role) => roleList.includes(role));
    };

    const clear = () => {
      user.value = null;
    };

    return {
      clear,
      getPermissions,
      getRoles,
      hasPermission,
      hasRole,
      initUser,
      user,
    };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
);
