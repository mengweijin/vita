<script setup>
import utils from "@/utils/utils.js";

const route = useRoute();

import { useMenuStore } from "@/store/menu-store";
import MenuTree from "./components/menu-tree.vue";

const menuStore = useMenuStore();
const { menus } = storeToRefs(menuStore);

import { useAppStore } from "@/store/app-store";

const appStore = useAppStore();
const { sideMenuOpened } = storeToRefs(appStore);

const activeMenu = computed(() => {
	const { meta, path } = route;
	return path || "/";
});

const menuTreeList = ref([]);

onMounted(() => {
	// 转为树状
	menuTreeList.value = utils.toArrayTree(menus.value, { sortKey: "seq" });
});
</script>

<template>
  <el-menu :collapse="!sideMenuOpened" :collapse-transition="false" :unique-opened="true" :router="true"
    :default-active="activeMenu" class="vt-menu">
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
  scrollbar-width: none;
}

.el-menu-item>span,
.el-sub-menu__title>span {
  padding-left: 5px;
}
</style>
