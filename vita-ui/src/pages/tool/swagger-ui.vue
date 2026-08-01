<route lang="yaml">
meta:
  title: 接口文档
  permission: tools:swagger-ui:view
</route>

<script setup>
// https://swagger.io/docs/open-source-tools/swagger-ui/usage/installation/
import { SwaggerUIBundle } from "swagger-ui-dist";
import "swagger-ui-dist/swagger-ui.css";
import utils from "@/utils/utils.js";
import { useLoginStore } from "@/store/login-store.js";
const loginStore = useLoginStore();

const { VITE_BASE_API } = import.meta.env;

const url = `${utils.trimSpecified(VITE_BASE_API, "/")}/v3/api-docs`;

onMounted(() => {
  SwaggerUIBundle({
    dom_id: "#swagger-ui",
    presets: [SwaggerUIBundle.presets.apis, SwaggerUIBundle.SwaggerUIStandalonePreset],
    url: url,
    // 请求拦截器：为每个请求添加 Token
    requestInterceptor: (request) => {
      // 将 token 以 Bearer scheme 的形式添加到请求头
      request.headers["Authorization"] = loginStore.getBearerToken();
      // 务必返回修改后的 request 对象
      return request;
    },
  });
});
</script>

<template>
  <el-scrollbar class="vt-swagger-ui">
    <div class="swagger" id="swagger-ui"></div>
  </el-scrollbar>
</template>

<style scoped>
.vt-swagger-ui {
  height: calc(var(--vt-tab-content-height));
}
</style>
