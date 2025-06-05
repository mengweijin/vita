<script setup>
import { categoryApi } from "@/api/system/category-api";
import { toArrayTree } from 'xe-utils';
import { columns } from './category-hook.js';
import CategoryEdit from './category-edit.vue';
import VtTableBarRight from "@/components/modules/vt-table-bar-right.vue";
import VtDictTag from "@/components/modules/vt-dict-tag.vue";
import VtDictSelect from "@/components/modules/vt-dict-select.vue";

const loading = ref(true);

const size = ref('default');

const tableRef = ref({});


const treeProps = reactive({
  // 父子节点默认联动
  checkStrictly: false,
})

const tableData = ref([]);

/**
 * 不能初始化为 null，否则 resetFields() 不生效
 */
const queryParams = reactive({
  keywords: undefined,
  disabled: undefined,
});

const queryFormRef = ref(null);

const resetQueryForm = () => {
  queryFormRef.value.resetFields();
  loadTableData();
};

const loadTableData = () => {
  loading.value = true;
  categoryApi.list(queryParams).then((res) => {
    tableData.value = toArrayTree(res, { sortKey: 'seq' });
    loading.value = false;
  });
};

const categoryEditRef = ref(null);

const handleAdd = (id) => {
  categoryEditRef.value.data = {
    parentId: id ?? undefined,
  };
  categoryEditRef.value.visible = true;
}

const handleEdit = (row) => {
  // 使用展开运算符，避免数据污染
  categoryEditRef.value.data = { ...row };
  categoryEditRef.value.visible = true;
}

/** selected rows */
const selected = ref([]);

const handleDelete = (ids) => {
  categoryApi.remove(ids).then(() => {
    // 清空已选择
    selected.value = [];
    loadTableData();
  });
}

const handleBatchDelete = () => {
  let ids = selected.value.map(item => item.id).join();
  handleDelete(ids);
}

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
    <el-form-item prop="disabled" label="状态">
      <VtDictSelect v-model="queryParams.disabled" :code="'vt_disabled'"></VtDictSelect>
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
    <el-col :span="1.5" v-if="false" v-show="selected.length">
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
    <el-col :span="1.5" v-if="false">
      <el-checkbox v-model="treeProps.checkStrictly">
        取消父子联动
      </el-checkbox>
    </el-col>
    <!-- 右侧 -->
    <VtTableBarRight :tableRef="tableRef" :columns="columns" @update-size="(val) => size = val" />
  </el-row>

  <!-- 表格 -->
  <div class="vt-table-container">
    <el-table ref="tableRef" v-loading="loading" :data="tableData" :tree-props="treeProps" :size="size" row-key="id"
      height="100%" stripe border show-overflow-tooltip highlight-current-row default-expand-all
      @selection-change="(val) => selected = val">
      <el-table-column v-if="columns.selection.visible" type="selection" width="55" />
      <el-table-column v-if="columns.index.visible" type="index" label="序号" width="60" />
      <el-table-column v-if="columns.id.visible" prop="id" label="ID" min-width="180" />
      <el-table-column v-if="columns.parentId.visible" prop="parentId" label="Parent ID" min-width="180" />
      <el-table-column v-if="columns.name.visible" prop="name" label="分类名称" min-width="230" fixed="left" />
      <el-table-column v-if="columns.code.visible" prop="code" label="分类编码" min-width="260" />
      <el-table-column v-if="columns.disabled.visible" prop="disabled" label="状态" min-width="80" align="center">
        <template #default="{ row }">
          <VtDictTag :code="'vt_disabled'" :value="row.disabled" :size="size"></VtDictTag>
        </template>
      </el-table-column>
      <el-table-column v-if="columns.seq.visible" prop="seq" label="排序" min-width="80" sortable align="center" />
      <el-table-column v-if="columns.remark.visible" prop="remark" label="备注" min-width="200" />
      <el-table-column v-if="columns.createByName.visible" prop="createByName" label="创建者" align="center"
        min-width="100" />
      <el-table-column v-if="columns.createTime.visible" prop="createTime" label="创建时间" align="center"
        min-width="180" />
      <el-table-column v-if="columns.updateByName.visible" prop="updateByName" label="更新者" align="center"
        min-width="100" />
      <el-table-column v-if="columns.updateTime.visible" prop="updateTime" label="更新时间" align="center"
        min-width="180" />
      <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="180">
        <template #default="scope">
          <div>
            <el-tooltip content="新增" placement="top">
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
            <el-tooltip content="删除" placement="top" v-if="scope.row.children?.length <= 0">
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
  </div>

  <CategoryEdit ref="categoryEditRef" @refresh-table="loadTableData"></CategoryEdit>
</template>

<style scoped>
.vt-table-container {
  flex: 1;
  /* 查询表单：70px; 表格头：63px； */
  height: calc(100vh - var(--vt-header-height) - var(--vt-footer-height) - 70px - 63px);
}
</style>
