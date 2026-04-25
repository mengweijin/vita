<script setup>
import { userApi } from "@/api/system/user-api.js";

const loading = ref(true);

const size = ref("default");

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

const loadTableData = () => {
  loading.value = true;
  userApi.queryTerminalInfo().then((res) => {
    tableData.value = res;
    loading.value = false;
  });
};

onMounted(() => {
  loadTableData();
});
</script>

<template>
  <el-table
    ref="tableRef"
    v-loading="loading"
    :data="tableData"
    :size="size"
    row-key="deviceId"
    height="100%"
    stripe
    border
    show-overflow-tooltip
    highlight-current-row
  >
    <el-table-column prop="index" label="会话索引" min-width="60" />
    <el-table-column prop="deviceId" label="设备唯一标识" min-width="220" />
    <el-table-column prop="deviceType" label="设备类型" min-width="80" />
    <el-table-column prop="tokenValue" label="Token" min-width="180" />
    <el-table-column v-if="false" prop="encryptTokenValue" label="Token" min-width="180" />
    <el-table-column prop="createTime" label="创建时间" align="center" width="180" />
  </el-table>
</template>

<style scoped></style>
