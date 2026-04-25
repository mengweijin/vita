<route lang="yaml">
meta:
  title: 403
</route>

<script setup></script>

<template>
  <div class="vt-background"></div>
</template>

<style scoped>
/* 全屏固定容器，无视 body 边距，完美覆盖视口 */
.vt-background {
  /* 背景图片设置（根据实际路径调整） */
  background-image: url("/403.png");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  /* 关键：占满父容器 */
  width: 100%;
  height: 100%;
  /* 如果需要，可以添加最小高度防止内容为空时塌陷 */
  min-height: 100%;
}
</style>
