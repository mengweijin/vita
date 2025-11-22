<!-- layouts/MainLayout.vue -->
<template>
  <div class="main-layout">
    <!-- 顶部导航 -->
    <div class="header">
      <div class="header-left">
        <h2>后台管理系统</h2>
      </div>
      <div class="header-right">
        <!-- 用户信息等 -->
      </div>
    </div>

    <div class="main-container">
      <!-- 侧边栏菜单 -->
      <div class="sidebar">
        <el-menu :default-active="activeMenu" router unique-opened>
          <el-menu-item index="/">
            <el-icon>
              <House />
            </el-icon>
            <span>首页</span>
          </el-menu-item>
          <el-sub-menu index="1">
            <template #title>
              <el-icon>
                <User />
              </el-icon>
              <span>用户管理</span>
            </template>
            <el-menu-item index="/user">用户列表</el-menu-item>
            <el-menu-item index="/user/add">添加用户</el-menu-item>
          </el-sub-menu>
          <el-menu-item index="/role">
            <el-icon>
              <Setting />
            </el-icon>
            <span>角色管理</span>
          </el-menu-item>
          <el-menu-item index="/menu">
            <el-icon>
              <Menu />
            </el-icon>
            <span>菜单管理</span>
          </el-menu-item>
        </el-menu>
      </div>

      <!-- 主内容区域 -->
      <div class="main-content">
        <TabsView />
      </div>
    </div>
  </div>
</template>

<script setup>
import { House, User, Setting, Menu } from '@element-plus/icons-vue'
import TabsView from '@/components/TabsView.vue'

const route = useRoute()

// 当前激活的菜单
const activeMenu = computed(() => route.path)
</script>

<style scoped>
.main-layout {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  height: 60px;
  background-color: #409eff;
  color: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.header h2 {
  margin: 0;
  color: white;
}

.main-container {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.sidebar {
  width: 200px;
  background-color: #304156;
}

:deep(.sidebar .el-menu) {
  border-right: none;
  background-color: #304156;
}

:deep(.sidebar .el-menu .el-menu-item),
:deep(.sidebar .el-menu .el-sub-menu__title) {
  color: #bfcbd9;
}

:deep(.sidebar .el-menu .el-menu-item:hover),
:deep(.sidebar .el-menu .el-sub-menu__title:hover) {
  background-color: #263445;
}

:deep(.sidebar .el-menu .el-menu-item.is-active) {
  background-color: #409eff;
  color: white;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
</style>
