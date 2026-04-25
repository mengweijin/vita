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
  tabsStore.removeTab(targetName);
};

const contextMenuPopoverRefs = useTemplateRef("popoverRefs");

// 关闭右键菜单
const closeContextMenuPopover = () => {
  contextMenuPopoverRefs.value.forEach((popoverRef) => {
    popoverRef?.hide();
  });
};

// 右键菜单操作
const handleCloseCurrent = (tab) => {
  tabsStore.removeTab(tab.name);
  closeContextMenuPopover();
};

const handleCloseOthers = (tab) => {
  tabsStore.removeOtherTabs(tab.name);
  closeContextMenuPopover();
};

const handleCloseAll = () => {
  tabsStore.removeAllTabs();
  router.push("/");
  closeContextMenuPopover();
};

const handleCloseLeft = (tab) => {
  tabsStore.removeLeftTabs(tab.name);
  closeContextMenuPopover();
};

const handleCloseRight = (tab) => {
  tabsStore.removeRightTabs(tab.name);
  closeContextMenuPopover();
};
</script>

<template>
  <div class="vt-tabs">
    <!-- 标签页区域 -->
    <el-tabs v-model="activeTab" closable @tab-click="handleTabClick" @tab-remove="handleTabRemove">
      <el-tab-pane
        v-for="(tab, index) in tabsList"
        :key="tab.name"
        :name="tab.name"
        :label="tab.title"
        :closable="tab.closable"
      >
        <!-- 核心：使用label插槽自定义标题区域 -->
        <template #label>
          <el-popover
            v-if="route.name === tab.name"
            ref="popoverRefs"
            :width="100"
            :popper-style="{ minWidth: '100px' }"
            trigger="contextmenu"
            placement="bottom"
          >
            <template #reference>
              {{ tab.title }}
            </template>
            <template #default>
              <div class="vt-right-context-menu">
                <el-menu mode="vertical" style="border-right: 0px">
                  <el-menu-item
                    index="1"
                    @click="handleCloseCurrent(tab)"
                    v-if="tab.closable === true"
                  >
                    <span>关闭当前</span>
                  </el-menu-item>
                  <el-menu-item index="2" @click="handleCloseOthers(tab)">
                    <span>关闭其他</span>
                  </el-menu-item>
                  <el-menu-item index="3" @click="handleCloseAll()">
                    <span>关闭所有</span>
                  </el-menu-item>
                  <el-menu-item index="4" @click="handleCloseLeft(tab)">
                    <span>关闭左侧</span>
                  </el-menu-item>
                  <el-menu-item index="5" @click="handleCloseRight(tab)">
                    <span>关闭右侧</span>
                  </el-menu-item>
                </el-menu>
              </div>
            </template>
          </el-popover>
          <span v-else>{{ tab.title }}</span>
        </template>
      </el-tab-pane>
    </el-tabs>
    <!-- 内容区域 -->
    <div class="vt-tab-content">
      <router-view v-slot="{ Component }">
        <keep-alive :include="tabsStore.getCachedViews()">
          <component :is="Component" :key="$route.fullPath" />
        </keep-alive>
      </router-view>
    </div>
  </div>
</template>

<style scoped>
.vt-tabs {
  overflow: hidden;
}

/* 明确选择第一个 el-tabs */
.vt-tabs > :deep(.el-tabs:nth-child(1)) .el-tabs__nav-scroll:first-child {
  padding-left: 20px;
}

.vt-tab-content {
  /* 减去标签页高度 */
  height: calc(var(--vt-tab-content-height));
  padding: 0px 15px 0px 15px;
}

.vt-right-context-menu .el-menu .el-menu-item {
  height: 30px;
  line-height: 30px;
  padding: 0px 0px 0px 8px;
}
</style>
