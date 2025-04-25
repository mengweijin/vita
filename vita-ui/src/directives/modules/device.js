import { browse } from 'xe-utils'

export default {
  mounted(el, binding) {
    // 这里使用 modifiers，则使用时应为：v-device.pc 或者多个：v-device.mobile.pc
    const modifiers = binding.modifiers

    // 初始检查
    const checkDevice = () => {
      const currentDevice = browse().isMobile ? 'mobile' : 'pc'
      const isMatch = Object.keys(modifiers).some((d) => d === currentDevice)
      // 匹配则显示，否则隐藏
      el.style.display = isMatch ? '' : 'none'
    }

    // 执行
    checkDevice()
  },
}
