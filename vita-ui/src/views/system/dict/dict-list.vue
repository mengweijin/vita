<script setup>
import { dictTypeApi } from "@/api/system/dict-api";
import DictTypeEdit from './dict-type-edit.vue';
import DictDataTable from './dict-data-table.vue';

const loading = ref(true);

const size = ref('default');

const tableRef = ref({});

const tableData = ref([]);

const columns = reactive({
  selection: { label: '选择列', visible: false },
  index: { label: '序号列', visible: false },
  id: { label: 'ID', visible: false },
  name: { label: '字典名称', visible: true },
  code: { label: '字典编码', visible: true },
  remark: { label: '备注', visible: true },
  createByName: { label: '创建者', visible: false },
  createTime: { label: '创建时间', visible: false },
  updateByName: { label: '更新者', visible: true },
  updateTime: { label: '更新时间', visible: true },
  operation: { label: '操作', visible: true },
});

/**
 * 不能初始化为 null，否则 resetFields() 不生效
 */
const queryParams = reactive({
  keywords: undefined,
  current: 1,
  size: 10,
  total: 0,
});

const queryFormRef = ref(null);

const resetQueryForm = () => {
  queryFormRef.value.resetFields();
  loadTableData();
};

const loadTableData = () => {
  loading.value = true;
  dictTypeApi.page(queryParams).then((res) => {
    tableData.value = res.records;
    queryParams.total = res.total;
    loading.value = false;
  });
};

const dictTypeEditRef = ref(null);

const handleAdd = () => {
  dictTypeEditRef.value.data = {};
  dictTypeEditRef.value.visible = true;
}

const handleEdit = (row) => {
  // 使用展开运算符，避免数据污染
  dictTypeEditRef.value.data = { ...row };
  dictTypeEditRef.value.visible = true;
}

/** selected rows */
const selected = ref([]);

const handleDelete = (ids) => {
  dictTypeApi.remove(ids).then(() => {
    // 清空已选择
    selected.value = [];
    loadTableData();
  });
}

const handleBatchDelete = () => {
  let ids = selected.value.map(item => item.id).join();
  handleDelete(ids);
}

const handlePageChange = (currentPage, pageSize) => {
  queryParams.current = currentPage;
  queryParams.size = pageSize;
  loadTableData();
}

const dictDataTableRef = ref(null);

const openDictDataTableListDialog = (row) => {
  dictDataTableRef.value.dictType = row;
  dictDataTableRef.value.visible = true;
};

onMounted(() => {
  loadTableData();
});

</script>

<template>
  <!-- 查询表单 -->
  <el-form ref="queryFormRef" :model="queryParams" :inline="true" @submit.prevent="loadTableData"
    class="vt-search-container">
    <el-form-item prop="keywords" label="关键字">
      <el-input v-model="queryParams.keywords" placeholder="名称、编码" clearable />
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
    <el-col :span="1.5">
      <el-button type="primary" @click="handleAdd(null)">
        <template #icon>
          <el-icon>
            <Icon icon="ep:plus"></Icon>
          </el-icon>
        </template>
        新增
      </el-button>
    </el-col>
    <el-col :span="1.5" v-show="selected.length">
      <el-popconfirm placement="right" width="400" :title="`确定全部删除已选择的【${selected.map(i => i.name).join()}】吗？`"
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
  <div class="vt-table-container">
    <el-table ref="tableRef" v-loading="loading" :data="tableData" :size="size" row-key="id" height="100%" stripe border
      highlight-current-row @selection-change="(val) => selected = val">
      <el-table-column v-if="columns.selection.visible" type="selection" width="55" />
      <el-table-column v-if="columns.index.visible" type="index" label="序号" width="60" />
      <el-table-column v-if="columns.id.visible" prop="id" label="ID" min-width="180" />
      <el-table-column v-if="columns.name.visible" prop="name" label="字典名称" min-width="220" align="center" fixed="left"
        :tooltip-formatter="'false'">
        <template #default="{ row }">
          <a href="javascript:" @click="openDictDataTableListDialog(row)">{{ row.name }}</a>
        </template>
      </el-table-column>
      <el-table-column v-if="columns.code.visible" prop="code" label="字典编码" min-width="220" />
      <el-table-column v-if="columns.remark.visible" prop="remark" label="备注" min-width="200" />
      <el-table-column v-if="columns.createByName.visible" prop="createByName" label="创建者" align="center"
        min-width="100" />
      <el-table-column v-if="columns.createTime.visible" prop="createTime" label="创建时间" align="center"
        min-width="180" />
      <el-table-column v-if="columns.updateByName.visible" prop="updateByName" label="更新者" align="center"
        min-width="100" />
      <el-table-column v-if="columns.updateTime.visible" prop="updateTime" label="更新时间" align="center"
        min-width="180" />
      <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="120">
        <template #default="scope">
          <div>
            <el-tooltip content="新增" placement="top" v-if="false">
              <el-button type="primary" text :size="size" @click="handleAdd(scope.row.id)">
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:plus"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="编辑" placement="top">
              <el-button type="primary" text :size="size" style="margin-left: 0px;" @click="handleEdit(scope.row)">
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:edit"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <div style="display: inline-block;">
                <el-popconfirm placement="left" width="400" :title="`确定删除【${scope.row.name}】吗？`"
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

  <DictTypeEdit ref="dictTypeEditRef" @refresh-table="loadTableData"></DictTypeEdit>

  <DictDataTable ref="dictDataTableRef"></DictDataTable>
</template>

<style scoped></style>
