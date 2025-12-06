<script setup>
import HomeLink from "./module/home-link.vue";
import HomeNoticeList from "./module/home-notice-list.vue";
import HomeOperationChart from "./module/home-operation-chart.vue";
import HomeStatistic from "./module/home-statistic.vue";
</script>

<template>
  <el-scrollbar class="vt-home">
    <el-container>
      <el-main>
        <HomeStatistic />
        <HomeOperationChart />
      </el-main>
      <el-aside width="400px">
        <HomeNoticeList />
        <HomeLink />
      </el-aside>
    </el-container>
  </el-scrollbar>
</template>


<style lang="css" scoped>
/* 隐藏垂直滚动条 */
:deep(.el-scrollbar__bar.is-vertical) {
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
}
</style>
