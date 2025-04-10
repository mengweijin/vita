import { defineStore } from 'pinia'
import { useSessionStorage } from '@vueuse/core'

const { VITE_APP_PREFIX } = import.meta.env

export const useUserStore = defineStore(`${VITE_APP_PREFIX}-user`, () => {
  const user = useSessionStorage(`${VITE_APP_PREFIX}-user-storage`, {})

  const getUser = () => user.value

  const setUser = (userInfo) => {
    user.value = userInfo
  }

  const getToken = () => {
    return getUser()?.token
  }

  return { user, getUser, setUser, getToken }
})
