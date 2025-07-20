<script setup>
import { deptApi } from '@/api/system/dept-api';
import { userApi } from '@/api/system/user-api';
import { toArrayTree, find, remove } from 'xe-utils';

const props = defineProps({
  multiple: {
    type: Boolean,
    default: false,
  },
  size: {
    type: String,
    default: 'default'
  },
  style: {
    type: String,
    default: 'min-width: 200px; width: 100%;',
  },
});

const selectValue = defineModel({ type: Array, default: [] });

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


const loading = ref(false);

const tableRef = ref({});

const tableData = ref([]);

const queryParams = reactive({
  keywords: undefined,
  deptId: undefined,
  disabled: 'N',
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

const dropdownRef = ref(null);

const handlePageChange = (currentPage, pageSize) => {
  queryParams.current = currentPage;
  queryParams.size = pageSize;
  loadTableData();

  // 手动保持下拉展开
  setTimeout(() => {
    // 调用内部方法重新展开
    dropdownRef.value?.handleOpen();
  }, 10);
}

const selected = ref([]);

const handleTableRowClick = (row) => {
  if (!find(selected.value, item => item.id === row.id)) {
    selected.value.push(row);
  }
  if (!selectValue.value.includes(row.id)) {
    selectValue.value.push(row.id);
  }
};

const handleRemoveTag = (value) => {
  remove(selected.value, item => item.id === value);
  console.log(selected.value);
};

const getUserByUserId = (userId) => {
  return find(selected.value, item => item.id === userId);
};


onMounted(() => {
  loadTreeData();
  loadTableData();
});

</script>

<template>
  <el-dropdown ref="dropdownRef" trigger="click" placement="bottom" :size="props.size" :hide-on-click="false"
    :max-height="500" :style="props.style" :popper-append-to-body="false">
    <el-input-tag v-model="selectValue" draggable clearable :save-on-blur="false" :trigger="''" :tag-type="'primary'"
      :tag-effect="'dark'" :max="props.multiple ? 999 : 1" :size="props.size" placeholder="请选择用户"
      @remove-tag="handleRemoveTag">
      <template #tag="{ value }">
        <span>{{ getUserByUserId(value)?.nickname + '(' + getUserByUserId(value)?.username + ')' }}</span>
      </template>
    </el-input-tag>
    <template #dropdown>
      <el-container>
        <el-aside width="160px" style="padding: 15px 0px 0px 10px;">
          <el-scrollbar max-height="100%">
            <el-tree ref="treeRef" :node-key="'id'" :props="treeProps" :data="treeData" :size="'small'"
              default-expand-all highlight-current :expand-on-click-node="false" @node-click="handleTreeNodeClick" />
          </el-scrollbar>
        </el-aside>
        <el-main class="vt-user-table-border">
          <!-- 查询表单 -->
          <el-form ref="queryFormRef" :model="queryParams" :inline="true" :size="'small'"
            @submit.prevent="loadTableData" class="vt-search-container">
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

          <!-- 表格 -->
          <el-table ref="tableRef" v-loading="loading" :data="tableData" :size="'small'" row-key="id" height="191px"
            stripe border show-overflow-tooltip highlight-current-row @row-click="handleTableRowClick">
            <el-table-column prop="username" label="用户名" min-width="100" align="center" />
            <el-table-column prop="nickname" label="用户昵称" min-width="100" align="center" />
            <el-table-column prop="gender" label="性别" min-width="80" align="center">
              <template #default="{ row }">
                <VtTagDict :code="'vt_user_gender'" :value="row.gender" :size="'small'"></VtTagDict>
              </template>
            </el-table-column>
            <el-table-column prop="deptName" label="部门名称" min-width="100" />
          </el-table>

          <!--  @click.native.stop：阻止事件冒泡 -->
          <el-pagination background layout="total, sizes, prev, pager, next, jumper"
            v-model:current-page="queryParams.current" v-model:page-size="queryParams.size" :total="queryParams.total"
            @change="handlePageChange" :size="'small'" />
        </el-main>
      </el-container>

    </template>
  </el-dropdown>
</template>

<style scoped>
.vt-search-container {
  padding: 0px;
}

.vt-user-table-border {
  border-left: 1px solid #dddddd;
}
</style>
