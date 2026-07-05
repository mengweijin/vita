<route lang="yaml">
meta:
  title: 待办任务
  permission: oa:backlogTask:view
</route>

<script setup>
import { flowTaskApi } from "@/api/workflow/flow-task-api.js";
import { useFlowTask } from "./hooks.js";
const { columns } = useFlowTask();

const loading = ref(true);

const size = ref("default");

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

/**
 * 不能初始化为 null，否则 resetFields() 不生效
 */
const queryParams = reactive({
  definitionId: undefined,
  instanceId: undefined,
  nodeCode: undefined,
  nodeName: undefined,
  nodeType: undefined,
  flowStatus: undefined,
  formCustom: undefined,
  formPath: undefined,
  delFlag: undefined,
  tenantId: undefined,
  pageCurrent: 1,
  pageSize: 10,
  pageTotal: 0,
  flowName: undefined,
});

const queryFormRef = useTemplateRef("queryFormRef");

const resetQueryForm = () => {
  queryFormRef.value.resetFields();
  loadTableData();
};

const loadTableData = () => {
  loading.value = true;
  flowTaskApi
    .page(queryParams)
    .then((res) => {
      tableData.value = res.pageRecords;
      queryParams.pageTotal = res.pageTotal;
    })
    .finally(() => {
      loading.value = false;
    });
};

/** selected rows */
const selected = ref([]);

const workflowApproveDialogVisible = ref(false);
const taskId = ref("");

const handleApprove = (row) => {
  taskId.value = row.id;
  workflowApproveDialogVisible.value = true;
};

const handleRestartWorkflow = (row) => {
  flowTaskApi.pass(row.id, "重新发起审批").then((res) => {
    loadTableData();
  });
};

const handlePageChange = (currentPage, pageSize) => {
  queryParams.pageCurrent = currentPage;
  queryParams.pageSize = pageSize;
  loadTableData();
};

onMounted(() => {
  loadTableData();
});
</script>

<template>
  <!-- 查询表单 -->
  <el-form ref="queryFormRef" :model="queryParams" :inline="true" @submit.prevent="loadTableData">
    <el-form-item prop="instanceId" label="流程编号">
      <el-input v-model="queryParams.instanceId" placeholder="" clearable />
    </el-form-item>
    <el-form-item prop="flowName" label="流程名称">
      <el-input v-model="queryParams.flowName" placeholder="" clearable />
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

  <el-divider style="margin: 0px" />

  <!-- 表格头-->
  <el-row :gutter="10" style="padding: 15px 0px">
    <!-- 左侧 -->

    <!-- 右侧 -->
    <VtTableBarRight
      :tableRef="tableRef"
      :columns="columns"
      @refresh="loadTableData"
      @update-size="(val) => (size = val)"
    />
  </el-row>

  <!-- 表格 -->
  <div class="vt-table">
    <el-table
      ref="tableRef"
      v-loading="loading"
      :data="tableData"
      :size="size"
      row-key="id"
      height="100%"
      stripe
      border
      show-overflow-tooltip
      highlight-current-row
      @selection-change="(val) => (selected = val)"
    >
      <el-table-column v-if="columns.selection.visible" type="selection" width="55" />
      <el-table-column v-if="columns.index.visible" type="index" label="序号" width="60" />
      <el-table-column v-if="columns.id.visible" prop="id" label="ID" min-width="180" />
      <el-table-column
        v-if="columns.definitionId.visible"
        prop="definitionId"
        label="流程定义 ID"
        min-width="180"
      />
      <el-table-column
        v-if="columns.instanceId.visible"
        prop="instanceId"
        label="流程编号"
        min-width="180"
      />

      <el-table-column
        v-if="columns.flowName.visible"
        prop="flowName"
        label="流程名称"
        min-width="100"
      />
      <el-table-column
        v-if="columns.nodeCode.visible"
        prop="nodeCode"
        label="节点编码"
        min-width="100"
      />
      <el-table-column
        v-if="columns.nodeName.visible"
        prop="nodeName"
        label="节点名称"
        min-width="100"
      />
      <el-table-column
        v-if="columns.nodeType.visible"
        prop="nodeType"
        label="节点类型"
        width="100"
        align="center"
      >
        <template #default="{ row }">
          <VtTagDict :code="'vt_warmflow_node_type'" :value="row.nodeType" :size="size"></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.flowStatus.visible"
        prop="flowStatus"
        label="流程状态"
        width="100"
        align="center"
      >
        <template #default="{ row }">
          <VtTagDict
            :code="'vt_warmflow_flow_status'"
            :value="row.flowStatus"
            :size="size"
          ></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.formCustom.visible"
        prop="formCustom"
        label="自定义表单"
        width="100"
        align="center"
      >
        <template #default="{ row }">
          <VtTagDict
            :code="'vt_warmflow_form_custom'"
            :value="row.formCustom"
            :size="size"
          ></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.formPath.visible"
        prop="formPath"
        label="表单路径"
        min-width="100"
      />
      <el-table-column
        v-if="columns.createByName.visible"
        prop="createByName"
        label="创建者"
        align="center"
        width="100"
      />
      <el-table-column
        v-if="columns.createTime.visible"
        prop="createTime"
        label="创建时间"
        align="center"
        width="180"
      />
      <el-table-column
        v-if="columns.updateByName.visible"
        prop="updateByName"
        label="更新者"
        align="center"
        width="100"
      />
      <el-table-column
        v-if="columns.updateTime.visible"
        prop="updateTime"
        label="更新时间"
        align="center"
        width="180"
      />
      <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="120">
        <template #default="scope">
          <div>
            <el-tooltip content="审批" placement="top">
              <el-button
                v-if="scope.row.flowStatus === '1'"
                type="primary"
                text
                :size="size"
                @click="handleApprove(scope.row)"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ri:chat-check-line"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="重新发起流程" placement="top">
              <el-button
                v-if="scope.row.flowStatus === '6' || scope.row.flowStatus === '9'"
                type="primary"
                text
                :size="size"
                style="margin-left: 0"
                @click="handleRestartWorkflow(scope.row)"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:promotion"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      background
      layout="total, sizes, prev, pager, next, jumper"
      v-model:current-page="queryParams.pageCurrent"
      v-model:page-size="queryParams.pageSize"
      :total="queryParams.pageTotal"
      @change="handlePageChange"
    />

    <VtDialogWorkflowApprove
      v-model:visible="workflowApproveDialogVisible"
      :task-id="taskId"
      @refresh="loadTableData"
    />
  </div>
</template>

<style scoped></style>
