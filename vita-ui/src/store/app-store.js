import utils from "@/utils/utils.js";

const { VITE_APP_PREFIX } = import.meta.env;

export const useAppStore = defineStore(
  `${VITE_APP_PREFIX}-app`,
  () => {
    // state 直接解构会丢失响应性，需要通过 storeToRefs 保留响应式
    const sideMenuOpened = ref(utils.browse().isPC);

    // method 可直接解构
    const toggleSideMenuOpened = () => {
      sideMenuOpened.value = !sideMenuOpened.value;
    };

    return { sideMenuOpened, toggleSideMenuOpened };
  },
  {
    persist: {
      storage: localStorage, // 默认为 localStorage，也可以指定为 sessionStorage
    },
  },
);
