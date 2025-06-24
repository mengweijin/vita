const { VITE_APP_PREFIX } = import.meta.env;
import { menuApi } from '@/api/system/menu-api';

export const useMenuStore = defineStore(
  `${VITE_APP_PREFIX}-menu`,
  () => {
    const menus = ref([]);

    const refresh = async () => {
      menus.value = await menuApi.listSideMenus();
    };

    const get = () => menus.value;

    const clear = () => (menus.value = []);

    return { menus, refresh, get, clear };
  },
  {
    persist: {
      storage: localStorage,
    },
  },
);
