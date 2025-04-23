import { camelCase } from 'xe-utils'

// 设备设备类型断点配置
const BREAKPOINTS = {
  mobile: 768, // < 768px
  tablet: 1024, // 768px ~ 1024px
  pc: 1025, // > 1024px
}

/** 获取当前设备类型 */
export const getDeviceType = () => {
  const width = window.innerWidth
  if (width < BREAKPOINTS.mobile) {
    return 'mobile'
  }
  if (width < BREAKPOINTS.tablet) {
    return 'tablet'
  }
  return 'pc'
}

/** 首字母转大写 */
export const upperFirst = (str) => str.charAt(0).toUpperCase() + str.slice(1)

/** kebab-case 转 camelCase 再转 PascalCase 格式 */
export const pascalCase = (str) => upperFirst(camelCase(str))
