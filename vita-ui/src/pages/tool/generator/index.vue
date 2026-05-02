<route lang="yaml">
meta:
  title: 代码生成器
  permission: tools:generator:view
</route>

<script setup>
import { generatorApi } from "@/api/tool/generator-api.js";
import { useGenerator } from "./hooks.js";
import GeneratorDialog from "./components/generator-dialog.vue";
const { columns } = useGenerator();

const loading = ref(true);

const size = ref("default");

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

/**
 * 不能初始化为 null，否则 resetFields() 不生效
 */
const queryParams = reactive({
  tableName: undefined,
});

const queryFormRef = useTemplateRef("queryFormRef");

const resetQueryForm = () => {
  queryFormRef.value.resetFields();
  loadTableData();
};

const loadTableData = () => {
  loading.value = true;
  generatorApi.listTableInfo(queryParams.tableName).then((res) => {
    tableData.value = res;
    loading.value = false;
  });
};

/** selected rows */
const selected = ref([]);

const generatorDialogRef = useTemplateRef("generatorDialogRef");

const handleOpenTemplateDialog = (row) => {
  generatorDialogRef.value.visible = true;
  generatorDialogRef.value.data = { ...row };
};

onMounted(() => {
  loadTableData();
});
</script>

<template>
  <!-- 查询表单 -->
  <el-form ref="queryFormRef" :model="queryParams" :inline="true" @submit.prevent="loadTableData">
    <el-form-item prop="tableName" label="表名称">
      <el-input v-model="queryParams.tableName" placeholder="" clearable />
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
      <el-table-column
        v-if="columns.index.visible"
        type="index"
        label="序号"
        width="60"
        fixed="left"
      />
      <el-table-column
        v-if="columns.name.visible"
        prop="name"
        label="表名称"
        width="330"
        fixed="left"
      >
        <template #default="{ row }">
          <a href="javascript:" @click="handleOpenTemplateDialog(row)">{{ row.name }}</a>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.havePrimaryKey.visible"
        prop="havePrimaryKey"
        label="是否有主键"
        width="100"
        align="center"
      >
        <template #default="{ row }">
          <VtTagDict
            :code="'vt_yes_no'"
            :value="row.havePrimaryKey === true ? 'Y' : 'N'"
            :size="size"
          ></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.fieldNames.visible"
        prop="fieldNames"
        label="字段名称"
        min-width="200"
      />
      <el-table-column
        v-if="columns.comment.visible"
        prop="comment"
        label="表注释"
        min-width="100"
      />
      <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="80">
        <template #default="scope">
          <div>
            <el-tooltip content="生成代码" placement="top">
              <el-button
                type="primary"
                text
                :size="size"
                @click="handleOpenTemplateDialog(scope.row)"
                v-permission="'tools:generator:view'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:pointer"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <GeneratorDialog ref="generatorDialogRef" />
</template>

<style scoped>
.vt-table {
  /* 分页组件：42px; */
  height: calc(var(--vt-table-height) + 42px);
}
</style>
