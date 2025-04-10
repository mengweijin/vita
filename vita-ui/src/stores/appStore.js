import { defineStore } from 'pinia'
import { useSessionStorage } from '@vueuse/core'

const { VITE_APP_PREFIX } = import.meta.env

export const useAppStore = defineStore(`${VITE_APP_PREFIX}-app`, () => {
  // state 直接解构会丢失响应性，需要通过 storeToRefs 保留响应式
  const sideMenuOpened = useSessionStorage(`${VITE_APP_PREFIX}-app-storage-side-menu-opened`, true)

  // action 可直接解构，但不能用 storeToRefs，会报错
  const toggleSideMenuOpened = () => {
    sideMenuOpened.value = !sideMenuOpened.value
  }

  return { sideMenuOpened, toggleSideMenuOpened }
})
