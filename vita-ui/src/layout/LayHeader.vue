<script setup>
import { useFullscreen } from '@vueuse/core';
import router from '@/router/index.js'
import { logout } from '@/api/login';

import { storeToRefs } from 'pinia';

import { useUserStore } from '@/stores/userStore.js'
const userStore = useUserStore();
const { user } = storeToRefs(userStore);

import { useAppStore } from '@/stores/appStore.js';
const appStore = useAppStore();
const { sideMenuOpened } = storeToRefs(appStore);

const toggleSideMenuOpened = () => sideMenuOpened.value = !sideMenuOpened.value;

// 强制刷新（适合更新静态资源）
const refresh = () => { top.location.reload(true); };

const onLogout = () => {
  logout().finally(() => {
    // 清理 store
    user.value = null;
    // 跳转登录页
    router.push('/login');
  });
};

// 绑定目标元素（不传则默认全屏整个页面）
const target = ref(null);
const { isFullscreen, toggle: toggleFullscreen } = useFullscreen(target);

</script>

<template>
  <el-menu mode="horizontal" :ellipsis="false">
    <el-menu-item index="0" style="width: 200px;">
      <img src="/logo.svg" alt="logo" />
    </el-menu-item>
    <el-menu-item index="1" @click="toggleSideMenuOpened()">
      <el-icon :size="24" v-if="sideMenuOpened"><icon-ep-fold /></el-icon>
      <el-icon :size="24" v-else><icon-ep-expand /></el-icon>
    </el-menu-item>
    <el-menu-item index="3" @click="refresh()" v-device.pc>
      <el-icon :size="24"><icon-ep-refresh /></el-icon>
    </el-menu-item>

    <el-menu-item index="5" @click="toggleFullscreen()" v-device.tablet.pc>
      <el-icon :size="24" v-if="isFullscreen"><icon-ri-fullscreen-exit-fill /></el-icon>
      <el-icon :size="24" v-else><icon-ri-fullscreen-fill /></el-icon>
    </el-menu-item>
    <el-menu-item index="6">
      <el-icon>
        <el-badge :value="100" :max="99">
          <el-icon :size="24"><icon-ep-bell-filled /></el-icon>
        </el-badge>
      </el-icon>
    </el-menu-item>

    <el-sub-menu index="8">
      <template #title>
        <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
        admin
      </template>
      <el-menu-item index="8-1">
        <el-icon :size="16"><icon-ri-home-9-fill /></el-icon>
        <span>个人主页</span>
      </el-menu-item>
      <el-menu-item index="8-2">
        <el-icon :size="16"><icon-ri-user-settings-line /></el-icon>
        <span>偏好设置</span>
      </el-menu-item>
      <el-menu-item index="8-3">
        <el-icon :size="16"><icon-ri-layout-3-fill /></el-icon>
        <span>布局设置</span>
      </el-menu-item>
      <el-divider />
      <el-menu-item index="8-99" @click="onLogout()">
        <el-icon :size="16"><icon-ri-logout-box-line /></el-icon>
        <span>退出</span>
      </el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<style scoped>
/* 左右布局显示 */
.el-menu--horizontal>.el-menu-item:nth-child(2) {
  margin-right: auto;
}

/* header 下拉列表中分隔线 */
.el-menu--horizontal .el-divider--horizontal {
  margin: 5px 0;
}

.el-menu-item>span {
  padding: 0px 5px;
}

/* 去掉普通菜单项选中效果 */
.el-menu-item.is-active {
  /* 取消选中背景色（透明） */
  background-color: transparent !important;
  /* 隐藏选中下划线（透明） */
  border-bottom: 2px solid transparent;
  /* 保留原始文字颜色 */
  /* color: inherit !important; */
}

.el-menu-item:hover,
.el-menu-item.is-active:hover {
  /* 自定义悬浮背景色（灰） */
  background-color: #f0f0f0 !important;
  /* 自定义悬浮文字颜色 */
  /* color: #333 !important; */
}
</style>
