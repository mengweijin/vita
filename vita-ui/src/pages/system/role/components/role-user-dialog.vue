<script setup>
import { roleApi } from "@/api/system/role-api";
import { userApi } from "@/api/system/user-api";
import RoleUsersSelectDialog from "./role-users-select-dialog.vue";

const loading = ref(false);

const visible = ref(false);

const size = ref("default");

const data = ref({});

const queryParams = reactive({
  disabled: "N",
  keywords: undefined,
  pageCurrent: 1,
  pageSize: 10,
  pageTotal: 0,
});

const queryFormRef = useTemplateRef("queryFormRef");

const resetQueryForm = () => {
  queryFormRef.value.resetFields();
  loadTableData();
};

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

const loadTableData = () => {
  loading.value = true;
  userApi.pageByRole(data.value.id, queryParams).then((res) => {
    tableData.value = res.pageRecords;
    queryParams.pageTotal = res.pageTotal;
    loading.value = false;
  });
};

const handlePageChange = (currentPage, pageSize) => {
  queryParams.pageCurrent = currentPage;
  queryParams.pageSize = pageSize;
  loadTableData();
};

const selected = ref([]);

const roleUsersSelectDialogRef = useTemplateRef("roleUsersSelectDialogRef");

const handleRoleAddUser = () => {
  roleUsersSelectDialogRef.value.data = { ...data.value };
  roleUsersSelectDialogRef.value.visible = true;
};

const handleRoleRemoveUser = (userId) => {
  roleApi.removeByRoleIdInUserIds(data.value.id, userId).then(() => {
    loadTableData();
  });
};

const handleRoleRemoveUserBatch = () => {
  const userIds = selected.value.map((item) => item.id).join();
  roleApi.removeByRoleIdInUserIds(data.value.id, userIds).then(() => {
    loadTableData();
  });
};

const onOpened = () => {
  loadTableData();
};

const onClosed = () => {
  visible.value = false;
  data.value = {};
};

/** 暴露给父组件，父组件可通过 deptEditRef.value.visible = true; 来赋值 */
defineExpose({ data, visible });
</script>

<template>
  <el-dialog
    v-model="visible"
    :title="`角色【${data.name}】分配用户`"
    destroy-on-close
    :align-center="false"
    @opened="onOpened"
    @closed="onClosed"
    width="70%"
    style="max-height: 90%"
  >
    <!-- 查询表单 -->
    <el-form
      ref="queryFormRef"
      :model="queryParams"
      :inline="true"
      @submit.prevent="loadTableData"
      style="margin-top: 5px"
    >
      <el-form-item prop="keywords" label="关键字">
        <el-input v-model="queryParams.keywords" placeholder="用户名、昵称" clearable />
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
      <el-col :span="1.5">
        <el-button type="primary" @click="handleRoleAddUser">
          <template #icon>
            <el-icon>
              <Icon icon="ep:plus"></Icon>
            </el-icon>
          </template>
          角色新增用户
        </el-button>
      </el-col>
      <el-col :span="1.5" v-if="selected.length">
        <el-popconfirm
          placement="right"
          width="400"
          :title="`确定从角色中移除已选中的所有用户吗？`"
          confirm-button-text="确定"
          cancel-button-text="取消"
          @confirm="handleRoleRemoveUserBatch"
        >
          <template #reference>
            <el-button type="danger">
              <template #icon>
                <el-icon>
                  <Icon icon="ep:delete"></Icon>
                </el-icon>
              </template>
              从角色中移除
            </el-button>
          </template>
        </el-popconfirm>
      </el-col>
      <!-- 右侧 -->
      <VtTableBarRight
        :tableRef="tableRef"
        :shows="['refresh', 'size']"
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
        <el-table-column type="selection" width="55" />
        <el-table-column v-if="false" prop="id" label="ID" min-width="180" />
        <el-table-column prop="nickname" label="用户昵称" min-width="100" align="center" />
        <el-table-column prop="username" label="用户名" min-width="100" align="center" />
        <el-table-column prop="deptName" label="部门名称" min-width="100" />
        <el-table-column prop="gender" label="性别" min-width="80" align="center">
          <template #default="{ row }">
            <VtTagDict :code="'vt_user_gender'" :value="row.gender" :size="size"></VtTagDict>
          </template>
        </el-table-column>
        <el-table-column prop="mobile" label="移动电话" min-width="120" />
        <el-table-column prop="email" label="邮箱" min-width="140" />
        <el-table-column prop="disabled" label="状态" min-width="80" align="center">
          <template #default="{ row }">
            <VtTagDict :code="'vt_disabled'" :value="row.disabled" :size="size"></VtTagDict>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="80" align="center">
          <template #default="scope">
            <div>
              <el-tooltip content="从角色中移除" placement="top">
                <div style="display: inline-block">
                  <el-popconfirm
                    placement="left"
                    width="400"
                    :title="`确定从角色中移除用户【${scope.row.nickname}】吗？`"
                    confirm-button-text="确定"
                    cancel-button-text="取消"
                    @confirm="handleRoleRemoveUser(scope.row.id)"
                  >
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

      <el-pagination
        background
        layout="total, sizes, prev, pager, next, jumper"
        v-model:current-page="queryParams.pageCurrent"
        v-model:page-size="queryParams.pageSize"
        :total="queryParams.pageTotal"
        @change="handlePageChange"
      />
    </div>

    <RoleUsersSelectDialog
      ref="roleUsersSelectDialogRef"
      @refresh-table="loadTableData"
    ></RoleUsersSelectDialog>
  </el-dialog>
</template>

<style scoped>
.vt-table {
  /* 查询表单：70px; 表格头：63px；分页组件：50px */
  height: calc(100% - 70px - 63px - 50px);
}
</style>
