const { VITE_APP_PREFIX } = import.meta.env;

export const useFilePreviewStore = defineStore(
  `${VITE_APP_PREFIX}-file-preview`,
  () => {
    /** dialog visible */
    const filePreviewDialogVisible = ref(false);

    /**
     * file id
     */
    const filePreviewId = ref(null);

    /**
     * file name
     */
    const filePreviewName = ref(null);

    return { filePreviewDialogVisible, filePreviewId, filePreviewName };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
);
