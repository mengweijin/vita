const STORAGE_NAME = import.meta.env.VITE_STORAGE_PREFIX + "user";

let userStorage = {

  name: "userInfo",

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
