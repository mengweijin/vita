<script setup>
import { ref, reactive, onMounted } from "vue";
import { getMenuList } from "@/api/system/menu.js";
import { toArrayTree } from 'xe-utils';
import { Icon } from "@iconify/vue";

const dataList = ref([]);

const loading = ref(true);

const columns = reactive({
  index: false,
  id: false,
  title: true,
  icon: true,
  type: true,
  seq: true,
  permission: true,
  component: true,
  routeName: false,
  routePath: false,
  createByName: false,
  createTime: false,
  updateByName: false,
  updateTime: false,
  operations: true,
});

onMounted(() => {
  getMenuList().then((res) => {
    dataList.value = toArrayTree(res, { sortKey: 'seq' });
    loading.value = false;
  });
});

</script>

<template>
  <div>
    <el-card shadow="hover">
      <template #header>
        <el-row :gutter="10">
          <el-col :span="1.5">
            <el-button type="primary">
              <template #icon>
                <Icon icon="ep:plus" width="24" height="24"></Icon>
              </template>
              新增
            </el-button>
          </el-col>

          <el-col :span="1.5" style="margin-left: auto;">
            <el-tooltip content="显示搜索/隐藏搜索" placement="top">
              <el-button text circle style="font-size: 22px;">
                <template #icon>
                  <!-- <Icon icon="ep:search" width="24" height="24"></Icon> -->
                  <Icon icon="mynaui:search-x" width="24" height="24" />
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="刷新" placement="top">
              <el-button text circle style="font-size: 22px;">
                <template #icon>
                  <Icon icon="ep:refresh" width="24" height="24"></Icon>
                </template>
              </el-button>
            </el-tooltip>

            <el-tooltip content="列设置" placement="top">
              <el-button text circle style="font-size: 22px;">
                <template #icon>
                  <Icon icon="ep:menu" width="24" height="24"></Icon>
                </template>
              </el-button>
            </el-tooltip>
          </el-col>
        </el-row>
      </template>

      <el-table ref="dataTable" v-loading="loading" :data="dataList" row-key="id" stripe border show-overflow-tooltip
        highlight-current-row>
        <el-table-column v-if="columns.index" type="index" label="编号" width="60" fixed="left" />
        <el-table-column v-if="columns.id" prop="id" label="ID" min-width="180" />
        <el-table-column v-if="columns.title" prop="title" label="菜单标题" min-width="260" fixed="left" />
        <el-table-column v-if="columns.icon" prop="icon" label="图标" min-width="80" align="center">
          <template #default="{ row }">
            <Icon :icon="row.icon" width="24" height="24" v-if="row.icon" />
          </template>
        </el-table-column>
        <el-table-column v-if="columns.type" prop="type" label="菜单类型" min-width="120" align="center" />
        <el-table-column v-if="columns.seq" prop="seq" label="排序" min-width="80" sortable />
        <el-table-column v-if="columns.permission" prop="permission" label="权限字符" min-width="260" />
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
        <el-table-column v-if="columns.operations" label="操作" fixed="right" min-width="240">
          <template #default="scope">
            <div>
              <el-button type="primary" text size="small" @click="handleAdd(scope.$index, scope.row)">
                <template #icon>
                  <Icon icon="ep:plus" width="24" height="24"></Icon>
                </template>
                新增
              </el-button>
              <el-button type="primary" text size="small" @click="handleEdit(scope.$index, scope.row)">
                <template #icon>
                  <Icon icon="ep:edit" width="24" height="24"></Icon>
                </template>
                编辑
              </el-button>
              <el-button type="danger" text size="small" @click="handleDelete(scope.$index, scope.row)">
                <template #icon>
                  <Icon icon="ep:delete" width="24" height="24"></Icon>
                </template>
                删除
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<style scoped>
:deep(.el-table__body-wrapper) {
  overflow-y: hidden !important;
}
</style>
