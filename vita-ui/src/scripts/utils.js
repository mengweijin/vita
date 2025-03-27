let utils = {
  isString: function (obj) {
    return typeof obj === "string" || obj instanceof String;
  },

  /** 判断是否为空，并特殊处理 "undefined" 和 "null" 也作为空处理 */
  isBlank: function (obj) {
    if (!obj) {
      // 1. 替换所有空白字符（比如，回车换行符等）; 2. trim() 去前后空格
      let str = obj.toString().replace(/\s/g, "").trim();
      return str == "" || str == "undefined" || str == "null";
    }
    return true;
  },

  isEmpty: function (obj) {
    if (!obj) {
      return true;
    }
    if (this.isString(obj)) {
      return this.isBlank(obj);
    }
    return Object.getOwnPropertyNames(obj).length === 0;
  },

  isValidCssSelector: function (selector) {
    try {
      document.querySelector(selector);
      return true;
    } catch (e) {
      return false;
    }
  },

  blankToDefault: function (val, defaultVal) {
    return this.isBlank(val) ? defaultVal : val;
  },

  /** 去前缀 */
  removePrefix: function (str, prefix) {
    if(str.startsWith(prefix)) {
      return str.slice(prefix.length);
    }
    return str;
  },

  /** 去后缀 */
  removeSuffix: function (str, suffix) {
    if(str.endsWith(suffix)) {
      return str.slice(0,  -suffix.length);
    }
    return str;
  },

  getCssVar: function(varName) {
    let root = document.documentElement; 
    return getComputedStyle(root).getPropertyValue(varName);
  }
};

export { utils };
