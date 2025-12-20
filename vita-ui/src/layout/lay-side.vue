<script setup>
import { menuApi } from "@/api/system/menu-api.js";
import utils from "@/utils/utils.js";

const route = useRoute();

import { useAppStore } from "@/store/app-store.js";
import MenuTree from "./components/menu-tree.vue";

const appStore = useAppStore();
const { sideMenuOpened } = storeToRefs(appStore);

const activeMenu = computed(() => {
	const { meta, path } = route;
	return path || "/";
});

const menuTreeList = ref([]);

onMounted(async () => {
	const menuList = await menuApi.listSideMenus();
	// 转为树状
	menuTreeList.value = utils.toArrayTree(menuList, { sortKey: "seq" });
});
</script>

<template>
  <el-menu :collapse="!sideMenuOpened" :collapse-transition="false" :unique-opened="true" :router="true"
    :default-active="activeMenu" class="vt-menu">
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
