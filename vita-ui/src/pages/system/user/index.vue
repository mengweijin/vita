<route lang="yaml">
meta:
  title: 用户管理
</route>

<script setup>
import { deptApi } from "@/api/system/dept-api";
import { userApi } from "@/api/system/user-api";
import utils from "@/utils/utils.js";
import { columns } from "./columns.js";
import UserEdit from "./components/user-edit.vue";
import UserResetPassword from "./components/user-reset-password.vue";
import UserSetRoles from "./components/user-set-roles.vue";

const loading = ref(false);

const treeRef = useTemplateRef("treeRef");

const treeProps = reactive({
  children: "children",
  disabled: (data, node) => data.disabled === "Y",
  label: "name",
});

const treeData = ref([]);

const loadTreeData = () => {
  deptApi.list({ disabled: "N" }).then((res) => {
    // 转为树状
    treeData.value = utils.toArrayTree(res, { sortKey: "seq" });
  });
};

const handleTreeNodeClick = (data, node) => {
  queryParams.deptId = data.id;
  loadTableData();
};

const size = ref("default");

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

const queryParams = reactive({
  deptId: undefined,
  disabled: undefined,
  nickname: undefined,
  pageCurrent: 1,
  pageSize: 10,
  pageTotal: 0,
  username: undefined,
});

const queryFormRef = useTemplateRef("queryFormRef");

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
    tableData.value = res.pageRecords;
    queryParams.pageTotal = res.pageTotal;
    loading.value = false;
  });
};

const userEditRef = useTemplateRef("userEditRef");

const handleAdd = () => {
  userEditRef.value.data = {};
  userEditRef.value.visible = true;
};

const handleEdit = (row) => {
  // 使用展开运算符，避免数据污染
  userEditRef.value.data = { ...row };
  userEditRef.value.visible = true;
};

/** selected rows */
const selected = ref([]);

const handleDelete = (ids) => {
  userApi.remove(ids).then(() => {
    // 清空已选择
    selected.value = [];
    loadTableData();
  });
};

const handlePageChange = (currentPage, pageSize) => {
  queryParams.pageCurrent = currentPage;
  queryParams.pageSize = pageSize;
  loadTableData();
};

const userSetRolesRef = useTemplateRef("userSetRolesRef");

const handleSetRoles = (row) => {
  userSetRolesRef.value.data = { ...row };
  userSetRolesRef.value.visible = true;
};

const userResetPasswordRef = useTemplateRef("userResetPasswordRef");

const handleResetPassword = (row) => {
  userResetPasswordRef.value.data = { ...row };
  userResetPasswordRef.value.visible = true;
};

onMounted(() => {
  loadTreeData();
  loadTableData();
});
</script>

