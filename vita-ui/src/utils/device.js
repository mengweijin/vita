import debounce from 'lodash/debounce'

// 设备断点配置
const BREAKPOINTS = {
  mobile: 768, // < 768px
  tablet: 1024, // 768px ~ 1024px
  pc: 1025, // > 1024px
}

// 判断当前设备类型
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

// 防抖监听窗口变化
export const listenResize = (callback) => {
  const debouncedFn = debounce(() => callback(getDeviceType()), 200)
  window.addEventListener('resize', debouncedFn)
  return () => window.removeEventListener('resize', debouncedFn)
}
