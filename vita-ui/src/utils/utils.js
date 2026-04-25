import XEUtils from "xe-utils";

const extendUtils = {
  /**
   * 追加完整路径字段。若使用默认配置，可通过传入 null 值来使用。
   * 比如：let deptList = addFullPath(dataList, {});
   * @param {Array} list
   * @param {Object} param1 { idKey: 'id', parentKey: 'parentId', pathKey: 'name', separator: '/' }
   * @returns
   */
  addFullPath: (
    list,
    { idKey = "id", parentKey = "parentId", pathKey = "name", separator = "/" },
  ) => {
    const buildPath = (id) => {
      const pathList = [];
      let row = XEUtils.find(
        list,
        (item) => XEUtils.toValueString(id) === XEUtils.toValueString(item[idKey]),
      );
      while (row != null) {
        // 往最前面增加元素（向数组最前面插入元素时，需设置起始索引为 0, 且删除数量为 0）
        pathList.splice(0, 0, row[pathKey]);
        row = XEUtils.find(
          list,
          (item) => XEUtils.toValueString(item[idKey]) === XEUtils.toValueString(row[parentKey]),
        );
      }
      return pathList.join(separator);
    };

    list.forEach((item) => {
      item[`${pathKey}FullPath`] = buildPath(item[idKey]);
    });

    return list;
  },

  /**
   * 仅复制 target 已定义的属性值，且 source 不为空对象才进行复制。
   * @param {Object} target 目标对象，要复制到的对象
   * @param {Object} source 原始对象，要被复制的对象
   */
  copyDefinedProperties: (target, source) => {
    if (!XEUtils.isEmpty(source)) {
      Object.keys(target).forEach((key) => {
        if (Object.hasOwn(source, key)) {
          target[key] = source[key];
        }
      });
    }
  },

  /**
   * 写入字符串到指定文件，并触发浏览器下载。
   * @param {string} content 要写入文件的字符串内容
   * @param {string} fileName 下载的文件名，默认为 "download.txt"
   */
  download: (content, fileName = "download.txt") => {
    const blob = new Blob([content], { type: "text/plain;charset=utf-8" });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = fileName;
    a.click();
    setTimeout(() => {
      document.body.removeChild(a); // 移除临时a标签
      URL.revokeObjectURL(url); // 释放内存
    }, 100);
  },

  /**
   * 判断是否为空白值
   * @param {String} val
   * @returns {Boolean}
   */
  isBlank: (val) => {
    return val === undefined || val === null || XEUtils.toString(val)?.trim() === "";
  },

  /**
   * 判断一个字符串是否为 json
   * @param {any} val
   */
  isJSON: (val) => {
    if (XEUtils.isEmpty(val)) {
      return false;
    }

    if (XEUtils.isPlainObject(val)) {
      return true;
    }

    if (!XEUtils.isString(val)) {
      return false;
    }

    const str = val.trim();
    // 检查首尾字符：合法JSON以 { } 或 [ ] 包裹
    const isLikeJSON =
      (str.startsWith("{") && str.endsWith("}")) || (str.startsWith("[") && str.endsWith("]"));
    if (!isLikeJSON) {
      return false;
    }

    try {
      JSON.parse(str);
      return true;
      // 变量命名约定：_（下划线）开头通常表示：
      // 这是一个故意不使用的变量;
      // 只是用来占位，满足语法要求；
      // 向其他开发者表明 "我知道这里有个错误对象，但我不需要用它"
    } catch {
      return false;
    }
  },

  /**
   * 判断是否不为空白值
   * @param {String} val
   * @returns {Boolean}
   */
  isNotBlank: (val) => {
    return !extendUtils.isBlank(val);
  },
  /**
   * 使用指定分隔符拼接字符串
   * @param {string} separator - 分隔符，默认为'/'
   * @param {boolean} ignoreBlank - 拼接时是否忽略空白项
   * @param {...any} parts - 要拼接的部分
   * @returns {string} 拼接后的字符串
   */
  join: (separator = ",", ignoreBlank = false, ...parts) => {
    if (!parts) {
      return "";
    }

    if (ignoreBlank) {
      parts = parts.filter((i) => extendUtils.isNotBlank(i));
    }

    return parts.join(separator);
  },

  /**
   * kebab-case 转 camelCase 再转 PascalCase 格式
   */
  pascalCase: (str) => extendUtils.upperFirst(XEUtils.camelCase(str)),

  /**
   * 异步等待指定毫秒数
   * @param {Number} ms
   * @returns Promise
   */
  sleep: (ms) => {
    return new Promise((resolve) => setTimeout(resolve, ms));
  },

  /**
   * 秒数转 time ago 格式
   * @param {Long} seconds
   * @returns
   */
  timeAgo: (seconds) => {
    const daySec = 24 * 60 * 60;
    const hourSec = 60 * 60;
    const minuteSec = 60;
    const dd = Math.floor(seconds / daySec);
    const hh = Math.floor((seconds % daySec) / hourSec);
    const mm = Math.floor((seconds % hourSec) / minuteSec);
    const ss = seconds % minuteSec;
    if (dd > 0) {
      return `${dd}天${hh}小时${mm}分钟${ss}秒`;
    } else if (hh > 0) {
      return `${hh}小时${mm}分钟${ss}秒`;
    } else if (mm > 0) {
      return `${mm}分钟${ss}秒`;
    } else {
      return `${ss}秒`;
    }
  },
  /**
   * 同时移除开头和结尾指定的字符串
   * @param {string} str 要处理的字符串
   * @param {string} toTrim 要移除的字符串
   * @returns
   */
  trimSpecified: (str, toTrim) => {
    if (!str || !toTrim) {
      return str;
    }

    const pattern = String(toTrim);
    let result = String(str);

    while (result.startsWith(pattern)) {
      result = result.slice(pattern.length);
    }

    while (result.endsWith(pattern)) {
      result = result.slice(0, -pattern.length);
    }

    return result;
  },
  /**
   * 首字母转大写
   */
  upperFirst: (str) => {
    return str && str.length > 0 ? str.charAt(0).toUpperCase() + str.slice(1) : str;
  },
};

// 合并 XEUtils 和自定义 extendUtils 工具
// 注意：XEUtils 和 extendUtils 中的方法名称不要重复，否则后面的 extendUtils 会覆盖前面的 XEUtils 中的方法。
const utils = {
  ...XEUtils,
  ...extendUtils,
};

export default utils;

// 导出类型（仅可在 typescript 下使用）
// export type ExtendUtils = typeof extendUtils
// export type Utils = typeof utils
