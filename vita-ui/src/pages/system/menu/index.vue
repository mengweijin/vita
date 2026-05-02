<route lang="yaml">
meta:
  title: 菜单管理
  permission: system:menu:view
</route>

<script setup>
import { menuApi } from "@/api/system/menu-api.js";
import utils from "@/utils/utils.js";
import { useMenu } from "./hooks.js";
import MenuEdit from "./components/menu-edit.vue";
const { columns } = useMenu();

const loading = ref(true);

const size = ref("default");

/** table */
const tableRef = useTemplateRef("tableRef");

const treeProps = reactive({
  // 父子节点默认联动
  checkStrictly: false,
});

const tableData = ref([]);

/**
 * 不能初始化为 null，否则 resetFields() 不生效
 */
const queryParams = reactive({
  disabled: undefined,
  title: undefined,
  type: undefined,
});

const queryFormRef = useTemplateRef("queryFormRef");

const resetQueryForm = () => {
  queryFormRef.value.resetFields();
  loadTableData();
};

const loadTableData = () => {
  loading.value = true;
  menuApi.list(queryParams).then((res) => {
    tableData.value = utils.toArrayTree(res, { sortKey: "seq" });
    loading.value = false;
  });
};

// -----------------------------------------
const editDialogVisible = ref(false);
const editData = ref(null);

const handleAdd = (id) => {
  editData.value = null;
  if (id) {
    editData.value = {
      parentId: id,
      seq: 0,
      disabled: "N",
      type: "MENU",
    };
  }
  editDialogVisible.value = true;
};

const handleEdit = (row) => {
  // 使用展开运算符，避免数据污染
  editData.value = { ...row };
  editDialogVisible.value = true;
};

/** selected rows */
const selected = ref([]);

const handleDelete = (ids) => {
  menuApi.remove(ids).then(() => {
    // 清空已选择
    selected.value = [];
    loadTableData();
  });
};

const handleBatchDelete = () => {
  const ids = selected.value.map((item) => item.id).join();
  handleDelete(ids);
};

onMounted(() => {
  loadTableData();
});
</script>

<template>
  <!-- 查询表单 -->
  <el-form ref="queryFormRef" :model="queryParams" :inline="true" @submit.prevent="loadTableData">
    <el-form-item prop="title" label="名称">
      <el-input v-model="queryParams.title" placeholder="" clearable />
    </el-form-item>
    <el-form-item prop="type" label="菜单类型">
      <VtSelectDict v-model="queryParams.type" :code="'vt_menu_type'"></VtSelectDict>
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

  <el-divider style="margin: 0px" />

  <!-- 表格头-->
  <el-row :gutter="10" style="padding: 15px 0px">
    <!-- 左侧 -->
    <el-col :span="1.5" v-permission="'system:menu:create'">
      <el-button type="primary" @click="handleAdd(null)">
        <template #icon>
          <el-icon>
            <Icon icon="ep:plus"></Icon>
          </el-icon>
        </template>
        新增
      </el-button>
    </el-col>
    <el-col :span="1.5" v-if="false" v-show="selected.length" v-permission="'system:menu:remove'">
      <el-popconfirm
        placement="right"
        width="400"
        :title="`确定全部删除已选择的【${selected.map((i) => i.title).join()}】吗？`"
        confirm-button-text="确定"
        cancel-button-text="取消"
        @confirm="handleBatchDelete"
      >
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
      <el-checkbox v-model="treeProps.checkStrictly"> 取消父子联动 </el-checkbox>
    </el-col>
    <!-- 右侧 -->
    <VtTableBarRight :tableRef="tableRef" :columns="columns" @update-size="(val) => (size = val)" />
  </el-row>

  <!-- 表格 -->
  <div class="vt-table">
    <el-table
      ref="tableRef"
      v-loading="loading"
      :data="tableData"
      :tree-props="treeProps"
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
      <el-table-column v-if="columns.id.visible" prop="id" label="ID" min-width="180" />
      <el-table-column
        v-if="columns.title.visible"
        prop="title"
        label="菜单标题"
        fixed="left"
        min-width="200"
      />
      <el-table-column
        v-if="columns.icon.visible"
        prop="icon"
        label="图标"
        min-width="80"
        align="center"
      >
        <template #default="{ row }">
          <Icon :icon="row.icon" width="24" height="24" v-if="row.icon" />
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.type.visible"
        prop="type"
        label="菜单类型"
        min-width="120"
        align="center"
      >
        <template #default="{ row }">
          <VtTagDict :code="'vt_menu_type'" :value="row.type" :size="size"></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.disabled.visible"
        prop="disabled"
        label="状态"
        min-width="80"
        align="center"
      >
        <template #default="{ row }">
          <VtTagDict :code="'vt_disabled'" :value="row.disabled" :size="size"></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.seq.visible"
        prop="seq"
        label="排序"
        min-width="80"
        sortable
        align="center"
      />
      <el-table-column
        v-if="columns.permission.visible"
        prop="permission"
        label="权限字符"
        min-width="200"
      />
      <el-table-column v-if="columns.url.visible" prop="url" label="路由路径/URL" min-width="200" />
      <el-table-column
        v-if="columns.createByName.visible"
        prop="createByName"
        label="创建者"
        align="center"
        width="90"
      />
      <el-table-column
        v-if="columns.createTime.visible"
        prop="createTime"
        label="创建时间"
        align="center"
        width="160"
      />
      <el-table-column
        v-if="columns.updateByName.visible"
        prop="updateByName"
        label="更新者"
        align="center"
        width="90"
      />
      <el-table-column
        v-if="columns.updateTime.visible"
        prop="updateTime"
        label="更新时间"
        align="center"
        width="160"
      />
      <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" min-width="150">
        <template #default="scope">
          <div>
            <el-tooltip content="新增" placement="top">
              <el-button
                type="primary"
                text
                :size="size"
                @click="handleAdd(scope.row.id)"
                v-permission="'system:menu:create'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:plus"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="编辑" placement="top">
              <el-button
                type="primary"
                text
                :size="size"
                style="margin-left: 0px"
                @click="handleEdit(scope.row)"
                v-permission="'system:menu:update'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:edit"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top" v-if="scope.row.children?.length <= 0">
              <div style="display: inline-block">
                <el-popconfirm
                  placement="left"
                  width="400"
                  :title="`确定删除【${scope.row.title}】吗？`"
                  confirm-button-text="确定"
                  cancel-button-text="取消"
                  @confirm="handleDelete(scope.row.id)"
                >
                  <template #reference>
                    <el-button type="danger" text :size="size" v-permission="'system:menu:remove'">
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

  <MenuEdit
    v-model:visible="editDialogVisible"
    :data="editData"
    @refresh="loadTableData"
  ></MenuEdit>
</template>

<style scoped>
.vt-table {
  /* 分页组件：42px; */
  height: calc(var(--vt-table-height) + 42px);
}
</style>
