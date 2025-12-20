<route lang="yaml">
meta:
  title: 接口文档
</route>

<script setup>
const { VITE_BASE_API } = import.meta.env;

const documentUrl = `${window.location.origin}${VITE_BASE_API}/doc.html`;
</script>

<template>
  <iframe :src="documentUrl" style="width:100%; height:100%; border:none;"></iframe>
</template>

<style scoped></style>
