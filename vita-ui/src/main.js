import { createApp } from "vue";
import App from "./App.vue";
import "./styles/main.css";
// 引入默认的 passive 事件监听器，以提升滚动性能
// 解决告警：[Violation]Added non-passive event listener to a scroll-blocking 'wheel' event. Consider marking event handler as 'passive' to make the page more responsive.
import "default-passive-events";

const app = createApp(App);

import { addCollection, Icon } from "@iconify/vue";
// 预加载全部 ep、ri、ant-design 图标，以实现离线使用 iconify
import antDesignIcons from "@iconify-json/ant-design/icons.json";
import epIcons from "@iconify-json/ep/icons.json";
import riIcons from "@iconify-json/ri/icons.json";
addCollection(epIcons);
addCollection(riIcons);
addCollection(antDesignIcons);
// 全局注册 @iconify/vue 中的 Icon 组件，避免多次导入
app.component("Icon", Icon);

import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

const pinia = createPinia();
// pinia 注册插件
pinia.use(piniaPluginPersistedstate);
app.use(pinia);

// 自定义指令（有些指令依赖 pinia，需要放到 pinia 后面）
import directives from "@/directives/index.js";

// 会自动调用 directives 中的 install 方法
app.use(directives);

// 自定义组件全局注册（有些组件依赖 pinia，需要放到 pinia 后面）
import components from "@/components/index.js";

// 会自动调用 components 中的 install 方法
app.use(components);

import router from "@/router/index.js";
app.use(router);

app.mount("#app");
