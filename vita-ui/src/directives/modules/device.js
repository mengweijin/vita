import { getDeviceType } from '@/utils/tools'

export default {
  mounted(el, binding) {
    // 这里使用 modifiers，则使用时应为：v-device.pc 或者多个：v-device.mobile.tablet
    const modifiers = binding.modifiers

    // 初始检查
    const checkDevice = () => {
      const currentDevice = getDeviceType()
      const isMatch = Object.keys(modifiers).some((d) => d === currentDevice)
      el.style.display = isMatch ? '' : 'none' // 匹配则显示，否则隐藏
    }

    // 执行
    checkDevice()
  },
}
