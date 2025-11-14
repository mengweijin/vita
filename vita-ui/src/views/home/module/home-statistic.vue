<script setup>
import { homeApi } from '@/api/home-api';

const data = ref({});

onMounted(() => {
  homeApi.getConsoleStatistic().then((res) => {
    data.value = res;
  });
});
</script>

<template>
  <div style="padding-top: 15px;">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-statistic :value="data?.dailyUserLoginCount" class="vt-statistic-item">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              <el-icon style="margin-right: 4px" :size="24">
                <Icon icon="ri:group-fill"></Icon>
              </el-icon>
              日用户登录次数
            </div>
          </template>
        </el-statistic>
      </el-col>
      <el-col :span="6">
        <el-statistic :value="data?.totalUserLoginCount" class="vt-statistic-item">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              <el-icon style="margin-right: 4px" :size="24">
                <Icon icon="ri:group-fill"></Icon>
              </el-icon>
              累计用户登录次数
            </div>
          </template>
        </el-statistic>
      </el-col>
      <el-col :span="6">
        <el-statistic :value="data?.dailyUserOperationCount" class="vt-statistic-item">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              <el-icon style="margin-right: 4px" :size="24">
                <Icon icon="ep:pointer"></Icon>
              </el-icon>
              日操作次数
            </div>
          </template>
          <template #suffix>次</template>
        </el-statistic>
      </el-col>
      <el-col :span="6">
        <el-statistic :value="data?.totalUserOperationCount" class="vt-statistic-item">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              <el-icon style="margin-right: 4px" :size="24">
                <Icon icon="ep:pointer"></Icon>
              </el-icon>
              累计用户操作次数
            </div>
          </template>
          <template #suffix>次</template>
        </el-statistic>
      </el-col>

      <el-col :span="6" v-if="false">
        <el-statistic :value="data?.dailyScheduledTaskExecutedCount" class="vt-statistic-item">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              <el-icon style="margin-right: 4px" :size="24">
                <Icon icon="ep:clock"></Icon>
              </el-icon>
              日执行定时任务数
            </div>
          </template>
        </el-statistic>
      </el-col>
      <el-col :span="6" v-if="false">
        <el-statistic :value="data?.totalScheduledTaskExecutedCount" class="vt-statistic-item">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              <el-icon style="margin-right: 4px" :size="24">
                <Icon icon="ep:clock"></Icon>
              </el-icon>
              总计执行定时任务数
            </div>
          </template>
        </el-statistic>
      </el-col>
    </el-row>
  </div>
</template>


<style lang="css" scoped>
.vt-statistic-item {
  padding: 15px 0px;
  background-color: white;
}

.el-col {
  text-align: center;
}
</style>
