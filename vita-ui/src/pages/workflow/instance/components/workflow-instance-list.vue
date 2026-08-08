<script setup>
import { flowInstanceApi } from "@/api/workflow/flow-instance-api.js";
import { flowDefinitionApi } from "@/api/workflow/flow-definition-api.js";
import { useFlowInstance } from "../hooks.js";
import utils from "@/utils/utils.js";
const { columns } = useFlowInstance();

const props = defineProps({
  /**
   * 数据范围：["my", "all"]
   * my：代表只显示与我相关的流程实例，如我发起的、我的待办/已办等。
   * all：代表显示所有流程实例，通常用于管理员查看全部流程实例的场景。
   */
  scope: {
    type: String,
    default: "my",
  },
});

const loading = ref(true);

const size = ref("default");

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

/**
 * 不能初始化为 null，否则 resetFields() 不生效
 */
const queryParams = reactive({
  flowStatus: undefined,
  activityStatus: undefined,
  category: undefined,
  delFlag: undefined,
  ext: undefined,
  flowCode: undefined,
  flowName: undefined,
  formCustom: undefined,
  formPath: undefined,
  isPublish: undefined,
  listenerPath: undefined,
  listenerType: undefined,
  modelValue: undefined,
  pageCurrent: 1,
  pageSize: 10,
  pageTotal: 0,
  tenantId: undefined,
  version: undefined,
});

const queryFormRef = useTemplateRef("queryFormRef");

const resetQueryForm = () => {
  queryFormRef.value.resetFields();
  loadTableData();
};

const loadTableData = () => {
  loading.value = true;
  if (props.scope === "my") {
    flowInstanceApi
      .pageMyFlow(queryParams)
      .then((res) => {
        tableData.value = res.pageRecords;
        queryParams.pageTotal = res.pageTotal;
      })
      .finally(() => {
        loading.value = false;
      });
  } else if (props.scope === "all") {
    flowInstanceApi
      .page(queryParams)
      .then((res) => {
        tableData.value = res.pageRecords;
        queryParams.pageTotal = res.pageTotal;
      })
      .finally(() => {
        loading.value = false;
      });
  }
};

// -----------------------------------------
/** selected rows */
const selected = ref([]);

const handlePageChange = (currentPage, pageSize) => {
  queryParams.pageCurrent = currentPage;
  queryParams.pageSize = pageSize;
  loadTableData();
};

const workflowChartDialogInstanceId = ref(null);
const workflowChartDialogVisible = ref(false);

const handleView = (row) => {
  workflowChartDialogInstanceId.value = row.id;
  workflowChartDialogVisible.value = true;
};

const dialogPageLoaderWorkflowFormVisible = ref(false);
const dialogPageLoaderWorkflowFormDefinitionId = ref(null);
const dialogPageLoaderWorkflowFormBusinessId = ref(null);
const dialogPageLoaderWorkflowFormApi = ref(null);
const handleEdit = async (row) => {
  dialogPageLoaderWorkflowFormDefinitionId.value = row.definitionId;
  dialogPageLoaderWorkflowFormBusinessId.value = row.businessId;
  dialogPageLoaderWorkflowFormApi.value = null;
  dialogPageLoaderWorkflowFormVisible.value = true;
};

const handleSubmit = (row) => {
  flowInstanceApi.submit(row.id).then(() => {
    loadTableData();
  });
};

const handleRevoke = (row) => {
  flowInstanceApi.revoke(row.id).then(() => {
    loadTableData();
  });
};

const handleTermination = (row) => {
  flowInstanceApi.termination(row.id, { ignore: true }).then(() => {
    loadTableData();
  });
};

const handleRemove = (row) => {
  flowInstanceApi.remove(row.id).then(() => {
    loadTableData();
  });
};

onMounted(() => {
  loadTableData();
});
</script>

