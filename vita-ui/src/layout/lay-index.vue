<script setup>
import LayHeader from "./lay-header.vue";
import LaySide from "./lay-side.vue";
import LayFooter from "./lay-footer.vue";

import { useAppStore } from '@/store/app-store';
const appStore = useAppStore();
const { sideMenuOpened } = storeToRefs(appStore);

const asideWidth = computed(() => sideMenuOpened.value ? '200px' : '64px');
</script>

<template>
  <el-container>
    <el-header>
      <LayHeader />
    </el-header>
    <el-container>
      <el-aside :width="asideWidth" style="transition: width 0.3s;">
        <LaySide />
      </el-aside>
      <el-container>
        <el-main>
          <RouterView />
        </el-main>
        <el-footer>
          <LayFooter />
        </el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>

<style scoped>
:deep(.el-main) {
  height: calc(100vh - var(--vt-header-height) - var(--vt-footer-height));
  overflow-y: hidden;
  padding: 0 15px;
}

:deep(.el-header) {
  --el-header-padding: 0px 0px;
}

:deep(.el-footer) {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  align-content: center;
  background-color: transparent;
  height: var(--vt-footer-height);
}
</style>
