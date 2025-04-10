<script setup>
import LayHeader from "./LayHeader.vue";
import LaySide from "./LaySide.vue";
import LayFooter from "./LayFooter.vue";
</script>

<template>
  <el-container>
    <el-header>
      <LayHeader />
    </el-header>
    <el-container>
      <el-aside>
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
  min-height: calc(100vh - var(--vt-header-height) - var(--vt-footer-height));
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
}
</style>
