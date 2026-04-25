const { VITE_APP_PREFIX } = import.meta.env;

export const useMessageStore = defineStore(
  `${VITE_APP_PREFIX}-message`,
  () => {
    const notViewedCount = ref(0);

    return { notViewedCount };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
);
