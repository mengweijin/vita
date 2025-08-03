<script setup>
import VtConsoleStatistic from './module/vt-console-statistic.vue';
import VtConsoleNoticeList from './module/vt-console-notice-list.vue';
</script>

<template>
  <el-container>
    <el-main>
      <VtConsoleStatistic></VtConsoleStatistic>
    </el-main>
    <el-aside width="450px">
      <VtConsoleNoticeList></VtConsoleNoticeList>
    </el-aside>
  </el-container>

</template>


<style lang="css" scoped>
:deep(.el-main) {
  padding: 0 0px;
}

:deep(.el-aside) {
  padding: 15px 0px 15px 10px;
  overflow-x: hidden;
}
</style>
