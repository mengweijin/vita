const { VITE_APP_PREFIX } = import.meta.env;
import { menuApi } from '@/api/system/menu-api.js';

export const useMenuStore = defineStore(
  `${VITE_APP_PREFIX}-menu`,
  () => {
    const menus = ref([]);

    const refresh = async () => {
      menus.value = await menuApi.listSideMenus();
    };

    const get = () => menus.value;

    const clear = () => {
      menus.value = [];
      dynamicRoutesAdded.value = false;
    };

    // 是否已添加动态路由
    const dynamicRoutesAdded = ref(false);

    const setDynamicRoutesAdded = (value) => {
      dynamicRoutesAdded.value = value;
    };

    const isDynamicRoutesAdded = () => dynamicRoutesAdded.value;

    return { menus, refresh, get, clear, dynamicRoutesAdded, setDynamicRoutesAdded, isDynamicRoutesAdded };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
);
