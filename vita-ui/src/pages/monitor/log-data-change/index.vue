<route lang="yaml">
meta:
  title: 数据变动日志
  permission: monitor:logDataChange:view
</route>

<script setup>
import { logDataChangeApi } from "@/api/monitor/log-data-change-api.js";
import { columns } from "./columns.js";
import LogDataChangeDetail from "./components/log-data-change-detail.vue";

const loading = ref(true);

const size = ref("default");

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

/**
 * 不能初始化为 null，否则 resetFields() 不生效
 */
const queryParams = reactive({
  businessId: undefined,
  pageCurrent: 1,
  pageSize: 10,
  pageTotal: 0,
  tableName: undefined,
});

const queryFormRef = useTemplateRef("queryFormRef");

const resetQueryForm = () => {
  queryFormRef.value.resetFields();
  loadTableData();
};

const loadTableData = () => {
  loading.value = true;
  logDataChangeApi.page(queryParams).then((res) => {
    tableData.value = res.pageRecords;
    queryParams.pageTotal = res.pageTotal;
    loading.value = false;
  });
};

/** selected rows */
const selected = ref([]);

const handleDelete = (ids) => {
  logDataChangeApi.remove(ids).then(() => {
    // 清空已选择
    selected.value = [];
    loadTableData();
  });
};

const handleBatchDelete = () => {
  const ids = selected.value.map((item) => item.id).join();
  handleDelete(ids);
};

const handlePageChange = (currentPage, pageSize) => {
  queryParams.pageCurrent = currentPage;
  queryParams.pageSize = pageSize;
  loadTableData();
};

const logDataChangeDetailRef = useTemplateRef("logDataChangeDetailRef");
const handleDetail = (row) => {
  logDataChangeDetailRef.value.data = { ...row };
  logDataChangeDetailRef.value.visible = true;
};

const tableNames = ref([]);
onMounted(() => {
  loadTableData();
  logDataChangeApi.listTableNames().then((res) => {
    tableNames.value = res;
  });
});
</script>

<template>
  <!-- 查询表单 -->
  <el-form ref="queryFormRef" :model="queryParams" :inline="true" @submit.prevent="loadTableData">
    <el-form-item prop="tableName" label="表名称">
      <el-select
        v-model="queryParams.tableName"
        clearable
        :style="'width: 240px;'"
        placeholder="请选择"
      >
        <el-option v-for="item in tableNames" :key="item" :label="item" :value="item" />
      </el-select>
    </el-form-item>
    <el-form-item prop="businessId" label="业务数据 ID">
      <el-input v-model="queryParams.businessId" placeholder="" clearable />
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
    <el-col :span="1.5" v-show="selected.length" v-permission="'monitor:logDataChange:remove'">
      <el-popconfirm
        placement="right"
        width="400"
        :title="`确定全部删除已选择的【${selected.map((i) => i.username).join()}】吗？`"
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
    <!-- 右侧 -->
    <VtTableBarRight :tableRef="tableRef" :columns="columns" @update-size="(val) => (size = val)" />
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
      <el-table-column v-if="columns.selection.visible" type="selection" width="55" />
      <el-table-column v-if="columns.index.visible" type="index" label="序号" width="60" />
      <el-table-column v-if="columns.id.visible" prop="id" label="ID" min-width="180" />
      <el-table-column
        v-if="columns.tableName.visible"
        prop="tableName"
        label="表名称"
        min-width="100"
        fixed="left"
      />
      <el-table-column
        v-if="columns.businessId.visible"
        prop="businessId"
        label="业务数据 ID"
        min-width="100"
      />
      <el-table-column
        v-if="columns.readableMessages.visible"
        prop="readableMessages"
        label="变更信息"
        min-width="180"
      />
      <el-table-column
        v-if="columns.createByName.visible"
        prop="createByName"
        label="操作者"
        align="center"
        width="100"
      />
      <el-table-column
        v-if="columns.createTime.visible"
        prop="createTime"
        label="操作时间"
        align="center"
        width="180"
      />
      <el-table-column
        v-if="columns.updateByName.visible"
        prop="updateByName"
        label="更新者"
        align="center"
        width="100"
      />
      <el-table-column
        v-if="columns.updateTime.visible"
        prop="updateTime"
        label="更新时间"
        align="center"
        width="180"
      />
      <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="120">
        <template #default="scope">
          <div>
            <el-tooltip content="详情" placement="top">
              <el-button type="primary" text :size="size" @click="handleDetail(scope.row)">
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:view"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <div style="display: inline-block">
                <el-popconfirm
                  placement="left"
                  width="400"
                  :title="`确定删除账号为【${scope.row.username}】的登录记录吗？`"
                  confirm-button-text="确定"
                  cancel-button-text="取消"
                  @confirm="handleDelete(scope.row.id)"
                >
                  <template #reference>
                    <el-button
                      type="danger"
                      text
                      :size="size"
                      v-permission="'monitor:logDataChange:remove'"
                    >
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

  <LogDataChangeDetail ref="logDataChangeDetailRef"></LogDataChangeDetail>
</template>

<style scoped></style>
