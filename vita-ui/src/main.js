import './styles/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

import App from './App.vue'
import router from './router'

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
app.use(router)

app.mount('#app')
