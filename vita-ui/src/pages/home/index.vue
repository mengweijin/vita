<route lang="yaml">
meta:
  title: 首页
  closable: false
</route>

<script setup>
import HomeLink from "./components/home-link.vue";
import HomeNoticeList from "./components/home-notice-list.vue";
import HomeOperationChart from "./components/home-operation-chart.vue";
import HomeStatistic from "./components/home-statistic.vue";
</script>

<template>
  <el-scrollbar class="vt-height">
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
  </el-scrollbar>
</template>

<style lang="css" scoped>
.vt-height {
  height: calc(var(--vt-tab-content-height));
}

.el-main {
  padding: 0px 15px 15px 0px;
}
</style>
