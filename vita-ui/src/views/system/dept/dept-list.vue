<script setup>
import { deptApi } from "@/api/system/dept-api";
import { toArrayTree } from 'xe-utils';
import { columns } from './dept-hook.js'

const tableRef = ref(null);

const loading = ref(true);

const tableDataList = ref([]);

const treeProps = reactive({
  // 父子节点默认联动
  checkStrictly: false,
})

/** selected rows */
const selected = ref([]);

const handleSelectionChange = (val) => {
  selected.value = val;
}

const refreshTableData = () => {
  loading.value = true;
  deptApi.list().then((res) => {
    tableDataList.value = toArrayTree(res, { sortKey: 'seq' });
    loading.value = false;
  });
};

onMounted(() => {
  refreshTableData();
});

</script>

<template>
  <!-- 查询表单 -->
  <el-form :inline="true" class="vt-search-container">
    <el-form-item label="名称">
      <el-input placeholder="名称" clearable />
    </el-form-item>
    <el-form-item>
      <el-button type="primary">搜索</el-button>
    </el-form-item>
  </el-form>

  <el-divider style="margin: 0px;" />

  <!-- 表格头-->
  <el-row :gutter="10" style="padding: 15px 0px">
    <!-- 左侧 -->
    <el-col :span="1.5">
      <el-button type="primary">
        <template #icon>
          <Icon icon="ep:plus" width="24" height="24"></Icon>
        </template>
        新增
      </el-button>
    </el-col>
    <el-col :span="1.5" v-if="false">
      <el-button type="danger" v-show="selected.length">
        <template #icon>
          <Icon icon="ep:delete" width="24" height="24"></Icon>
        </template>
        批量删除
      </el-button>
    </el-col>
    <el-col :span="1.5" v-if="false">
      <el-checkbox v-model="treeProps.checkStrictly">
        取消父子联动
      </el-checkbox>
    </el-col>
    <!-- 右侧 -->
    <VtTableBarRight :columns="columns"></VtTableBarRight>
  </el-row>

  <!-- 表格 -->
  <div class="vt-table-container">
    <el-table ref="tableRef" v-loading="loading" :data="tableDataList" :tree-props="treeProps" row-key="id"
      height="100%" stripe border show-overflow-tooltip highlight-current-row default-expand-all
      @selection-change="handleSelectionChange">
      <el-table-column v-if="columns[0].visible" type="selection" width="55" />
      <el-table-column v-if="columns[1].visible" type="index" label="序号" width="60" fixed="left" />
      <el-table-column v-if="columns[2].visible" prop="id" label="ID" min-width="180" />
      <el-table-column v-if="columns[3].visible" prop="name" label="部门名称" min-width="260" fixed="left" />
      <el-table-column v-if="columns[4].visible" prop="disabled" label="状态" min-width="80" align="center">
        <template #default="{ row }">
          <VtDictTag :code="'vt_disabled'" :value="row.disabled"></VtDictTag>
        </template>
      </el-table-column>
      <el-table-column v-if="columns[5].visible" prop="seq" label="排序" min-width="80" sortable align="center" />
      <el-table-column v-if="columns[6].visible" prop="remark" label="备注" min-width="300" sortable />
      <el-table-column v-if="columns[7].visible" prop="createByName" label="创建者" align="center" min-width="100" />
      <el-table-column v-if="columns[8].visible" prop="createTime" label="创建时间" align="center" min-width="180" />
      <el-table-column v-if="columns[9].visible" prop="updateByName" label="更新者" align="center" min-width="100" />
      <el-table-column v-if="columns[10].visible" prop="updateTime" label="更新时间" align="center" min-width="180" />
      <el-table-column v-if="columns[11].visible" label="操作" align="center" fixed="right" min-width="180">
        <template #default="scope">
          <div>
            <el-tooltip content="新增" placement="top">
              <el-button type="primary" text @click="handleAdd(scope.$index, scope.row)">
                <template #icon>
                  <Icon icon="ep:plus" width="24" height="24"></Icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="编辑" placement="top">
              <el-button type="primary" text @click="handleEdit(scope.$index, scope.row)">
                <template #icon>
                  <Icon icon="ep:edit" width="24" height="24"></Icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button type="danger" text @click="handleDelete(scope.$index, scope.row)">
                <template #icon>
                  <Icon icon="ep:delete" width="24" height="24"></Icon>
                </template>
              </el-button>
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style scoped></style>