<template>
  <!-- 查询表单 -->
  <el-form ref="queryFormRef" :model="queryParams" :inline="true" @submit.prevent="loadTableData">
    <el-form-item prop="flowName" label="流程名称">
      <el-input v-model="queryParams.flowName" placeholder="" clearable style="width: 140px" />
    </el-form-item>
    <el-form-item prop="flowStatus" label="流程状态">
      <VtSelectDict
        v-model="queryParams.flowStatus"
        :code="'vt_warmflow_flow_status'"
        :style="'width: 160px;'"
      />
    </el-form-item>
    <el-form-item prop="activityStatus" label="激活状态">
      <VtSelectDict
        v-model="queryParams.activityStatus"
        :code="'vt_warmflow_activity_status'"
        :style="'width: 120px;'"
      />
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
    <el-col :span="1.5" v-show="selected.length" v-permission="'workflow:definition:remove'">
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
      <el-table-column v-if="columns.id.visible" prop="id" label="流程号" min-width="180" />
      <el-table-column
        v-if="columns.definitionId.visible"
        prop="definitionId"
        label="流程定义ID"
        min-width="100"
      />
      <el-table-column
        v-if="columns.flowName.visible"
        prop="flowName"
        label="流程名称"
        min-width="200"
      >
        <template #default="{ row }"> {{ row.flowName }} （{{ row.createByName }}）</template>
      </el-table-column>
      <el-table-column
        v-if="columns.businessId.visible"
        prop="businessId"
        label="业务ID"
        width="180"
      />

      <el-table-column v-if="columns.nodeType.visible" prop="nodeType" label="节点类型" width="100">
        <template #default="{ row }">
          <VtTagDict :code="'vt_warmflow_node_type'" :value="row.nodeType" :size="size"></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.nodeCode.visible"
        prop="nodeCode"
        label="流程节点编码"
        width="120"
        align="center"
      />
      <el-table-column
        v-if="columns.nodeName.visible"
        prop="nodeName"
        label="流程节点名称"
        width="120"
        align="center"
      />
      <el-table-column
        v-if="columns.variable.visible"
        prop="variable"
        label="流程变量"
        width="100"
        align="center"
      />
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
        v-if="columns.activityStatus.visible"
        prop="activityStatus"
        label="流程激活状态"
        width="120"
        align="center"
      >
        <template #default="{ row }">
          <VtTagDict
            :code="'vt_warmflow_activity_status'"
            :value="row.activityStatus"
            :size="size"
          ></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.formCustom.visible"
        prop="formCustom"
        label="自定义表单"
        width="130"
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
        v-if="columns.defJson.visible"
        prop="defJson"
        label="流程定义json"
        min-width="100"
      />
      <el-table-column v-if="columns.ext.visible" prop="ext" label="扩展字段" min-width="100" />
      <el-table-column
        v-if="columns.createByName.visible"
        prop="createByName"
        label="发起者"
        align="center"
        width="90"
      />
      <el-table-column
        v-if="columns.createTime.visible"
        prop="createTime"
        label="发起时间"
        align="center"
        width="160"
      />
      <el-table-column
        v-if="columns.updateByName.visible"
        prop="updateByName"
        label="更新者"
        align="center"
        width="90"
      />
      <el-table-column
        v-if="columns.updateTime.visible"
        prop="updateTime"
        label="更新时间"
        align="center"
        width="160"
      />
      <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="220">
        <template #default="scope">
          <div>
            <el-tooltip content="查看流程" placement="top">
              <el-button type="primary" text :size="size" @click="handleView(scope.row)">
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:view"></Icon>
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
                v-if="scope.row.flowStatus === '0' || scope.row.flowStatus === '9'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:edit"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="提交流程" placement="top">
              <div
                style="display: inline-block"
                v-if="scope.row.flowStatus === '0' || scope.row.flowStatus === '9'"
              >
                <el-popconfirm
                  placement="left"
                  width="400"
                  :title="`确定提交【${scope.row.id} - ${scope.row.flowName}】吗？`"
                  confirm-button-text="确定"
                  cancel-button-text="取消"
                  @confirm="handleSubmit(scope.row)"
                >
                  <template #reference>
                    <el-button type="primary" text :size="size">
                      <template #icon>
                        <el-icon :size="size">
                          <Icon icon="ep:promotion"></Icon>
                        </el-icon>
                      </template>
                    </el-button>
                  </template>
                </el-popconfirm>
              </div>
            </el-tooltip>
            <el-tooltip content="撤销流程" placement="top">
              <div
                style="display: inline-block"
                v-if="scope.row.flowStatus === '1' || scope.row.flowStatus === '9'"
              >
                <el-popconfirm
                  placement="left"
                  width="400"
                  :title="`确定撤销【${scope.row.id} - ${scope.row.flowName}】吗？`"
                  confirm-button-text="确定"
                  cancel-button-text="取消"
                  @confirm="handleRevoke(scope.row)"
                >
                  <template #reference>
                    <el-button type="danger" text :size="size">
                      <template #icon>
                        <el-icon :size="size">
                          <Icon icon="ri:arrow-go-back-fill"></Icon>
                        </el-icon>
                      </template>
                    </el-button>
                  </template>
                </el-popconfirm>
              </div>
            </el-tooltip>
            <el-tooltip content="终止流程" placement="top">
              <div
                style="display: inline-block"
                v-if="props.scope === 'all' && scope.row.flowStatus === '1'"
              >
                <el-popconfirm
                  placement="left"
                  width="400"
                  :title="`确定终止【${scope.row.id} - ${scope.row.flowName}】吗？`"
                  confirm-button-text="确定"
                  cancel-button-text="取消"
                  @confirm="handleTermination(scope.row)"
                >
                  <template #reference>
                    <el-button
                      type="danger"
                      text
                      :size="size"
                      v-permission="'workflow:instance:termination'"
                    >
                      <template #icon>
                        <el-icon :size="size">
                          <Icon icon="ep:circle-close"></Icon>
                        </el-icon>
                      </template>
                    </el-button>
                  </template>
                </el-popconfirm>
              </div>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <div
                style="display: inline-block"
                v-if="
                  scope.row.flowStatus === '0' ||
                  scope.row.flowStatus === '4' ||
                  scope.row.flowStatus === '5' ||
                  scope.row.flowStatus === '6'
                "
              >
                <el-popconfirm
                  placement="left"
                  width="400"
                  :title="`确定删除【${scope.row.id} - ${scope.row.flowName}】吗？`"
                  confirm-button-text="确定"
                  cancel-button-text="取消"
                  @confirm="handleRemove(scope.row)"
                >
                  <template #reference>
                    <el-button
                      type="danger"
                      text
                      :size="size"
                      v-permission="'workflow:instance:remove'"
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

    <VtDialogWorkflowChart
      v-model:visible="workflowChartDialogVisible"
      :id="workflowChartDialogInstanceId"
    />

    <VtDialogPageLoaderWorkflowForm
      v-model="dialogPageLoaderWorkflowFormVisible"
      :definition-id="dialogPageLoaderWorkflowFormDefinitionId"
      :business-id="dialogPageLoaderWorkflowFormBusinessId"
      :api="dialogPageLoaderWorkflowFormApi"
      :title="'编辑'"
    />
  </div>
</template>

<style scoped></style>
