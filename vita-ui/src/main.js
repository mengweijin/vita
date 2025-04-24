import './styles/main.css'
import App from './App.vue'
import { createApp } from 'vue'

const app = createApp(App)

// 预加载全部 ep、ri、ant-design 图标，以实现离线使用 iconify
import { addCollection, Icon } from '@iconify/vue'
import epIcons from '@iconify-json/ep/icons.json'
import riIcons from '@iconify-json/ri/icons.json'
import antDesignIcons from '@iconify-json/ant-design/icons.json'
addCollection(epIcons)
addCollection(riIcons)
addCollection(antDesignIcons)

// 全局注册 @iconify/vue 中的 Icon 组件，避免多次导入
app.component('Icon', Icon)

import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
const pinia = createPinia()
// pinia 注册插件
pinia.use(piniaPluginPersistedstate)
app.use(pinia)

// 自定义指令（有些指令依赖 pinia，需要放到 pinia 后面）
import directives from '@/directives/index'
// 会自动调用 directives 中的 install 方法
app.use(directives)

// 自定义组件全局注册（有些组件依赖 pinia，需要放到 pinia 后面）
import components from '@/components/index'
// 会自动调用 components 中的 install 方法
app.use(components)

import { default as router, initDynamicRoutes } from './router/index'
// 刷一次动态路由，以免刷新页面时，页面空白或404。依赖 pinia，所有要放在 pinia 后面。
initDynamicRoutes()
app.use(router)

app.mount('#app')
