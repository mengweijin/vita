<route lang="yaml">
meta:
  title: 已办任务
  permission: oa:doneTask:view
</route>

<script setup>
import { flowHisTaskApi } from "@/api/workflow/flow-his-task-api.js";
import { useFlowHisTask } from "./hooks.js";
const { columns } = useFlowHisTask();

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
  taskId: undefined,
  nodeCode: undefined,
  nodeName: undefined,
  nodeType: undefined,
  targetNodeCode: undefined,
  targetNodeName: undefined,
  approver: undefined,
  cooperateType: undefined,
  collaborator: undefined,
  skipType: undefined,
  flowStatus: undefined,
  formCustom: undefined,
  formPath: undefined,
  message: undefined,
  variable: undefined,
  ext: undefined,
  delFlag: undefined,
  tenantId: undefined,
  pageCurrent: 1,
  pageSize: 10,
  pageTotal: 0,
});

const queryFormRef = useTemplateRef("queryFormRef");

const resetQueryForm = () => {
  queryFormRef.value.resetFields();
  loadTableData();
};

const loadTableData = () => {
  loading.value = true;
  flowHisTaskApi
    .page(queryParams)
    .then((res) => {
      tableData.value = res.pageRecords;
      queryParams.pageTotal = res.pageTotal;
    })
    .finally(() => {
      loading.value = false;
    });
};

const editDialogVisible = ref(false);
const editData = ref(null);

const handleAdd = () => {
  editData.value = null;
  editDialogVisible.value = true;
};

const handleEdit = (row) => {
  // 使用展开运算符，避免数据污染
  editData.value = { ...row };
  editDialogVisible.value = true;
};

/** selected rows */
const selected = ref([]);

const handleDelete = (ids) => {
  flowHisTaskApi.remove(ids).then(() => {
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
    <el-col :span="1.5" v-permission="'workflow:flowHisTask:create'">
      <el-button type="primary" @click="handleAdd">
        <template #icon>
          <el-icon>
            <Icon icon="ep:plus"></Icon>
          </el-icon>
        </template>
        新增
      </el-button>
    </el-col>
    <el-col :span="1.5" v-show="selected.length" v-permission="'workflow:flowHisTask:remove'">
      <el-popconfirm
        placement="right"
        width="400"
        :title="`确定全部删除已选择的【${selected.map((i) => i.name).join()}】吗？`"
        confirm-button-text="确定"
        cancel-button-text="取消"
        @confirm="handleBatchDelete"
      >
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
        label="流程定义ID"
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
      <el-table-column v-if="columns.taskId.visible" prop="taskId" label="任务ID" min-width="100" />
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
        min-width="100"
        align="center"
      >
        <template #default="{ row }">
          <VtTagDict :code="'vt_warmflow_node_type'" :value="row.nodeType" :size="size"></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.targetNodeCode.visible"
        prop="targetNodeCode"
        label="目标节点编码"
        min-width="100"
      />
      <el-table-column
        v-if="columns.targetNodeName.visible"
        prop="targetNodeName"
        label="目标节点名称"
        min-width="100"
      />
      <el-table-column
        v-if="columns.approver.visible"
        prop="approver"
        label="审批人"
        min-width="100"
        align="center"
      />
      <el-table-column
        v-if="columns.cooperateType.visible"
        prop="cooperateType"
        label="协作类型"
        min-width="100"
        align="center"
      >
        <template #default="{ row }">
          <VtTagDict
            :code="'vt_warmflow_node_cooperate_type'"
            :value="row.cooperateType"
            :size="size"
          ></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.collaborator.visible"
        prop="collaborator"
        label="协作者"
        min-width="100"
        align="center"
      />
      <el-table-column
        v-if="columns.skipType.visible"
        prop="skipType"
        label="跳转类型"
        min-width="100"
        align="center"
      >
        <template #default="{ row }">
          <VtTagDict
            :code="'vt_warmflow_node_skip_type'"
            :value="row.skipType"
            :size="size"
          ></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.flowStatus.visible"
        prop="flowStatus"
        label="流程状态"
        min-width="100"
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
        min-width="100"
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
      <el-table-column v-if="columns.message.visible" prop="message" label="消息" min-width="100" />
      <el-table-column
        v-if="columns.variable.visible"
        prop="variable"
        label="变量"
        min-width="100"
      />
      <el-table-column v-if="columns.ext.visible" prop="ext" label="扩展属性" min-width="100" />
      <el-table-column
        v-if="columns.createTime.visible"
        prop="createTime"
        label="创建时间"
        align="center"
        width="180"
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
            <el-tooltip content="新增" placement="top" v-if="false">
              <el-button
                type="primary"
                text
                :size="size"
                @click="handleAdd(scope.row.id)"
                v-permission="'workflow:flowHisTask:create'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:plus"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="编辑" placement="top">
              <el-button
                type="primary"
                text
                :size="size"
                style="margin-left: 0px"
                @click="handleEdit(scope.row)"
                v-permission="'workflow:flowHisTask:update'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:edit"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <div style="display: inline-block">
                <el-popconfirm
                  placement="left"
                  width="400"
                  :title="`确定删除【${scope.row.name}】吗？`"
                  confirm-button-text="确定"
                  cancel-button-text="取消"
                  @confirm="handleDelete(scope.row.id)"
                >
                  <template #reference>
                    <el-button
                      type="danger"
                      text
                      :size="size"
                      v-permission="'workflow:flowHisTask:remove'"
                    >
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

    <el-pagination
      background
      layout="total, sizes, prev, pager, next, jumper"
      v-model:current-page="queryParams.pageCurrent"
      v-model:page-size="queryParams.pageSize"
      :total="queryParams.pageTotal"
      @change="handlePageChange"
    />
  </div>
</template>

<style scoped></style>
