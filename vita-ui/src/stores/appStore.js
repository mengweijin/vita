import { ref } from 'vue'
import { defineStore } from 'pinia'

const { VITE_APP_PREFIX } = import.meta.env

export const useAppStore = defineStore(
  `${VITE_APP_PREFIX}-app`,
  () => {
    // state 直接解构会丢失响应性，需要通过 storeToRefs 保留响应式
    const sideMenuOpened = ref(true)

    return { sideMenuOpened }
  },
  {
    persist: {
      storage: localStorage, // 默认为 localStorage，也可以指定为 sessionStorage
    },
  },
)
