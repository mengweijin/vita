const { VITE_APP_PREFIX } = import.meta.env

export const useUserStore = defineStore(
  `${VITE_APP_PREFIX}-user`,
  () => {
    const user = ref(null)

    const initUser = (data) => {
      user.value = data
    }

    const getToken = () => user.value?.token

    const getMenus = () => user.value?.menus

    const getRoles = () => user.value?.roles

    const getPermissions = () => user.value?.permissions

    const isLogin = () => (user.value?.token ? true : false)

    const clear = () => (user.value = null)

    return { user, initUser, getToken, getMenus, getRoles, getPermissions, isLogin, clear }
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
)
