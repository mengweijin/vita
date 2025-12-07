<script setup>
import { useTabsStore } from "@/store/tabs-store.js";

const route = useRoute();
const router = useRouter();
const tabsStore = useTabsStore();
const { tabsList, activeTab } = storeToRefs(tabsStore);

// 监听路由变化，添加标签页
watch(
	() => route,
	(newRoute) => {
		if (newRoute.name) {
			tabsStore.addTab(newRoute);
		}
	},
	{ deep: true, immediate: true },
);

// 标签页点击事件
const handleTabClick = (tab) => {
	const targetTab = tabsList.value.find((item) => item.name === tab.paneName);
	if (targetTab) {
		router.push(targetTab.path);
	}
};

// 标签页关闭事件
const handleTabRemove = (targetName) => {
	// 先判断是否关闭的是当前激活的标签页（顺序很重要）
	const isRemoveCurrent = targetName === activeTab.value;

	// 再移除标签页，并切换到新的活动页签
	tabsStore.removeTab(targetName);

	// 如果关闭的是当前激活的标签页，需要跳转到新的激活标签页
	if (isRemoveCurrent) {
		const currentTab = tabsList.value.find((tab) => tab.name === activeTab.value);
		if (currentTab) {
			router.push(currentTab.path);
		}
	}
};

// 右键菜单事件
const handleContextMenu = (event, tab) => {
	const targetTab = tabsList.value.find((item) => item.name === tab.name);
};

// 关闭右键菜单
const closeContextMenu = () => {
	contextMenuVisible.value = false;
};

// 右键菜单操作
const handleCloseCurrent = () => {
	if (currentContextTab.value) {
		handleTabRemove(currentContextTab.value.name);
	}
	closeContextMenu();
};

const handleCloseOthers = () => {
	if (currentContextTab.value) {
		tabsStore.closeOtherTabs(currentContextTab.value);
		router.push(currentContextTab.value.path);
	}
	closeContextMenu();
};

const handleCloseAll = () => {
	tabsStore.closeAllTabs();
	router.push("/");
	closeContextMenu();
};

const handleCloseLeft = () => {
	if (currentContextTab.value) {
		tabsStore.closeLeftTabs(currentContextTab.value);
		router.push(currentContextTab.value.path);
	}
	closeContextMenu();
};

const handleCloseRight = () => {
	if (currentContextTab.value) {
		tabsStore.closeRightTabs(currentContextTab.value);
		router.push(currentContextTab.value.path);
	}
	closeContextMenu();
};
</script>


<template>
  <div class="tabs-view">
    <el-tabs v-model="activeTab" closable @tab-click="handleTabClick" @tab-remove="handleTabRemove">
      <el-tab-pane v-for="tab in tabsList" :key="tab.name" :name="tab.name" :label="tab.title" :closable="tab.closable">
      <!-- 核心：使用label插槽自定义标题区域 -->
      <template #label>
        <span @contextmenu.prevent="handleContextMenu($event, tab)">
          {{ tab.title }}
        </span>
      </template>
		</el-tab-pane>
    </el-tabs>

	<el-scrollbar class="vt-tab-content">	
      <router-view v-slot="{ Component }">
        <keep-alive :include="tabsStore.getCachedViews()">
          <component :is="Component" :key="$route.fullPath" />
        </keep-alive>
      </router-view>
	</el-scrollbar>
  </div>
</template>


<style scoped>
.tabs-view {
  height: 100%;
  display: flex;
  flex-direction: column;
  position: relative;
}

:deep(.el-tabs__nav-scroll) {
  padding-left: 20px;
}

/* 隐藏水平滚动条 */
:deep(.el-scrollbar__bar.is-horizontal) {
  display: none !important;
}

.vt-tab-content {
	padding: 0px 20px;
}
/* .tabs-view {
  height: 100%;
  display: flex;
  flex-direction: column;
  position: relative;
}

.tabs-content {
  flex: 1;
  padding: 16px;
  overflow: auto;
  background: #f0f2f5;
}

:deep(.el-tabs__header) {
  margin: 0;
  background: #fff;
  border-bottom: 1px solid #e8e8e8;
}

:deep(.el-tabs__content) {
  display: none;
} */
</style>
