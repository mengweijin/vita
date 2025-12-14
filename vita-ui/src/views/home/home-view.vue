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
.el-main{
  padding: 0px 15px 15px 0px;
}
</style>
