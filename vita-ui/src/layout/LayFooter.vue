<script setup>
import { ref } from "vue";
const footerYear = ref(new Date().getFullYear());
</script>

<template>
  <span><a href="https://aday.fun" target="_blank">https://aday.fun</a></span>
  <span><a href="https://vita.aday.fun" target="_blank">Vita 管理系统</a></span>
  <span><a href="https://beian.miit.gov.cn" target="_blank">陕ICP备2025064396号</a></span>
  <span>Copyright © 2018 ~ {{ footerYear }}</span>
  <span>Meng Wei Jin</span>
  <span>APACHE LICENSED 2.0</span>
</template>

<style scoped>
span+span {
  margin-left: 10px;
}
</style>
