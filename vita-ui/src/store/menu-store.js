const { VITE_APP_PREFIX } = import.meta.env;
import { menuApi } from '@/api/system/menu-api';

export const useMenuStore = defineStore(
  `${VITE_APP_PREFIX}-menu`,
  () => {
    const menus = ref([]);

    const initMenus = async () => {
      menus.value = await menuApi.listSideMenus();
    };

    const getMenus = () => menus.value;

    const clear = () => (menus.value = []);

    return { menus, initMenus, getMenus, clear };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
);
