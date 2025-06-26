<script setup>
import { deptApi } from '@/api/system/dept-api';
import { userApi } from "@/api/system/user-api";
import { columns } from './user-hook.js';
import UserEdit from './user-edit.vue';
import VtTableBarRight from "@/components/modules/vt-table-bar-right.vue";
import VtDictTag from "@/components/modules/vt-dict-tag.vue";
import VtDictSelect from "@/components/modules/vt-dict-select.vue";
import { toArrayTree } from 'xe-utils';

const loading = ref(false);

const treeRef = ref(null);

const treeProps = reactive({
  children: 'children',
  label: 'name',
  disabled: (data, node) => data.disabled === 'Y',
})

const treeData = ref([]);

const loadTreeData = () => {
  deptApi.list({ disabled: 'N' }).then((res) => {
    // 转为树状
    treeData.value = toArrayTree(res, { sortKey: 'seq' });
  });
};

const handleTreeNodeClick = (data, node) => {
  queryParams.deptId = data.id;
  loadTableData();
};

const size = ref('default');

const tableRef = ref({});

const tableData = ref([]);

const queryParams = reactive({
  keywords: undefined,
  deptId: undefined,
  disabled: undefined,
  current: 1,
  size: 10,
  total: 0,
});

const queryFormRef = ref(null);

const resetQueryForm = () => {
  queryFormRef.value.resetFields();
  queryParams.deptId = null;
  // 清除选中状态及背景颜色
  treeRef.value.setCurrentKey(null);
  loadTableData();
};

const loadTableData = () => {
  loading.value = true;
  userApi.page(queryParams).then((res) => {
    tableData.value = res.records;
    queryParams.total = res.total;
    loading.value = false;
  });
};

const userEditRef = ref(null);

const handleAdd = () => {
  userEditRef.value.data = {};
  userEditRef.value.visible = true;
}

const handleEdit = (row) => {
  // 使用展开运算符，避免数据污染
  userEditRef.value.data = { ...row };
  userEditRef.value.visible = true;
}

/** selected rows */
const selected = ref([]);

const handleDelete = (ids) => {
  userApi.remove(ids).then(() => {
    // 清空已选择
    selected.value = [];
    loadTableData();
  });
}

const handlePageChange = (currentPage, pageSize) => {
  queryParams.current = currentPage;
  queryParams.size = pageSize;
  loadTableData();
}


onMounted(() => {
  loadTreeData();
  loadTableData();
});
</script>

<template>
  <el-container v-loading="loading" style="padding: 10px 0px;">
    <el-aside width="200px">
      <el-scrollbar max-height="100%">
        <el-tree ref="treeRef" :node-key="'id'" :props="treeProps" :data="treeData" default-expand-all highlight-current
          :expand-on-click-node="false" @node-click="handleTreeNodeClick" class="vt-user-dept-tree" />
      </el-scrollbar>
    </el-aside>
    <el-main>
      <!-- 查询表单 -->
      <el-form ref="queryFormRef" :model="queryParams" :inline="true" @submit.prevent="loadTableData"
        class="vt-search-container">
        <el-form-item prop="keywords" label="关键字">
          <el-input v-model="queryParams.keywords" placeholder="用户名、昵称" clearable />
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
        <!-- 右侧 -->
        <VtTableBarRight :tableRef="tableRef" :columns="columns" @update-size="(val) => size = val" />
      </el-row>

      <!-- 表格 -->
      <div class="vt-table-container">
        <el-table ref="tableRef" v-loading="loading" :data="tableData" :size="size" row-key="id" height="100%" stripe
          border show-overflow-tooltip highlight-current-row @selection-change="(val) => selected = val">
          <el-table-column v-if="columns.selection.visible" type="selection" width="55" />
          <el-table-column v-if="columns.index.visible" type="index" label="序号" width="60" />
          <el-table-column v-if="columns.id.visible" prop="id" label="ID" min-width="180" />
          <el-table-column v-if="columns.deptId.visible" prop="deptId" label="部门 ID" min-width="180" />
          <el-table-column v-if="columns.avatar.visible" prop="avatar" label="头像" min-width="60" align="center">
            <template #default="{ row }">
              <el-avatar :size="'small'" :src="row.avatar" v-if="row.avatar" />
              <el-avatar :size="size" src="/avatar.jpg" v-else />
            </template>
          </el-table-column>
          <el-table-column v-if="columns.username.visible" prop="username" label="用户名" min-width="100" />
          <el-table-column v-if="columns.nickname.visible" prop="nickname" label="用户昵称" min-width="100" />
          <el-table-column v-if="columns.deptName.visible" prop="deptName" label="部门名称" min-width="100" />
          <el-table-column v-if="columns.gender.visible" prop="gender" label="性别" min-width="80" align="center">
            <template #default="{ row }">
              <VtDictTag :code="'vt_user_gender'" :value="row.gender" :size="size"></VtDictTag>
            </template>
          </el-table-column>
          <el-table-column v-if="columns.mobile.visible" prop="mobile" label="移动电话" min-width="120" />
          <el-table-column v-if="columns.email.visible" prop="email" label="邮箱" min-width="140" />
          <el-table-column v-if="columns.disabled.visible" prop="disabled" label="状态" min-width="80" align="center">
            <template #default="{ row }">
              <VtDictTag :code="'vt_disabled'" :value="row.disabled" :size="size"></VtDictTag>
            </template>
          </el-table-column>
          <el-table-column v-if="columns.remark.visible" prop="remark" label="备注" min-width="260" />
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

      <UserEdit ref="userEditRef" @refresh-table="loadTableData"></UserEdit>
    </el-main>
  </el-container>

</template>

<style scoped>
.el-avatar--circle {
  vertical-align: middle;
}

.vt-user-dept-tree {
  border-right: 1px solid #dddddd;
  height: calc(100vh - var(--vt-header-height) - var(--vt-footer-height));
}

.vt-search-container {
  padding: 10px 0px 0px 0px;
}
</style>
