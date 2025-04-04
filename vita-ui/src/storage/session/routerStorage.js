const STORAGE_NAME = import.meta.env.VITE_STORAGE_PREFIX + "router";

let routerStorage = {

  get: function (name) {
    return layui.sessionData(STORAGE_NAME)[name];
  },

  set: function (name, data) {
    layui.sessionData(STORAGE_NAME, { key: name, value: data });
  },

  del: function (name) {
    layui.sessionData(STORAGE_NAME, { key: name, remove: true });
  },

};

export { routerStorage };
