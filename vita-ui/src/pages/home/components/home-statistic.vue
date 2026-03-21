<script setup>
import { homeApi } from "@/api/home-api";

const data = ref({});

onMounted(() => {
  homeApi.queryConsoleStatistic().then((res) => {
    data.value = res;
  });
});
</script>

<template>
  <el-row :gutter="15">
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
  </el-row>
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
