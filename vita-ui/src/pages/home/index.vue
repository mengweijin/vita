<route lang="yaml">
meta:
  title: 首页
</route>

<script setup>
import HomeLink from "./components/home-link.vue";
import HomeNoticeList from "./components/home-notice-list.vue";
import HomeOperationChart from "./components/home-operation-chart.vue";
import HomeStatistic from "./components/home-statistic.vue";
</script>

<template>
  <div class="vt-height">
    <el-scrollbar>
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
  </div>

</template>


<style lang="css" scoped>
.vt-height {
  height: calc(var(--vt-content-aside-height));
}
.el-main {
  padding: 0px 15px 15px 0px;
}
</style>
