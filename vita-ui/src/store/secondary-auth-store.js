const { VITE_APP_PREFIX } = import.meta.env;

export const useSecondaryAuthStore = defineStore(
  `${VITE_APP_PREFIX}-secondary-auth`,
  () => {
    const secondaryAuthDialogVisible = ref(false);

    return { secondaryAuthDialogVisible };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
);
