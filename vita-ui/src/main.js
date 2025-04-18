import './styles/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

import App from './App.vue'
import { default as router, initDynamicRoutes } from './router/index.js'

const app = createApp(App)

// 预加载全部 ep、ri、mdi 图标，以实现离线使用 iconify
import { addCollection } from '@iconify/vue'
import epIcons from '@iconify-json/ep/icons.json'
import riIcons from '@iconify-json/ri/icons.json'
import antDesignIcons from '@iconify-json/ant-design/icons.json'
addCollection(epIcons)
addCollection(riIcons)
addCollection(antDesignIcons)

// 自定义指令
import * as directives from '@/directives/index.js'
Object.keys(directives).forEach((key) => {
  app.directive(key, directives[key])
})

const pinia = createPinia()
// pinia 注册插件
pinia.use(piniaPluginPersistedstate)

app.use(pinia)

// 刷一次动态路由，以免刷新页面时，页面空白或404。依赖 pinia，所有要放在 pinia 后面。
initDynamicRoutes()
app.use(router)

app.mount('#app')
