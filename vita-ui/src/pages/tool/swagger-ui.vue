<route lang="yaml">
meta:
  title: 接口文档
  permission: tools:swagger-ui:view
</route>

<script setup>
// https://swagger.io/docs/open-source-tools/swagger-ui/usage/installation/
import { SwaggerUIBundle } from "swagger-ui-dist";
import "swagger-ui-dist/swagger-ui.css";

const { VITE_BASE_API } = import.meta.env;

let url = VITE_BASE_API === "/" ? "/v3/api-docs" : `${VITE_BASE_API}/v3/api-docs`;

onMounted(() => {
  SwaggerUIBundle({
    dom_id: "#swagger-ui",
    presets: [SwaggerUIBundle.presets.apis, SwaggerUIBundle.SwaggerUIStandalonePreset],
    url: url,
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
