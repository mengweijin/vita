import { camelCase, isEmpty } from 'xe-utils'

/** 首字母转大写 */
export const upperFirst = (str) => {
  return str && str.length > 0 ? str.charAt(0).toUpperCase() + str.slice(1) : str
}

/** kebab-case 转 camelCase 再转 PascalCase 格式 */
export const pascalCase = (str) => upperFirst(camelCase(str))

/**
 * 仅复制 target 已定义的属性值，且 source 不为空对象才进行复制。
 * @param {*} target 目标对象，要复制到的对象
 * @param {*} source 原始对象，要被复制的对象
 */
export const copyDefinedProperties = (target, source) => {
  if (!isEmpty(source)) {
    Object.keys(target).forEach((key) => {
      if (source.hasOwnProperty(key)) {
        target[key] = source[key]
      }
    })
  }
}
