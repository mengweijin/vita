<script setup>
import zhCn from "element-plus/es/locale/lang/zh-cn";
</script>

<template>
  <el-config-provider :locale="zhCn">
    <RouterView />
    <!-- 二级认证弹窗 -->
    <VtDialogSecondaryAuth />
  </el-config-provider>
</template>

<style scoped></style>
