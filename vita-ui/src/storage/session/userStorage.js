import { STORAGE_NAME } from "@/storage/storage.js";

let userStorage = {

  name: "user",

  get: function () {
    return layui.sessionData(STORAGE_NAME)[this.name];
  },

  set: function (data) {
    layui.sessionData(STORAGE_NAME, { key: this.name, value: data });
  },

  del: function () {
    layui.sessionData(STORAGE_NAME, { key: this.name, remove: true });
  },

  getToken: function () {
    return this.get()?.token;
  },

};

export { userStorage };
