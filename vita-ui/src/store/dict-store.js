import { dictDataApi } from "@/api/system/dict-api.js";
import utils from "@/utils/utils.js";

const { VITE_APP_PREFIX } = import.meta.env;

export const useDictStore = defineStore(
  `${VITE_APP_PREFIX}-dict`,
  () => {
    const dicts = ref(null);

    const refresh = async () => {
      const res = await dictDataApi.list();
      dicts.value = utils.groupBy(res, "code");
    };

    const get = (code) => {
      const list = code ? dicts.value[code] : dicts.value;
      return utils.orderBy(list, ["seq", "asc"]);
    };

    const clear = () => {
      dicts.value = null;
    };

    return { clear, dicts, get, refresh };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
);
