<script setup>
import { Icon } from "@iconify/vue";
import { toArrayTree } from 'xe-utils';

import { useRoute } from 'vue-router'
const route = useRoute()

import MenuTree from "./components/MenuTree.vue";

import { storeToRefs } from 'pinia';
import { useAppStore } from '@/stores/appStore.js';
const appStore = useAppStore();
const { sideMenuOpened } = storeToRefs(appStore);

import { useUserStore } from '@/stores/userStore.js'
const userStore = useUserStore();
const { user } = storeToRefs(userStore);

const activeMenu = computed(() => {
  const { meta, path } = route
  return path || '/'
});

const menuTreeList = ref([]);

onMounted(() => {
  // 转为树状
  menuTreeList.value = toArrayTree(user.value.menus, { sortKey: 'seq' });
});

</script>

<template>
  <el-menu :collapse="!sideMenuOpened" :collapse-transition="false" :router="true" :default-active="activeMenu"
    class="vt-menu">
    <el-menu-item index="/home">
      <Icon icon="ant-design:home-filled" width="24" height="24" />
      <span>首页</span>
    </el-menu-item>

    <MenuTree :menu-list="menuTreeList" />
  </el-menu>
</template>

<style scoped>
.vt-menu {
  transition: width 0.3s;
  height: calc(100vh - var(--vt-header-height));
  overflow-y: auto;
}

.el-menu-item>span,
.el-sub-menu__title>span {
  padding-left: 5px;
}
</style>
