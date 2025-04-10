<script setup>
import { ref } from 'vue';
import { useFullscreen } from '@vueuse/core';
import { Icon } from "@iconify/vue";
import router from '@/router/index.js'
import { logout } from '@/api/login';
import { storeToRefs } from 'pinia';
import { useAppStore } from '@/stores/appStore.js';
const appStore = useAppStore();
const { sideMenuOpened } = storeToRefs(appStore);
const { toggleSideMenuOpened } = appStore;

// 强制刷新（适合更新静态资源）
const refresh = () => { top.location.reload(true); };

const onLogout = () => {
  logout().then(() => { router.push('/login'); });
};

// 绑定目标元素（不传则默认全屏整个页面）
const target = ref(null);
const { isFullscreen, toggle: toggleFullscreen } = useFullscreen(target);

</script>

<template>
  <el-menu mode="horizontal" :ellipsis="false">
    <el-menu-item index="0" style="width: var(--el-aside-width);">
      <img src="/logo.svg" alt="logo" />
    </el-menu-item>
    <el-menu-item index="1" @click="toggleSideMenuOpened()">
      <Icon icon="ep:fold" width="24" height="24" v-if="sideMenuOpened" />
      <Icon icon="ep:expand" width="24" height="24" v-else />
    </el-menu-item>
    <el-menu-item index="3" @click="refresh()">
      <Icon icon="ep:refresh" width="24" height="24" />
    </el-menu-item>

    <el-menu-item index="5" @click="toggleFullscreen()">
      <Icon icon="ri:fullscreen-exit-fill" width="24" height="24" v-if="isFullscreen" />
      <Icon icon="ri:fullscreen-fill" width="24" height="24" v-else />
    </el-menu-item>
    <el-menu-item index="6">
      <el-icon>
        <el-badge :value="100" :max="99">
          <Icon icon="ri:notification-2-fill" width="24" height="24" />
        </el-badge>
      </el-icon>
    </el-menu-item>

    <el-sub-menu index="8">
      <template #title>
        <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
        admin
      </template>
      <el-menu-item index="8-1">
        <Icon icon="ri:home-9-fill" width="16" height="16" />
        <span>个人主页</span>
      </el-menu-item>
      <el-menu-item index="8-2">
        <Icon icon="ri:user-settings-line" width="16" height="16" />
        <span>偏好设置</span>
      </el-menu-item>
      <el-menu-item index="8-3">
        <Icon icon="ri:layout-3-fill" width="16" height="16" />
        <span>布局设置</span>
      </el-menu-item>
      <el-divider />
      <el-menu-item index="8-99" @click="onLogout()">
        <Icon icon="ri:logout-box-line" width="16" height="16" />
        <span>退出</span>
      </el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<style scoped>
/* 左右布局显示 */
.el-menu--horizontal>.el-menu-item:nth-child(3) {
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
