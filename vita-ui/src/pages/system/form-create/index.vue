<route lang="yaml">
meta:
  title: 动态表单
  permission: system:formCreate:view
</route>

<script setup>
import { formCreateApi } from "@/api/system/form-create-api.js";
import FormCreateEdit from "./components/form-create-edit.vue";
import { useFormCreate } from "./hooks.js";
const { columns } = useFormCreate();

const loading = ref(true);

const size = ref("default");

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

/**
 * 不能初始化为 null，否则 resetFields() 不生效
 */
const queryParams = reactive({
  name: undefined,
  code: undefined,
  rules: undefined,
  options: undefined,
  remark: undefined,
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
  formCreateApi
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
  formCreateApi.remove(ids).then(() => {
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

const formCreateDesignerDialogVisible = ref(false);
const formCreateDesignerId = ref(null);
const openDesignerDialog = (row) => {
  formCreateDesignerId.value = row.id;
  formCreateDesignerDialogVisible.value = true;
};

const previewDialogVisible = ref(false);
const previewDialogFormCode = ref(null);
const openPreviewDialog = (row) => {
  previewDialogFormCode.value = row.code;
  previewDialogVisible.value = true;
};

onMounted(() => {
  loadTableData();
});
</script>

<template>
  <!-- 查询表单 -->
  <el-form ref="queryFormRef" :model="queryParams" :inline="true" @submit.prevent="loadTableData">
    <el-form-item prop="name" label="名称">
      <el-input v-model="queryParams.name" placeholder="" clearable />
    </el-form-item>
    <el-form-item prop="code" label="编码">
      <el-input v-model="queryParams.code" placeholder="" clearable />
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
    <el-col :span="1.5" v-permission="'system:formCreate:create'">
      <el-button type="primary" @click="handleAdd">
        <template #icon>
          <el-icon>
            <Icon icon="ep:plus"></Icon>
          </el-icon>
        </template>
        新增
      </el-button>
    </el-col>
    <el-col :span="1.5" v-show="selected.length" v-permission="'system:formCreate:remove'">
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
      <el-table-column v-if="columns.name.visible" prop="name" label="表单名称" min-width="180" />
      <el-table-column v-if="columns.code.visible" prop="code" label="表单编码" min-width="160" />
      <el-table-column v-if="columns.rules.visible" prop="rules" label="表单规则" min-width="100" />
      <el-table-column
        v-if="columns.options.visible"
        prop="options"
        label="表单配置"
        min-width="100"
      />
      <el-table-column v-if="columns.remark.visible" prop="remark" label="备注" min-width="100" />
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
      <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="200">
        <template #default="scope">
          <div>
            <el-tooltip content="新增" placement="top" v-if="false">
              <el-button
                type="primary"
                text
                :size="size"
                @click="handleAdd(scope.row.id)"
                v-permission="'system:formCreate:create'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:plus"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>

            <el-tooltip content="表单预览" placement="top">
              <el-button
                type="primary"
                text
                :size="size"
                style="margin-left: 0px"
                @click="openPreviewDialog(scope.row)"
                v-permission="'system:formCreate:update'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:view"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="表单设计" placement="top">
              <el-button
                type="primary"
                text
                :size="size"
                style="margin-left: 0px"
                @click="openDesignerDialog(scope.row)"
                v-permission="'system:formCreate:update'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ri:input-field"></Icon>
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
                v-permission="'system:formCreate:update'"
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
                      v-permission="'system:formCreate:remove'"
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

  <FormCreateEdit v-model="editDialogVisible" :data="editData" @refresh="loadTableData" />

  <VtDialogFormCreateDesigner
    v-model="formCreateDesignerDialogVisible"
    :id="formCreateDesignerId"
  />

  <el-dialog
    v-model="previewDialogVisible"
    :title="'表单预览'"
    destroy-on-close
    align-center
    width="60%"
  >
    <VtFormCreateLoader :form-code="previewDialogFormCode" />
  </el-dialog>
</template>

<style scoped></style>
