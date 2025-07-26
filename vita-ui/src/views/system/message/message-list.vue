<script setup>
import { messageApi } from "@/api/system/message-api";
import { columns } from './message-hook.js';
import { useDictStore } from "@/store/dict-store.js";
const dictStore = useDictStore();

const loading = ref(true);

const treeRef = ref(null);

const treeProps = reactive({
  children: 'children',
  label: 'label',
  disabled: 'disabled',
})

const treeData = ref([]);

const loadTreeData = () => {
  let dictData = dictStore.get('vt_message_category');
  treeData.value = [{
    label: '消息分类',
    val: null,
    disabled: false,
    children: dictData,
  }];
};

const handleTreeNodeClick = (data, node) => {
  queryParams.category = data.val;
  loadTableData();
};

const size = ref('default');

const tableRef = ref({});

const tableData = ref([]);

/**
 * 不能初始化为 null，否则 resetFields() 不生效
 */
const queryParams = reactive({
  keywords: undefined,
  category: undefined,
  current: 1,
  size: 10,
  total: 0,
});

const queryFormRef = ref(null);

const resetQueryForm = () => {
  queryFormRef.value.resetFields();
  queryParams.category = null;
  // 清除选中状态及背景颜色
  treeRef.value.setCurrentKey(null);
  loadTableData();
};

const loadTableData = () => {
  loading.value = true;
  messageApi.page(queryParams).then((res) => {
    tableData.value = res.records;
    queryParams.total = res.total;
    loading.value = false;
  });
};

/** selected rows */
const selected = ref([]);


const handleSetViewed = (messageReceiverIds) => {
  messageApi.setViewed(messageReceiverIds).then(() => {
    // 清空已选择
    selected.value = [];
    loadTableData();
  });
}

const handleBatchSetViewed = () => {
  let messageReceiverIds = selected.value.map(item => item.id).join();
  handleSetViewed(messageReceiverIds);
}

const handleSetUnviewed = (messageReceiverIds) => {
  messageApi.setUnviewed(messageReceiverIds).then(() => {
    // 清空已选择
    selected.value = [];
    loadTableData();
  });
};

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
  <el-container style="padding: 10px 0px;">
    <el-aside width="140px">
      <el-scrollbar max-height="100%">
        <el-tree ref="treeRef" :node-key="'id'" :props="treeProps" :data="treeData" default-expand-all highlight-current
          :expand-on-click-node="false" @node-click="handleTreeNodeClick" class="vt-message-category-tree" />
      </el-scrollbar>
    </el-aside>
    <el-main>
      <!-- 查询表单 -->
      <el-form ref="queryFormRef" :model="queryParams" :inline="true" @submit.prevent="loadTableData"
        class="vt-search-container">
        <el-form-item prop="keywords" label="关键字">
          <el-input v-model="queryParams.keywords" placeholder="标题、内容" clearable />
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
        <el-col :span="1.5" v-show="selected.length">
          <el-button type="primary" @click="handleBatchSetViewed(null)">
            <template #icon>
              <el-icon>
                <Icon icon="ri:eye-line"></Icon>
              </el-icon>
            </template>
            标为已读
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
          <el-table-column v-if="columns.messageId.visible" prop="messageId" label="消息 ID" min-width="180" />
          <el-table-column v-if="columns.title.visible" prop="title" label="标题" width="220" />
          <el-table-column v-if="columns.content.visible" prop="content" label="内容" min-width="260" />
          <el-table-column v-if="columns.category.visible" prop="category" label="消息分类" min-width="100" align="center">
            <template #default="{ row }">
              <VtTagDict :code="'vt_message_category'" :value="row.category" :size="size"></VtTagDict>
            </template>
          </el-table-column>
          <el-table-column v-if="columns.createByName.visible" prop="createByName" label="发送者" align="center"
            min-width="100">
            <template #default="{ row }">
              <span v-if="row.category === 'user'">{{ row.createByName }}</span>
              <span v-else>{{ row.createByName ?? '系统' }}</span>
            </template>
          </el-table-column>
          <el-table-column v-if="columns.createTime.visible" prop="createTime" label="发送时间" align="center"
            min-width="180" />
          <el-table-column v-if="columns.viewed.visible" prop="viewed" label="已读/未读" min-width="100" align="center">
            <template #default="{ row }">
              <VtTagDict :code="'vt_message_viewed_status'" :value="row.viewed" :size="size"></VtTagDict>
            </template>
          </el-table-column>
          <el-table-column v-if="columns.viewedTime.visible" prop="viewedTime" label="查看时间" align="center"
            min-width="180" />
          <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="75">
            <template #default="scope">
              <div>
                <el-tooltip content="标为已读" placement="top" v-if="scope.row.viewed === 'N'">
                  <el-button type="primary" text :size="size" @click="handleSetViewed(scope.row.id)">
                    <template #icon>
                      <el-icon :size="size">
                        <Icon icon="ri:eye-line"></Icon>
                      </el-icon>
                    </template>
                  </el-button>
                </el-tooltip>
                <el-tooltip content="标为未读" placement="top" v-if="scope.row.viewed === 'Y'">
                  <el-button type="primary" text :size="size" @click="handleSetUnviewed(scope.row.id)">
                    <template #icon>
                      <el-icon :size="size">
                        <Icon icon="ri:eye-off-line"></Icon>
                      </el-icon>
                    </template>
                  </el-button>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination background layout="total, sizes, prev, pager, next, jumper"
          v-model:current-page="queryParams.current" v-model:page-size="queryParams.size" :total="queryParams.total"
          @change="handlePageChange" />
      </div>
    </el-main>
  </el-container>

</template>

<style scoped>
.vt-message-category-tree {
  border-right: 1px solid #dddddd;
  height: calc(100vh - var(--vt-header-height) - var(--vt-footer-height));
}

.vt-search-container {
  padding: 10px 0px 0px 0px;
}
</style>
