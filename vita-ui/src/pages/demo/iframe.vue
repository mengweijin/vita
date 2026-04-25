<route lang="yaml">
meta:
  title: IFRAME 页面
  permission: demo:page:view
</route>

<script setup>
const url = ref("https://aday.fun");

onMounted(() => {});
</script>

<template>
  <iframe title="开心每一天" :src="url" style="width: 100%; height: 100%; border: none"></iframe>
</template>

<style scoped></style>
