<route lang="yaml">
meta:
  title: 系统日志
  permission: monitor:logSystem:view
</route>

<script setup>
import { logSystemApi } from "@/api/monitor/log-system-api";
import LogSystemDetail from "./components/log-system-detail.vue";
import { useLogSystem } from "./hooks.js";
const { columns } = useLogSystem();

const loading = ref(true);

const size = ref("default");

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

/**
 * 不能初始化为 null，否则 resetFields() 不生效
 */
const queryParams = reactive({
  formattedMessage: undefined,
  loggerLevel: undefined,
  loggerName: undefined,
  pageCurrent: 1,
  pageSize: 100,
  pageTotal: 0,
});

const queryFormRef = useTemplateRef("queryFormRef");

const resetQueryForm = () => {
  queryFormRef.value.resetFields();
  loadTableData();
};

const loadTableData = () => {
  loading.value = true;
  logSystemApi.page(queryParams).then((res) => {
    tableData.value = res.pageRecords;
    queryParams.pageTotal = res.pageTotal;
    loading.value = false;
  });
};

/** selected rows */
const selected = ref([]);

const handleDelete = (ids) => {
  logSystemApi.remove(ids).then(() => {
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

const logSystemDetailRef = useTemplateRef("logSystemDetailRef");
const handleDetail = (row) => {
  logSystemDetailRef.value.data = { ...row };
  logSystemDetailRef.value.visible = true;
};

onMounted(() => {
  loadTableData();
});
</script>

<template>
  <!-- 查询表单 -->
  <el-form ref="queryFormRef" :model="queryParams" :inline="true" @submit.prevent="loadTableData">
    <el-form-item prop="loggerName" label="日志名称">
      <el-input v-model="queryParams.loggerName" placeholder="" clearable />
    </el-form-item>
    <el-form-item prop="formattedMessage" label="日志内容">
      <el-input v-model="queryParams.formattedMessage" placeholder="" clearable />
    </el-form-item>
    <el-form-item prop="loggerLevel" label="日志级别">
      <VtSelectDict v-model="queryParams.loggerLevel" :code="'vt_log_level'"></VtSelectDict>
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
    <el-col :span="1.5" v-show="selected.length" v-permission="'monitor:logSystem:remove'">
      <el-popconfirm
        placement="right"
        width="400"
        :title="`确定全部删除已选择的日志吗？`"
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
    <VtTableBarRight
      :tableRef="tableRef"
      :columns="columns"
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
      <el-table-column v-if="columns.selection.visible" type="selection" width="55" />
      <el-table-column v-if="columns.index.visible" type="index" label="序号" width="60" />
      <el-table-column v-if="columns.id.visible" prop="id" label="ID" min-width="180" />
      <el-table-column
        v-if="columns.loggerLevel.visible"
        prop="loggerLevel"
        label="日志级别"
        width="100"
        align="center"
      >
        <template #default="{ row }">
          <VtTagDict :code="'vt_log_level'" :value="row.loggerLevel" :size="size"></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.threadName.visible"
        prop="threadName"
        label="线程名称"
        min-width="160"
      />
      <el-table-column
        v-if="columns.loggerName.visible"
        prop="loggerName"
        label="日志名称"
        min-width="300"
      />
      <el-table-column
        v-if="columns.formattedMessage.visible"
        prop="formattedMessage"
        label="日志内容"
        min-width="500"
      />
      <el-table-column
        v-if="columns.stackTrace.visible"
        prop="stackTrace"
        label="堆栈信息"
        min-width="260"
      />
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
        sortable
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
                  :title="`确定删除日志吗？`"
                  confirm-button-text="确定"
                  cancel-button-text="取消"
                  @confirm="handleDelete(scope.row.id)"
                >
                  <template #reference>
                    <el-button
                      type="danger"
                      text
                      :size="size"
                      v-permission="'monitor:logSystem:remove'"
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
      :page-sizes="[100, 200, 300, 500, 1000]"
      @change="handlePageChange"
    />
  </div>

  <LogSystemDetail ref="logSystemDetailRef"></LogSystemDetail>
</template>

<style scoped></style>
