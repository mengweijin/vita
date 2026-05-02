<route lang="yaml">
meta:
  title: 调度任务执行日志
</route>

<script setup>
import { schedulingTaskLogApi } from "@/api/monitor/scheduling-task-api.js";
import { useScheduling } from "./hooks.js";
const { taskLogColumns } = useScheduling();
const columns = taskLogColumns;

const route = useRoute();

const id = route.params.id;

const loading = ref(true);

const visible = ref(false);

const data = ref({});

/**
 * 不能初始化为 null，否则 resetFields() 不生效
 */
const queryParams = reactive({
  keywords: undefined,
  pageCurrent: 1,
  pageSize: 10,
  pageTotal: 0,
  schedulingTaskId: undefined,
  status: undefined,
  success: undefined,
});

const queryFormRef = useTemplateRef("queryFormRef");

const resetQueryForm = () => {
  queryFormRef.value.resetFields();
  loadTableData();
};

const size = ref("default");

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

const loadTableData = () => {
  loading.value = true;
  schedulingTaskLogApi.page(queryParams).then((res) => {
    tableData.value = res.pageRecords;
    queryParams.pageTotal = res.pageTotal;
    loading.value = false;
  });
};

/** selected rows */
const selected = ref([]);

const handleDelete = (ids) => {
  schedulingTaskLogApi.remove(ids).then(() => {
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

onMounted(() => {
  loading.value = true;
  queryParams.schedulingTaskId = id;
  loadTableData();
});
</script>

<template>
  <!-- 查询表单 -->
  <el-form ref="queryFormRef" :model="queryParams" :inline="true" @submit.prevent="loadTableData">
    <el-form-item prop="keywords" label="关键字" v-if="false">
      <el-input v-model="queryParams.keywords" placeholder="" clearable />
    </el-form-item>
    <el-form-item prop="status" label="任务执行状态">
      <VtSelectDict v-model="queryParams.status" :code="'vt_scheduling_task_status'"></VtSelectDict>
    </el-form-item>
    <el-form-item prop="success" label="任务执行结果">
      <VtSelectDict v-model="queryParams.success" :code="'vt_succeeded'"></VtSelectDict>
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
    <el-col :span="1.5" v-show="selected.length">
      <el-popconfirm
        placement="right"
        width="400"
        :title="`确定全部删除已选择的【${selected.map((i) => i.id).join()}】吗？`"
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
      :shows="['size', 'columns']"
      :columns="columns"
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
        v-if="columns.schedulingTaskId.visible"
        prop="schedulingTaskId"
        label="调度任务ID"
        min-width="140"
      />
      <el-table-column v-if="columns.args.visible" prop="args" label="实际执行参数" width="160" />
      <el-table-column
        v-if="columns.status.visible"
        prop="status"
        label="执行状态"
        width="90"
        align="center"
      >
        <template #default="{ row }">
          <VtTagDict
            :code="'vt_scheduling_task_status'"
            :value="row.status"
            :size="size"
          ></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.success.visible"
        prop="success"
        label="执行结果"
        width="90"
        align="center"
      >
        <template #default="{ row }">
          <VtTagDict :code="'vt_succeeded'" :value="row.success" :size="size"></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.costTime.visible"
        prop="costTime"
        label="消耗时间（毫秒）"
        width="140"
      />
      <el-table-column
        v-if="columns.message.visible"
        prop="message"
        label="附加信息"
        min-width="220"
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
      <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="75">
        <template #default="scope">
          <div>
            <el-tooltip content="删除" placement="top">
              <div style="display: inline-block">
                <el-popconfirm
                  placement="left"
                  width="400"
                  :title="`确定删除 ID 为【${scope.row.id}】的任务执行记录吗？`"
                  confirm-button-text="确定"
                  cancel-button-text="取消"
                  @confirm="handleDelete(scope.row.id)"
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
      :size="size"
    />
  </div>
</template>

<style scoped></style>
