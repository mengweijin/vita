<script setup>
import { ref, onMounted } from "vue";
import { getMenuList } from "@/api/system/menu.js";
import { toArrayTree } from 'xe-utils';
import { Icon } from "@iconify/vue";

const menuList = ref([]);

const loading = ref(true);

onMounted(() => {
  getMenuList().then((res) => {
    menuList.value = toArrayTree(res, { sortKey: 'seq' });
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
            <el-button type="primary" plain icon="Plus">新增
            </el-button>
          </el-col>



          <el-col :span="1.5" style="margin-left: auto;">
            <el-button type="info" plain icon="Sort">刷新</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="info" plain icon="Sort">展开/折叠</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="info" plain icon="Sort">筛选列</el-button>
          </el-col>
          <!-- 其它 -->
        </el-row>
      </template>

      <el-table :data="menuList" v-loading="loading" row-key="id" max-height="300" stripe border show-overflow-tooltip
        highlight-current-row>
        <el-table-column type="index" label="编号" width="60" fixed="left" v-if="false" />
        <el-table-column prop="id" label="ID" min-width="180" v-if="false" />
        <el-table-column prop="title" label="菜单标题" min-width="260" fixed="left" />
        <el-table-column prop="icon" label="图标" min-width="80" align="center">
          <template #default="{ row }">
            <Icon :icon="row.icon" width="24" height="24" v-if="row.icon" />
          </template>
        </el-table-column>
        <el-table-column prop="type" label="菜单类型" min-width="120" align="center" />
        <el-table-column prop="seq" label="排序" min-width="80" sortable />
        <el-table-column prop="permission" label="权限字符" min-width="260" />
        <el-table-column prop="component" label="组件" min-width="260">
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
        <el-table-column prop="createByName" label="创建者" align="center" min-width="100" />
        <el-table-column prop="createTime" label="创建时间" align="center" min-width="180" />
        <el-table-column prop="updateByName" label="更新者" align="center" min-width="100" />
        <el-table-column prop="updateTime" label="更新时间" align="center" min-width="180" />
        <el-table-column label="操作" fixed="right" min-width="160">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.$index, scope.row)">
              编辑
            </el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<style scoped></style>
