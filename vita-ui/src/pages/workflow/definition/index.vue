<route lang="yaml">
meta:
  title: 流程定义
  permission: workflow:flowDefinition:view
</route>

<script setup>
import { flowDefinitionApi } from "@/api/workflow/flow-definition-api.js";
import { useFlowDefinition } from "./hooks.js";
import utils from "@/utils/utils.js";
const { columns } = useFlowDefinition();

const router = useRouter();

const loading = ref(true);

const size = ref("default");

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

/**
 * 不能初始化为 null，否则 resetFields() 不生效
 */
const queryParams = reactive({
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
  flowDefinitionApi.page(queryParams).then((res) => {
    tableData.value = res.pageRecords;
    queryParams.pageTotal = res.pageTotal;
    loading.value = false;
  });
};

// -----------------------------------------
const editDialogVisible = ref(false);
const editDialogTitle = ref("");
const editDataId = ref(null);
const editOnlyDesignShow = ref(false);
const editDisabled = ref(false);

const handleAdd = () => {
  editDataId.value = "";
  editOnlyDesignShow.value = false;
  editDisabled.value = false;
  editDialogTitle.value = "流程定义 - 新增";
  editDialogVisible.value = true;
};

const handleEdit = (row) => {
  editDataId.value = row.id;
  editOnlyDesignShow.value = false;
  editDisabled.value = false;
  editDialogTitle.value = "流程定义 - 编辑";
  editDialogVisible.value = true;
};

const handleView = (row) => {
  editDataId.value = row.id;
  editOnlyDesignShow.value = true;
  editDisabled.value = true;
  editDialogTitle.value = "流程定义 - 查看";
  editDialogVisible.value = true;
};

const handleDesign = (row) => {
  editDataId.value = row.id;
  editOnlyDesignShow.value = true;
  editDisabled.value = false;
  editDialogTitle.value = "流程定义 - 流程设计";
  editDialogVisible.value = true;
};

const handlePublish = (row) => {
  flowDefinitionApi.publish(row.id).then(() => {
    loadTableData();
  });
};

const handleUnpublish = (row) => {
  flowDefinitionApi.unpublish(row.id).then(() => {
    loadTableData();
  });
};

const handleCopy = (row) => {
  flowDefinitionApi.copy(row.id).then(() => {
    loadTableData();
  });
};

/** selected rows */
const selected = ref([]);

const handleDelete = (ids) => {
  flowDefinitionApi.remove(ids).then(() => {
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
    <el-form-item prop="flowName" label="流程名称">
      <el-input v-model="queryParams.flowName" placeholder="" clearable style="width: 140px" />
    </el-form-item>
    <el-form-item prop="flowCode" label="流程编码">
      <el-input v-model="queryParams.flowCode" placeholder="" clearable style="width: 140px" />
    </el-form-item>
    <el-form-item prop="isPublish" label="是否发布">
      <VtSelectDict
        v-model="queryParams.isPublish"
        :code="'vt_warmflow_publish'"
        :style="'width: 120px;'"
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
    <!-- 左侧 -->
    <el-col :span="1.5" v-permission="'workflow:flowDefinition:create'">
      <el-button type="primary" @click="handleAdd">
        <template #icon>
          <el-icon>
            <Icon icon="ep:plus"></Icon>
          </el-icon>
        </template>
        新增
      </el-button>
    </el-col>
    <el-col :span="1.5" v-show="selected.length" v-permission="'workflow:flowDefinition:remove'">
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
    <VtTableBarRight :tableRef="tableRef" :columns="columns" @update-size="(val) => (size = val)" />
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
        v-if="columns.flowName.visible"
        prop="flowName"
        label="流程名称"
        min-width="100"
      />
      <el-table-column
        v-if="columns.flowCode.visible"
        prop="flowCode"
        label="流程编码"
        width="180"
      />
      <el-table-column
        v-if="columns.modelValue.visible"
        prop="modelValue"
        label="设计器模型"
        width="110"
        align="center"
      >
        <template #default="{ row }">
          <VtTagDict
            :code="'vt_warmflow_designer_model'"
            :value="row.modelValue"
            :size="size"
          ></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.categoryName.visible"
        prop="categoryName"
        label="流程分类"
        width="100"
      />
      <el-table-column
        v-if="columns.version.visible"
        prop="version"
        label="版本"
        width="80"
        align="center"
      />
      <el-table-column
        v-if="columns.isPublish.visible"
        prop="isPublish"
        label="是否发布"
        width="100"
        align="center"
      >
      </el-table-column>
      <el-table-column
        v-if="columns.formCustom.visible"
        prop="formCustom"
        label="表单是否自定义"
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
        v-if="columns.activityStatus.visible"
        prop="activityStatus"
        label="激活状态"
        width="100"
        align="center"
      >
      </el-table-column>
      <el-table-column
        v-if="columns.listenerType.visible"
        prop="listenerType"
        label="监听器类型"
        min-width="100"
      />
      <el-table-column
        v-if="columns.listenerPath.visible"
        prop="listenerPath"
        label="监听器路径"
        min-width="100"
      />
      <el-table-column v-if="columns.ext.visible" prop="ext" label="扩展字段" min-width="100" />
      <el-table-column
        v-if="columns.delFlag.visible"
        prop="delFlag"
        label="删除标记"
        min-width="100"
      />
      <el-table-column
        v-if="columns.tenantId.visible"
        prop="tenantId"
        label="租户ID"
        min-width="100"
      />
      <el-table-column
        v-if="columns.createByName.visible"
        prop="createByName"
        label="创建者"
        align="center"
        width="90"
      />
      <el-table-column
        v-if="columns.createTime.visible"
        prop="createTime"
        label="创建时间"
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
      <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="240">
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
            <el-tooltip content="流程设计" placement="top" v-if="scope.row.isPublish === 0">
              <el-button
                type="primary"
                text
                :size="size"
                style="margin-left: 0px"
                @click="handleDesign(scope.row)"
                v-permission="'workflow:flowDefinition:update'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ri:flow-chart"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="流程发布" placement="top" v-if="scope.row.isPublish === 0">
              <el-button
                type="primary"
                text
                :size="size"
                style="margin-left: 0px"
                @click="handlePublish(scope.row)"
                v-permission="'workflow:flowDefinition:publish'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:promotion"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>

            <el-tooltip content="编辑" placement="top" v-if="scope.row.isPublish === 0">
              <el-button
                type="primary"
                text
                :size="size"
                style="margin-left: 0px"
                @click="handleEdit(scope.row)"
                v-permission="'workflow:flowDefinition:update'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:edit"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top" v-if="scope.row.isPublish === 0">
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
                      v-permission="'workflow:flowDefinition:remove'"
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
            <el-tooltip content="流程复制" placement="top" v-if="scope.row.isPublish === 1">
              <el-button
                type="primary"
                text
                :size="size"
                style="margin-left: 0px"
                @click="handleCopy(scope.row)"
                v-permission="'workflow:flowDefinition:copy'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:copy-document"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="取消发布" placement="top" v-if="scope.row.isPublish === 1">
              <el-button
                type="primary"
                text
                :size="size"
                style="margin-left: 0px"
                @click="handleUnpublish(scope.row)"
                v-permission="'workflow:flowDefinition:unpublish'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ri:arrow-go-back-fill"></Icon>
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
  </div>

  <VtDialogWorkflowDesigner
    v-model:visible="editDialogVisible"
    :id="editDataId"
    :title="editDialogTitle"
    :only-design-show="editOnlyDesignShow"
    :disabled="editDisabled"
    @refresh="loadTableData"
  ></VtDialogWorkflowDesigner>
</template>

<style scoped></style>
