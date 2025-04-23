<script setup>
import { menuApi } from "@/api/system/menu-api";
import { toArrayTree } from 'xe-utils';
import VtTableBarRight from "@/components/modules/vt-table-bar-right.vue";
import VtDictTag from "@/components/modules/vt-dict-tag.vue";

const tableRef = ref(null);

const loading = ref(true);

const tableDataList = ref([]);

const treeProps = reactive({
  // 父子节点默认联动
  checkStrictly: false,
})

const columns = reactive({
  index: false,
  id: false,
  title: true,
  icon: true,
  type: true,
  disabled: true,
  seq: true,
  permission: true,
  component: true,
  routeName: false,
  routePath: false,
  createByName: true,
  createTime: true,
  updateByName: false,
  updateTime: false,
  operations: true,
});

/** selected rows */
const selected = ref([]);

const handleSelectionChange = (val) => {
  selected.value = val;
}

const refreshTableData = () => {
  loading.value = true;
  menuApi.list().then((res) => {
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
    <el-form-item label="菜单名称">
      <el-input placeholder="菜单名称" clearable />
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
    <el-col :span="1.5">
      <el-button type="danger" v-show="selected.length">
        <template #icon>
          <Icon icon="ep:delete" width="24" height="24"></Icon>
        </template>
        批量删除
      </el-button>
    </el-col>
    <el-col :span="1.5">
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
      height="100%" stripe border show-overflow-tooltip highlight-current-row @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column v-if="columns.index" type="index" label="编号" width="60" fixed="left" />
      <el-table-column v-if="columns.id" prop="id" label="ID" min-width="180" />
      <el-table-column v-if="columns.title" prop="title" label="菜单标题" min-width="260" fixed="left" />
      <el-table-column v-if="columns.icon" prop="icon" label="图标" min-width="80" align="center">
        <template #default="{ row }">
          <Icon :icon="row.icon" width="24" height="24" v-if="row.icon" />
        </template>
      </el-table-column>
      <el-table-column v-if="columns.type" prop="type" label="菜单类型" min-width="120" align="center">
        <template #default="{ row }">
          <VtDictTag :code="'vt_menu_type'" :value="row.type"></VtDictTag>
        </template>
      </el-table-column>
      <el-table-column v-if="columns.disabled" prop="disabled" label="状态" min-width="80" align="center">
        <template #default="{ row }">
          <VtDictTag :code="'vt_disabled'" :value="row.disabled"></VtDictTag>
        </template>
      </el-table-column>
      <el-table-column v-if="columns.seq" prop="seq" label="排序" min-width="80" sortable />
      <el-table-column v-if="columns.permission" prop="permission" label="权限字符" min-width="200" />
      <el-table-column v-if="columns.component" prop="component" label="组件" min-width="260">
        <template #default="scope">
          <el-popover effect="light" trigger="hover" placement="top" width="auto">
            <template #default>
              <div>组件路径: {{ scope.row.component }}</div>
              <div>路由名称: {{ scope.row.routeName }}</div>
              <div>路由路径: {{ scope.row.routePath }}</div>
            </template>
            <template #reference>
              <el-tag v-if="scope.row.component">{{ scope.row.component }}</el-tag>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column v-if="columns.routeName" prop="routeName" label="路由名称" min-width="180" />
      <el-table-column v-if="columns.routePath" prop="routePath" label="路由路径" min-width="200" />
      <el-table-column v-if="columns.createByName" prop="createByName" label="创建者" align="center" min-width="100" />
      <el-table-column v-if="columns.createTime" prop="createTime" label="创建时间" align="center" min-width="180" />
      <el-table-column v-if="columns.updateByName" prop="updateByName" label="更新者" align="center" min-width="100" />
      <el-table-column v-if="columns.updateTime" prop="updateTime" label="更新时间" align="center" min-width="180" />
      <el-table-column v-if="columns.operations" label="操作" fixed="right" min-width="180">
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
