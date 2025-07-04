import { groupBy, orderBy } from 'xe-utils';
import { dictDataApi } from '@/api/system/dict-api';

const { VITE_APP_PREFIX } = import.meta.env;

export const useDictStore = defineStore(
  `${VITE_APP_PREFIX}-dict`,
  () => {
    const dicts = ref(null);

    const refresh = async () => {
      const res = await dictDataApi.list();
      dicts.value = groupBy(res, 'code');
    };

    const get = (code) => {
      let list = code ? dicts.value[code] : dicts.value;
      return orderBy(list, ['seq', 'asc']);
    };

    const clear = () => (dicts.value = null);

    return { dicts, refresh, get, clear };
  },
  {
    persist: {
      storage: localStorage,
    },
  },
);
