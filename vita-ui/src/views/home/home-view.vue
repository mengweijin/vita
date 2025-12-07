<script setup>
import HomeLink from "./module/home-link.vue";
import HomeNoticeList from "./module/home-notice-list.vue";
import HomeOperationChart from "./module/home-operation-chart.vue";
import HomeStatistic from "./module/home-statistic.vue";
</script>

<template>
  <el-container>
    <el-main>
      <HomeStatistic />
      <HomeOperationChart />
    </el-main>
    <el-aside width="380px">
      <HomeNoticeList />
      <HomeLink />
    </el-aside>
  </el-container>
</template>


<style lang="css" scoped>

:deep(.el-main) {
  padding: 0 0;
  height: 100%;
  overflow-x: hidden;
}

aside.el-aside {
  padding: 0px 0px 0px 15px;
}

/* 隐藏垂直滚动条 */
/* :deep(.el-scrollbar__bar.is-vertical) {
  display: none !important;
}

.vt-home {
  max-height: calc(100vh - var(--vt-header-height) - var(--vt-footer-height));
}

:deep(.el-main) {
  padding: 0px 10px 0px 0px;
}

:deep(.el-aside) {
  padding: 15px 0px 15px 10px;
} */
</style>
