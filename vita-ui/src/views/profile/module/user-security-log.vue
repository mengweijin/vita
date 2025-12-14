<script setup>
import { logLoginApi } from "@/api/monitor/log-login-api";

const loading = ref(true);

const size = ref("default");

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

const queryParams = reactive({
	current: 1,
	size: 10,
	total: 0,
});

const loadTableData = () => {
	loading.value = true;
	logLoginApi.pageByLoginUser(queryParams).then((res) => {
		tableData.value = res.records;
		queryParams.total = res.total;
		loading.value = false;
	});
};

const handlePageChange = (currentPage, pageSize) => {
	queryParams.current = currentPage;
	queryParams.size = pageSize;
	loadTableData();
};

onMounted(() => {
	loadTableData();
});
</script>

<template>
  <el-table ref="tableRef" v-loading="loading" :data="tableData" :size="size" row-key="id" height="100%" stripe border
      show-overflow-tooltip highlight-current-row @selection-change="(val) => selected = val">
      <el-table-column v-if="false" prop="id" label="ID" min-width="180" />
      <el-table-column v-if="false" prop="username" label="登录账号" min-width="100" fixed="left" />
      <el-table-column prop="loginType" label="登录类型" min-width="100" align="center">
        <template #default="{ row }">
          <VtTagDict :code="'vt_login_type'" :value="row.loginType" :size="size"></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column prop="ip" label="IP" min-width="140" />
      <el-table-column prop="ipLocation" label="登录位置" min-width="180" />
      <el-table-column prop="browser" label="浏览器" min-width="100" />
      <el-table-column prop="platform" label="设备平台" min-width="100" />
      <el-table-column prop="os" label="操作系统" min-width="220" />
      <el-table-column prop="success" label="登录状态" min-width="100" align="center">
        <template #default="{ row }">
          <VtTagDict :code="'vt_succeeded'" :value="row.success" :size="size"></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column v-if="false" prop="createByName" label="操作者" align="center" width="100" />
      <el-table-column prop="createTime" label="登录时间" align="center" width="180" />
      <el-table-column v-if="false" prop="updateByName" label="更新者" align="center" width="100" />
      <el-table-column v-if="false" prop="updateTime" label="更新时间" align="center" width="180" />
    </el-table>

    <el-pagination background layout="total, sizes, prev, pager, next, jumper"
      v-model:current-page="queryParams.current" v-model:page-size="queryParams.size" :total="queryParams.total"
      @change="handlePageChange" />
</template>

<style scoped></style>
