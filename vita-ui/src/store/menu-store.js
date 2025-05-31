const { VITE_APP_PREFIX } = import.meta.env;

export const useMenuStore = defineStore(
  `${VITE_APP_PREFIX}-menu`,
  () => {
    const menus = ref([]);

    const initMenus = (data) => {
      menus.value = data;
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
