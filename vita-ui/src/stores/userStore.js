import { ref } from 'vue'
import { defineStore } from 'pinia'

const { VITE_APP_PREFIX } = import.meta.env

export const useUserStore = defineStore(
  `${VITE_APP_PREFIX}-user`,
  () => {
    const user = ref(null)

    const isLogin = () => (user?.value?.token ? true : false)

    return { user, isLogin }
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
)
