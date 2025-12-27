<route lang="yaml">
meta:
  title: IFRAME 页面
</route>

<script setup>
const url = ref("https://aday.fun");

onMounted(() => { });
</script>

<template>
  <iframe :src="url" style="width:100%; height:600px; border:none;"></iframe>
</template>

<style scoped></style>
