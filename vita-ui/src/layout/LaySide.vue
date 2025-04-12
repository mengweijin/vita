<script setup>
import { ref, onMounted } from "vue";
import { Icon } from "@iconify/vue";
import { getMenuSideList } from "@/api/system/menu";
import { toTree } from "@/utils/util";

import { storeToRefs } from 'pinia';
import { useAppStore } from '@/stores/appStore.js';
const appStore = useAppStore();
const { sideMenuOpened } = storeToRefs(appStore);

const menuList = ref([]);

const handleOpen = (key, keyPath) => {
  console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
  console.log(key, keyPath)
}

onMounted(() => {
  getMenuSideList().then((res) => {
    menuList.value = toTree(res);
    console.log(menuList.value);
  });
});

</script>

<template>
  <el-menu :collapse="!sideMenuOpened" :collapse-transition="true" :default-active="0" @open="handleOpen"
    @close="handleClose">
    <el-menu-item index="0">
      <Icon icon="ri:home-2-fill" width="24" height="24" />
      <span>首页</span>
    </el-menu-item>

    <el-sub-menu index="1">
      <template #title>
        <Icon icon="ri:function-fill" width="24" height="24" />
        <span>系统管理</span>
      </template>
      <el-menu-item index="1-1">部门管理</el-menu-item>
      <el-menu-item index="1-2">用户管理</el-menu-item>
    </el-sub-menu>

    <el-menu-item index="999">
      <Icon icon="ri:github-fill" width="24" height="24" />
      <span>Vita（微塔）Github</span>
    </el-menu-item>

    <el-menu-item index="999">
      <Icon icon="simple-icons:gitee" width="24" height="24" />
      <span>Vita（微塔）Gitee</span>
    </el-menu-item>

  </el-menu>
</template>

<style scoped>
.el-menu-item>span,
.el-sub-menu__title>span {
  padding-left: 5px;
}
</style>
