<script setup>
import { ref, onMounted } from "vue";
import { Icon } from "@iconify/vue";
import { toTree } from "@/utils/util";

import MenuTree from "./components/MenuTree.vue";

import { storeToRefs } from 'pinia';
import { useAppStore } from '@/stores/appStore.js';
const appStore = useAppStore();
const { sideMenuOpened } = storeToRefs(appStore);

import { useUserStore } from '@/stores/userStore.js'
const userStore = useUserStore();
const { user } = storeToRefs(userStore);

const menuTreeList = ref([]);

const handleOpen = (key, keyPath) => {
  console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
  console.log(key, keyPath)
}

onMounted(() => {
  // 转为树状
  menuTreeList.value = toTree(user.value.menus);
  console.log(menuTreeList.value);
});

</script>

<template>
  <el-menu :collapse="!sideMenuOpened" :collapse-transition="false" :router="true" :default-active="'/home'"
    @open="handleOpen" @close="handleClose" style="transition: width 0.3s;">
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
