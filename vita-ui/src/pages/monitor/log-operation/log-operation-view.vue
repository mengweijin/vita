<route lang="yaml">
meta:
  title: 操作日志
</route>

<script setup>
import { logOperationApi } from "@/api/monitor/log-operation-api";
import LogOperationDetail from "./log-operation-detail.vue";

const loading = ref(true);

const size = ref("default");

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

const columns = reactive({
	costTime: { label: "执行时间（ms）", visible: true },
	createByName: { label: "操作者", visible: true },
	createTime: { label: "操作时间", visible: true },
	errorMsg: { label: "失败信息", visible: false },
	httpMethod: { label: "请求方式", visible: true },
	id: { label: "ID", visible: false },
	index: { label: "序号列", visible: false },
	methodName: { label: "方法名称", visible: true },
	operation: { label: "操作", visible: true },
	operationType: { label: "操作类型", visible: true },
	requestData: { label: "请求数据", visible: false },
	responseData: { label: "响应数据", visible: false },
	selection: { label: "选择列", visible: false },
	success: { label: "操作状态", visible: true },
	title: { label: "模块标题", visible: true },
	updateByName: { label: "更新者", visible: false },
	updateTime: { label: "更新时间", visible: false },
	url: { label: "URL", visible: true },
});

/**
 * 不能初始化为 null，否则 resetFields() 不生效
 */
const queryParams = reactive({
	current: 1,
	httpMethod: undefined,
	keywords: undefined,
	operationType: undefined,
	size: 10,
	total: 0,
});

const queryFormRef = useTemplateRef("queryFormRef");

const resetQueryForm = () => {
	queryFormRef.value.resetFields();
	loadTableData();
};

const loadTableData = () => {
	loading.value = true;
	logOperationApi.page(queryParams).then((res) => {
		tableData.value = res.records;
		queryParams.total = res.total;
		loading.value = false;
	});
};

/** selected rows */
const selected = ref([]);

const handleDelete = (ids) => {
	logOperationApi.remove(ids).then(() => {
		// 清空已选择
		selected.value = [];
		loadTableData();
	});
};

const handleBatchDelete = () => {
	const ids = selected.value.map((item) => item.id).join();
	handleDelete(ids);
};

const handlePageChange = (currentPage, pageSize) => {
	queryParams.current = currentPage;
	queryParams.size = pageSize;
	loadTableData();
};

const logOperationDetailRef = useTemplateRef("logOperationDetailRef");
const handleDetail = (row) => {
	logOperationDetailRef.value.data = { ...row };
	logOperationDetailRef.value.visible = true;
};

onMounted(() => {
	loadTableData();
});
</script>

