import { camelCase, isEmpty, find, toValueString, isPlainObject, isString } from 'xe-utils';

/**
 * 首字母转大写
 */
export const upperFirst = (str) => {
  return str && str.length > 0 ? str.charAt(0).toUpperCase() + str.slice(1) : str;
};

/**
 * kebab-case 转 camelCase 再转 PascalCase 格式
 */
export const pascalCase = (str) => upperFirst(camelCase(str));

/**
 * 判断一个字符串是否为 json
 * @param {any} val
 */
export const isJSON = (val) => {
  if (isEmpty(val)) {
    return false;
  }

  if (isPlainObject(val)) {
    return true;
  }

  if (!isString(val)) {
    return false;
  }

  let str = val.trim();
  // 检查首尾字符：合法JSON以 { } 或 [ ] 包裹
  const isLikeJSON = (str.startsWith('{') && str.endsWith('}')) || (str.startsWith('[') && str.endsWith(']'));
  if (!isLikeJSON) {
    return false;
  }

  try {
    JSON.parse(str);
    return true;
  } catch (e) {
    return false;
  }
};

/**
 * 仅复制 target 已定义的属性值，且 source 不为空对象才进行复制。
 * @param {Object} target 目标对象，要复制到的对象
 * @param {Object} source 原始对象，要被复制的对象
 */
export const copyDefinedProperties = (target, source) => {
  if (!isEmpty(source)) {
    Object.keys(target).forEach((key) => {
      if (source.hasOwnProperty(key)) {
        target[key] = source[key];
      }
    });
  }
};

/**
 * 追加完整路径字段
 * @param {Array} list
 * @param {Object} param1 { idKey: 'id', parentKey: 'parentId', pathKey: 'name', separator: '/' }
 * @returns
 */
export const addFullPath = (list, { idKey = 'id', parentKey = 'parentId', pathKey = 'name', separator = '/' }) => {
  const buildPath = (id) => {
    let pathList = [];
    let row = find(list, (item) => toValueString(id) === toValueString(item[idKey]));
    while (row != null) {
      // 往最前面增加元素（向数组最前面插入元素时，需设置起始索引为 0, 且删除数量为 0）
      pathList.splice(0, 0, row[pathKey]);
      row = find(list, (item) => toValueString(item[idKey]) === toValueString(row[parentKey]));
    }
    return pathList.join(separator);
  };

  list.forEach((item) => {
    item[`${pathKey}FullPath`] = buildPath(item[idKey]);
  });

  return list;
};

/**
 * 秒数转 time ago 格式
 * @param {Long} seconds
 * @returns
 */
export const timeAgo = (seconds) => {
  let daySec = 24 * 60 * 60;
  let hourSec = 60 * 60;
  let minuteSec = 60;
  let dd = Math.floor(seconds / daySec);
  let hh = Math.floor((seconds % daySec) / hourSec);
  let mm = Math.floor((seconds % hourSec) / minuteSec);
  let ss = seconds % minuteSec;
  if (dd > 0) {
    return dd + '天' + hh + '小时' + mm + '分钟' + ss + '秒';
  } else if (hh > 0) {
    return hh + '小时' + mm + '分钟' + ss + '秒';
  } else if (mm > 0) {
    return mm + '分钟' + ss + '秒';
  } else {
    return ss + '秒';
  }
};