<template>
  <el-container>
    <el-aside width="200px">
      <el-scrollbar>
        <el-tree ref="treeRef" :node-key="'id'" :props="treeProps" :data="treeData" default-expand-all highlight-current
          :expand-on-click-node="false" @node-click="handleTreeNodeClick" class="vt-tree vt-height" />
      </el-scrollbar>
    </el-aside>
    <el-main class="vt-main vt-height">
      <!-- 查询表单 -->
      <el-form ref="queryFormRef" :model="queryParams" :inline="true" @submit.prevent="loadTableData">
        <el-form-item prop="username" label="用户名">
          <el-input v-model="queryParams.username" placeholder="" clearable />
        </el-form-item>
        <el-form-item prop="nickname" label="昵称">
          <el-input v-model="queryParams.nickname" placeholder="" clearable />
        </el-form-item>
        <el-form-item prop="disabled" label="状态">
          <VtSelectDict v-model="queryParams.disabled" :code="'vt_disabled'"></VtSelectDict>
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
      <div class="vt-table">
        <el-table ref="tableRef" v-loading="loading" :data="tableData" :size="size" row-key="id" height="100%" stripe
          border show-overflow-tooltip highlight-current-row @selection-change="(val) => selected = val">
          <el-table-column v-if="columns.selection.visible" type="selection" width="55" />
          <el-table-column v-if="columns.index.visible" type="index" label="序号" width="60" />
          <el-table-column v-if="columns.id.visible" prop="id" label="ID" min-width="180" />
          <el-table-column v-if="columns.deptId.visible" prop="deptId" label="部门 ID" min-width="180" />
          <el-table-column v-if="columns.avatar.visible" prop="avatar" label="头像" min-width="70" align="center">
            <template #default="{ row }">
              <el-avatar :size="size" :src="row.avatar" v-if="row.avatar" />
              <el-avatar :size="size" src="/avatar.jpg" v-else />
            </template>
          </el-table-column>
          <el-table-column v-if="columns.username.visible" prop="username" label="用户名" min-width="100" align="center" />
          <el-table-column v-if="columns.nickname.visible" prop="nickname" label="用户昵称" min-width="100"
            align="center" />
          <el-table-column v-if="columns.deptName.visible" prop="deptName" label="部门名称" min-width="100" />
          <el-table-column v-if="columns.gender.visible" prop="gender" label="性别" min-width="80" align="center">
            <template #default="{ row }">
              <VtTagDict :code="'vt_user_gender'" :value="row.gender" :size="size"></VtTagDict>
            </template>
          </el-table-column>
          <el-table-column v-if="columns.mobile.visible" prop="mobile" label="移动电话" min-width="120" />
          <el-table-column v-if="columns.email.visible" prop="email" label="邮箱" min-width="140" />
          <el-table-column v-if="columns.disabled.visible" prop="disabled" label="状态" min-width="80" align="center">
            <template #default="{ row }">
              <VtTagDict :code="'vt_disabled'" :value="row.disabled" :size="size"></VtTagDict>
            </template>
          </el-table-column>
          <el-table-column v-if="columns.remark.visible" prop="remark" label="备注" min-width="260" />
          <el-table-column v-if="columns.createByName.visible" prop="createByName" label="创建者" align="center"
            width="100" />
          <el-table-column v-if="columns.createTime.visible" prop="createTime" label="创建时间" align="center"
            width="180" />
          <el-table-column v-if="columns.updateByName.visible" prop="updateByName" label="更新者" align="center"
            width="100" />
          <el-table-column v-if="columns.updateTime.visible" prop="updateTime" label="更新时间" align="center"
            width="180" />
          <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="210">
            <template #default="scope">
              <div>
                <el-tooltip content="设置角色" placement="top">
                  <el-button type="primary" text :size="size" style="margin-left: 0px;"
                    @click="handleSetRoles(scope.row)">
                    <template #icon>
                      <el-icon :size="size">
                        <Icon icon="ri:group-fill"></Icon>
                      </el-icon>
                    </template>
                  </el-button>
                </el-tooltip>
                <el-tooltip content="重置密码" placement="top">
                  <el-button type="primary" text :size="size" style="margin-left: 0px;"
                    @click="handleResetPassword(scope.row)">
                    <template #icon>
                      <el-icon :size="size">
                        <Icon icon="ep:key"></Icon>
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
                    <el-popconfirm placement="left" width="400" :title="`确定删除【${scope.row.username}】吗？`"
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
          v-model:current-page="queryParams.pageCurrent" v-model:page-size="queryParams.pageSize"
          :total="queryParams.pageTotal" @change="handlePageChange" />
      </div>

      <UserEdit ref="userEditRef" @refresh-table="loadTableData"></UserEdit>
      <UserSetRoles ref="userSetRolesRef"></UserSetRoles>
      <UserResetPassword ref="userResetPasswordRef"></UserResetPassword>
    </el-main>
  </el-container>

</template>

<style scoped>
.el-avatar--circle {
  vertical-align: middle;
}

.vt-tree {
  margin-right: 20px;
}

.vt-height {
  height: calc(var(--vt-tab-content-height)) !important;
}

.el-main {
  padding: 0px;
  overflow: hidden;
}
</style>
