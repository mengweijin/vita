import { pascalCase } from '@/utils/tool'

/** 动态注册组件 */
const components = import.meta.glob('./modules/**/*.vue', { eager: true })

export default {
  // install 方法接收 app 实例
  install(app) {
    Object.entries(components).forEach(([path, module]) => {
      const name = path.match(/([^/]+).vue$/) || null
      if (name && module.default) {
        app.component(pascalCase(name[1]), module.default)
      }
    })
  },
}
