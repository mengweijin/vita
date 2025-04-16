import './styles/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

import App from './App.vue'
import {default as router, initDynamicRoutes } from './router/index.js'

const app = createApp(App)

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
initDynamicRoutes();
app.use(router)

app.mount('#app')
