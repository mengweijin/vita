import { camelCase } from 'xe-utils'

/** 首字母转大写 */
export const upperFirst = (str) => {
  return str && str.length > 0 ? str.charAt(0).toUpperCase() + str.slice(1) : str
}

/** kebab-case 转 camelCase 再转 PascalCase 格式 */
export const pascalCase = (str) => upperFirst(camelCase(str))
