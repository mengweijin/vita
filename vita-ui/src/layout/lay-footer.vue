<script setup>
const footerYear = ref(new Date().getFullYear());
</script>

<template>
  <div class="vt-footer">
    <span><a href="https://aday.fun" target="_blank">https://aday.fun</a></span>
    <span
      ><a href="https://vita.aday.fun" target="_blank"
        ><img src="/favicon.png" style="width: 34px" />&nbsp;微塔管理系统</a
      ></span
    >
    <span><a href="https://beian.miit.gov.cn" target="_blank">陕ICP备2025064396号</a></span>
    <span>Copyright © 2018 ~ {{ footerYear }}</span>
    <span>Meng Wei Jin</span>
    <span>APACHE LICENSED 2.0</span>
  </div>
</template>

<style scoped>
.vt-footer {
  display: flex;
  justify-content: center;
  /* 水平居中 */
  align-items: center;
  /* 垂直居中 */
  height: 100%;
}

span + span {
  margin-left: 10px;
}
</style>
