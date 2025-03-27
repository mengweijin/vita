import _ from "lodash";
import { STORAGE_NAME } from "@/storage/storage.js";
import { utils } from "@/script/utils.js";
import { dictDataApi } from "@/api/dictData.js";

let dictStorage = {
  name: "dict",

  init: async () => {
    let list = null;
    await dictDataApi.list().then((res) => {
      list = res;
    });
    let groupedObject = _.groupBy(list, "code");
    this.set(groupedObject);
    return groupedObject;
  },

  get: () => {
    let data = layui.sessionData(STORAGE_NAME)[this.name];
    if (utils.isEmpty(data)) {
      return this.init();
    }
    return data;
  },

  getByCode: (code) => {
    return this.get()[code];
  },

  set: (data) => {
    layui.sessionData(STORAGE_NAME, { key: this.name, value: data });
  },

  del: () => {
    layui.sessionData(STORAGE_NAME, { key: this.name, remove: true });
  },
};

export { dictStorage };
