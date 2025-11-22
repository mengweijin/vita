<script setup>
import { useTabsStore } from '@/store/tabs-store.js'
import TabContextMenu from './backup/tab-context-menu.vue'

const route = useRoute();
const router = useRouter();
const tabsStore = useTabsStore();

// 计算属性
const tabsList = computed(() => tabsStore.tabsList);

const activeTab = computed({
  get: () => tabsStore.activeTab,
  set: (val) => tabsStore.setActiveTab(val)
});

const cachedViews = computed(() => tabsStore.getCachedViews());

// 右键菜单相关
const contextMenuVisible = ref(false);
const contextMenuStyle = ref({ left: '0px', top: '0px' });
const currentContextTab = ref(null);

// 监听路由变化，添加标签页
watch(() => route, (newRoute) => {
  if (newRoute.name) {
    tabsStore.addTab(newRoute);
  }
},
  { immediate: true, deep: true }
)

// 标签页点击事件
const handleTabClick = (tab) => {
  const targetTab = tabsList.value.find(item => item.name === tab.paneName)
  if (targetTab) {
    router.push(targetTab.path)
  }
}

// 标签页关闭事件
const handleTabRemove = (targetName) => {
  const tab = tabsList.value.find(item => item.name === targetName)
  if (tab && !tab.closable) {
    ElMessage.warning('该页签不可关闭')
    return
  }

  tabsStore.removeTab(targetName)

  // 如果关闭的是当前激活的标签页，需要跳转到新的激活标签页
  if (targetName === activeTab.value) {
    const currentTab = tabsList.value.find(tab => tab.name === activeTab.value)
    if (currentTab) {
      router.push(currentTab.path)
    } else {
      // 如果没有其他页签，跳转到首页
      router.push('/')
    }
  }
}

// 右键菜单事件
const handleContextMenu = (event, tab) => {
  event.preventDefault()

  const targetTab = tabsList.value.find(item => item.name === tab.paneName)
  if (!targetTab) return

  currentContextTab.value = targetTab

  // 设置菜单位置
  contextMenuStyle.value = {
    left: event.clientX + 'px',
    top: event.clientY + 'px'
  }

  contextMenuVisible.value = true
}

// 关闭右键菜单
const closeContextMenu = () => {
  contextMenuVisible.value = false
}

// 右键菜单操作
const handleCloseCurrent = () => {
  if (currentContextTab.value) {
    handleTabRemove(currentContextTab.value.name)
  }
  closeContextMenu()
}

const handleCloseOthers = () => {
  if (currentContextTab.value) {
    tabsStore.closeOtherTabs(currentContextTab.value)
    router.push(currentContextTab.value.path)
  }
  closeContextMenu()
}

const handleCloseAll = () => {
  tabsStore.closeAllTabs()
  router.push('/')
  closeContextMenu()
}

const handleCloseLeft = () => {
  if (currentContextTab.value) {
    tabsStore.closeLeftTabs(currentContextTab.value)
    router.push(currentContextTab.value.path)
  }
  closeContextMenu()
}

const handleCloseRight = () => {
  if (currentContextTab.value) {
    tabsStore.closeRightTabs(currentContextTab.value)
    router.push(currentContextTab.value.path)
  }
  closeContextMenu()
}

// 点击页面其他区域关闭右键菜单
document.addEventListener('click', closeContextMenu)
</script>


<template>
  <div class="tabs-view">
    <el-tabs v-model="activeTab" type="card" closable @tab-click="handleTabClick" @tab-remove="handleTabRemove"
      @contextmenu="handleContextMenu">
      <el-tab-pane v-for="tab in tabsList" :key="tab.name" :name="tab.name" :label="tab.title" :closable="tab.closable">
      </el-tab-pane>
    </el-tabs>

    <div class="tabs-content">
      <router-view v-slot="{ Component }">
        <keep-alive :include="cachedViews">
          <component :is="Component" :key="$route.fullPath" />
        </keep-alive>
      </router-view>
    </div>

    <!-- 右键菜单 -->
    <TabContextMenu v-if="contextMenuVisible" :style="contextMenuStyle" :current-tab="currentContextTab"
      @close-current="handleCloseCurrent" @close-others="handleCloseOthers" @close-all="handleCloseAll"
      @close-left="handleCloseLeft" @close-right="handleCloseRight" />
  </div>
</template>


<style scoped>
.tabs-view {
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
}

/* 首页标签页特殊样式 */
:deep(.el-tabs__item[id^="tab-Home"]) .el-icon-close {
  display: none;
}
</style>
