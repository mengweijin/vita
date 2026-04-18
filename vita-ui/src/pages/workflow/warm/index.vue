<route lang="yaml">
meta:
  title: warmflow 原生页面
  permission: workflow:menu:view
</route>

<script setup>
import { useLoginStore } from '@/store/login-store.js';
import utils from '@/utils/utils.js';

const loginStore = useLoginStore();

const { VITE_BASE_API } = import.meta.env;

const id = ref("");
const onlyDesignShow = ref(false);
const disabled = ref(false);

const url = ref(`/warm-flow-ui/index.html?id=${id.value}&onlyDesignShow=${onlyDesignShow.value}&disabled=${disabled.value}&Authorization=${loginStore.getBearerToken()}`);
if (VITE_BASE_API !== "/" && utils.isNotBlank(VITE_BASE_API)) {
  url.value = `${VITE_BASE_API}${url.value}`;
}

onMounted(() => { });
</script>

<template>
  <iframe title="warmflow 原生页面" :src="url" style="width:100%; height:100%; border:none;"></iframe>
</template>

<style scoped></style>
