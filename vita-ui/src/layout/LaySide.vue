<script setup>
import { ref, computed, onMounted } from "vue";
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
    style="transition: width 0.3s;">
    <el-menu-item index="/home">
      <Icon icon="ri:home-2-fill" width="24" height="24" />
      <span>首页</span>
    </el-menu-item>

    <MenuTree :menu-list="menuTreeList" />
  </el-menu>
</template>

<style scoped>
.el-menu-item>span,
.el-sub-menu__title>span {
  padding-left: 5px;
}
</style>
