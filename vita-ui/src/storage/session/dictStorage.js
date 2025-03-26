import _ from "lodash";
import { STORAGE_NAME } from "@/storage/storage.js";
import { utils } from "@/script/utils.js";

let dictStorage = {
  name: "dict",

  get: function () {
    let data = layui.sessionData(STORAGE_NAME)[this.name];
    if (utils.isEmpty(data)) {
      $.ajax({
        url: "/system/dict-data/list",
        method: "get",
        async: false,
      }).then((list) => {
        let groupedObject = _.groupBy(list, "code");
        this.set(groupedObject);
        data = groupedObject;
      });
    }
    return data;
  },

  set: function (data) {
    layui.sessionData(STORAGE_NAME, { key: this.name, value: data });
  },

  del: function () {
    layui.sessionData(STORAGE_NAME, { key: this.name, remove: true });
  },

};

export { dictStorage };
