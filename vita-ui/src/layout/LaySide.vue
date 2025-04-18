<script setup>
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

const getDynamicIcon = (name) => {
  let [collection, icon] = name.split(':');
  return defineComponent({
    render: () => h(resolveComponent(`icon-${collection}-${icon}`))
  })
}

onMounted(() => {
  // 转为树状
  menuTreeList.value = toArrayTree(user.value.menus, { sortKey: 'seq' });
});

</script>

<template>
  <el-menu :collapse="!sideMenuOpened" :collapse-transition="false" :router="true" :default-active="activeMenu"
    class="vt-menu">
    <el-menu-item index="/home">
      <el-icon :size="24">
        <!-- <icon-mdi-home /> -->
        <iconify-icon icon="mdi:home"></iconify-icon>
      </el-icon>
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
