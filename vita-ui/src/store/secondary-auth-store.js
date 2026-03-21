const { VITE_APP_PREFIX } = import.meta.env;

export const useSecondaryAuthStore = defineStore(
  `${VITE_APP_PREFIX}-secondary-auth`,
  () => {
    const dialogSecondaryAuthVisible = ref(false);

    return { dialogSecondaryAuthVisible };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
);
