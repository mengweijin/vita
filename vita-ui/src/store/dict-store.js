import { groupBy } from 'xe-utils'
import { defineStore } from 'pinia'
import { dictDataApi } from '@/api/system/dict-api'

const { VITE_APP_PREFIX } = import.meta.env

export const useDictStore = defineStore(
  `${VITE_APP_PREFIX}-dict`,
  () => {
    const dicts = ref(null)

    const initDicts = async () => {
      const res = await dictDataApi.list()
      dicts.value = groupBy(res, 'code')
    }

    const getDicts = (code) => {
      return code ? dicts.value[code] : dicts.value
    }

    const clear = () => (dicts.value = null)

    return { dicts, initDicts, getDicts, clear }
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
)