<template>
  <!-- 查询表单 -->
  <el-form ref="queryFormRef" :model="queryParams" :inline="true" @submit.prevent="loadTableData">
    <el-form-item prop="keywords" label="关键字">
      <el-input v-model="queryParams.keywords" placeholder="模块标题、URL、方法名称" clearable />
    </el-form-item>
    <el-form-item prop="operationType" label="操作类型">
      <VtSelectDict v-model="queryParams.operationType" :code="'vt_operation_log_type'"></VtSelectDict>
    </el-form-item>
    <el-form-item prop="httpMethod" label="请求方式">
      <VtSelectDict v-model="queryParams.httpMethod" :code="'vt_http_request_type'"></VtSelectDict>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" native-type="submit">
        <template #icon>
          <el-icon>
            <Icon icon="ep:search"></Icon>
          </el-icon>
        </template>
        搜索
      </el-button>
      <el-button type="warning" @click="resetQueryForm">
        <template #icon>
          <el-icon>
            <Icon icon="ep:refresh-left"></Icon>
          </el-icon>
        </template>
        重置
      </el-button>
    </el-form-item>
  </el-form>

  <el-divider style="margin: 0px;" />

  <!-- 表格头-->
  <el-row :gutter="10" style="padding: 15px 0px">
    <!-- 左侧 -->
    <el-col :span="1.5" v-show="selected.length">
      <el-popconfirm placement="right" width="400" :title="`确定全部删除已选择的【${selected.map(i => i.username).join()}】吗？`"
        confirm-button-text="确定" cancel-button-text="取消" @confirm="handleBatchDelete">
        <template #reference>
          <el-button type="danger">
            <template #icon>
              <el-icon>
                <Icon icon="ep:delete"></Icon>
              </el-icon>
            </template>
            批量删除
          </el-button>
        </template>
      </el-popconfirm>
    </el-col>
    <!-- 右侧 -->
    <VtTableBarRight :tableRef="tableRef" :columns="columns" @update-size="(val) => size = val" />
  </el-row>

  <!-- 表格 -->
  <div class="vt-table">
    <el-table ref="tableRef" v-loading="loading" :data="tableData" :size="size" row-key="id" height="100%" stripe border
      show-overflow-tooltip highlight-current-row @selection-change="(val) => selected = val">
      <el-table-column v-if="columns.selection.visible" type="selection" width="55" />
      <el-table-column v-if="columns.index.visible" type="index" label="序号" width="60" />
      <el-table-column v-if="columns.id.visible" prop="id" label="ID" min-width="180" />
      <el-table-column v-if="columns.title.visible" prop="title" label="模块标题" min-width="100" fixed="left" />
      <el-table-column v-if="columns.operationType.visible" prop="operationType" label="操作类型" min-width="100"
        align="center">
        <template #default="{ row }">
          <VtTagDict :code="'vt_operation_log_type'" :value="row.operationType" :size="size"></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column v-if="columns.httpMethod.visible" prop="httpMethod" label="请求方式" min-width="100" align="center">
        <template #default="{ row }">
          <VtTagDict :code="'vt_http_request_type'" :value="row.httpMethod" :size="size"></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column v-if="columns.url.visible" prop="url" label="URL" min-width="220" />
      <el-table-column v-if="columns.methodName.visible" prop="methodName" label="方法名称" min-width="190" />
      <el-table-column v-if="columns.costTime.visible" prop="costTime" label="执行时间" min-width="100" align="center">
        <template #default="{ row }">
          {{ row.costTime }} 毫秒
        </template>
      </el-table-column>
      <el-table-column v-if="columns.success.visible" prop="success" label="操作状态" min-width="100" align="center">
        <template #default="{ row }">
          <VtTagDict :code="'vt_succeeded'" :value="row.success" :size="size"></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column v-if="columns.requestData.visible" prop="requestData" label="请求数据" min-width="260" />
      <el-table-column v-if="columns.responseData.visible" prop="responseData" label="响应数据" min-width="260" />
      <el-table-column v-if="columns.errorMsg.visible" prop="errorMsg" label="失败信息" min-width="260" />
      <el-table-column v-if="columns.createByName.visible" prop="createByName" label="操作者" align="center" width="100" />
      <el-table-column v-if="columns.createTime.visible" prop="createTime" label="操作时间" align="center" width="180" />
      <el-table-column v-if="columns.updateByName.visible" prop="updateByName" label="更新者" align="center" width="100" />
      <el-table-column v-if="columns.updateTime.visible" prop="updateTime" label="更新时间" align="center" width="180" />
      <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="120">
        <template #default="scope">
          <div>
            <el-tooltip content="详情" placement="top">
              <el-button type="primary" text :size="size" @click="handleDetail(scope.row)">
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:view"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <div style="display: inline-block;">
                <el-popconfirm placement="left" width="400" :title="`确定删除账号为【${scope.row.username}】的登录记录吗？`"
                  confirm-button-text="确定" cancel-button-text="取消" @confirm="handleDelete(scope.row.id)">
                  <template #reference>
                    <el-button type="danger" text :size="size">
                      <template #icon>
                        <el-icon :size="size">
                          <Icon icon="ep:delete"></Icon>
                        </el-icon>
                      </template>
                    </el-button>
                  </template>
                </el-popconfirm>
              </div>
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination background layout="total, sizes, prev, pager, next, jumper"
      v-model:current-page="queryParams.current" v-model:page-size="queryParams.size" :total="queryParams.total"
      @change="handlePageChange" />
  </div>

  <LogOperationDetail ref="logOperationDetailRef"></LogOperationDetail>
</template>

<style scoped></style>
