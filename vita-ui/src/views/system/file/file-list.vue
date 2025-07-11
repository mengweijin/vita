<script setup>
import { fileApi } from "@/api/system/file-api";
import { columns } from './file-hook.js';
import { useUserStore } from '@/store/user-store';
const userStore = useUserStore();

const loading = ref(true);

const size = ref('default');

const tableRef = ref({});

const tableData = ref([]);

/**
 * 不能初始化为 null，否则 resetFields() 不生效
 */
const queryParams = reactive({
  keywords: undefined,
  md5: undefined,
  current: 1,
  size: 10,
  total: 0,
});

const queryFormRef = ref(null);

const resetQueryForm = () => {
  queryFormRef.value.resetFields();
  loadTableData();
};

const loadTableData = () => {
  loading.value = true;
  fileApi.page(queryParams).then((res) => {
    tableData.value = res.records;
    queryParams.total = res.total;
    loading.value = false;
  });
};

const { VITE_BASE_API } = import.meta.env;

const uploadUrl = `${window.location.origin}${VITE_BASE_API}/system/file/upload`;

const handleUpload = (res) => {
  ElMessage.success({ message: `【${res[0]?.name}】上传成功!`, duration: 3000, showClose: true });
  loadTableData();
}

const handleDownload = (row) => {
  fileApi.download(row.id);
}

/** selected rows */
const selected = ref([]);

const handleDelete = (ids) => {
  fileApi.remove(ids).then(() => {
    // 清空已选择
    selected.value = [];
    loadTableData();
  });
}

const handleBatchDelete = () => {
  let ids = selected.value.map(item => item.id).join();
  handleDelete(ids);
}

const handlePageChange = (currentPage, pageSize) => {
  queryParams.current = currentPage;
  queryParams.size = pageSize;
  loadTableData();
}

onMounted(() => {
  loadTableData();
});

</script>

<template>
  <!-- 查询表单 -->
  <el-form ref="queryFormRef" :model="queryParams" :inline="true" @submit.prevent="loadTableData"
    class="vt-search-container">
    <el-form-item prop="keywords" label="关键字">
      <el-input v-model="queryParams.keywords" placeholder="文件名称、后缀" clearable />
    </el-form-item>
    <el-form-item prop="md5" label="MD5">
      <el-input v-model="queryParams['md5']" clearable />
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
      <el-upload multiple :show-file-list="false" :action="uploadUrl" :on-success="handleUpload"
        :headers="{ Authorization: `${userStore.getBearerToken()}` }">
        <el-button type="primary">
          <template #icon>
            <el-icon>
              <Icon icon="ep:upload"></Icon>
            </el-icon>
          </template>
          上传文件
        </el-button>
      </el-upload>
    </el-col>
    <el-col :span="1.5" v-show="selected.length">
      <el-popconfirm placement="right" width="400" :title="`确定全部删除已选择的【${selected.map(i => i.name).join()}】吗？`"
        confirm-button-text="确定" cancel-button-text="取消" @confirm="handleBatchDelete">
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
    <!-- 右侧 -->
    <VtTableBarRight :tableRef="tableRef" :columns="columns" @update-size="(val) => size = val" />
  </el-row>

  <!-- 表格 -->
  <div class="vt-table-container">
    <el-table ref="tableRef" v-loading="loading" :data="tableData" :size="size" row-key="id" height="100%" stripe border
      show-overflow-tooltip highlight-current-row @selection-change="(val) => selected = val">
      <el-table-column v-if="columns.selection.visible" type="selection" width="55" />
      <el-table-column v-if="columns.index.visible" type="index" label="序号" width="60" />
      <el-table-column v-if="columns.id.visible" prop="id" label="ID" min-width="180" />
      <el-table-column v-if="columns.name.visible" prop="name" label="文件名称" min-width="240" fixed="left" />
      <el-table-column v-if="columns.suffix.visible" prop="suffix" label="文件后缀" min-width="80" />
      <el-table-column v-if="columns.storagePath.visible" prop="storagePath" label="存储路径" min-width="260" />
      <el-table-column v-if="columns.md5.visible" prop="md5" label="MD5" min-width="260" />
      <el-table-column v-if="columns.createByName.visible" prop="createByName" label="创建者" align="center" width="100" />
      <el-table-column v-if="columns.createTime.visible" prop="createTime" label="创建时间" align="center" width="180" />
      <el-table-column v-if="columns.updateByName.visible" prop="updateByName" label="更新者" align="center" width="100" />
      <el-table-column v-if="columns.updateTime.visible" prop="updateTime" label="更新时间" align="center" width="180" />
      <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="120">
        <template #default="scope">
          <div>
            <el-tooltip content="下载" placement="top">
              <el-button type="primary" text :size="size" @click="handleDownload(scope.row)">
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:download"></Icon>
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

</template>

<style scoped>
:deep(.el-upload-list) {
  margin: 0px;
}
</style>
