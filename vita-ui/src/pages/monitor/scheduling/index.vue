<route lang="yaml">
meta:
  title: 调度任务
  permission: monitor:schedulingTask:view
</route>

<script setup>
import { schedulingTaskApi } from "@/api/monitor/scheduling-task-api.js";
import cronstrue from "cronstrue";
import "cronstrue/locales/zh_CN";
import { columns } from "./columns.js";
import SchedulingTaskEdit from "./components/scheduling-task-edit.vue";

const router = useRouter();

const loading = ref(true);

const size = ref("default");

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

/**
 * 不能初始化为 null，否则 resetFields() 不生效
 */
const queryParams = reactive({
  beanName: undefined,
  disabled: undefined,
  name: undefined,
  pageCurrent: 1,
  pageSize: 10,
  pageTotal: 0,
});

const queryFormRef = useTemplateRef("queryFormRef");

const resetQueryForm = () => {
  queryFormRef.value.resetFields();
  loadTableData();
};

const loadTableData = () => {
  loading.value = true;
  schedulingTaskApi.page(queryParams).then((res) => {
    tableData.value = res.pageRecords;
    queryParams.pageTotal = res.pageTotal;
    loading.value = false;
  });
};

const handleEnableTask = (row) => {
  schedulingTaskApi.enable(row.id).then(() => {
    loadTableData();
  });
};

const handleDisableTask = (row) => {
  schedulingTaskApi.disable(row.id).then(() => {
    loadTableData();
  });
};

const handleViewTaskLog = (row) => {
  router.push(`/monitor/scheduling/${row.id}`);
};

const handleRunTask = (row) => {
  schedulingTaskApi.run(row.id);
};

const schedulingTaskEditRef = useTemplateRef("schedulingTaskEditRef");

const handleEdit = (row) => {
  schedulingTaskEditRef.value.data = { ...row };
  schedulingTaskEditRef.value.visible = true;
};

/** selected rows */
const selected = ref([]);

const handleDelete = (ids) => {
  schedulingTaskApi.remove(ids).then(() => {
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

const taskBeanNames = ref([]);

onMounted(() => {
  loadTableData();

  schedulingTaskApi.queryTaskBeanNames().then((res) => {
    taskBeanNames.value = res;
  });
});
</script>

<template>
  <!-- 查询表单 -->
  <el-form ref="queryFormRef" :model="queryParams" :inline="true" @submit.prevent="loadTableData">
    <el-form-item prop="name" label="任务名称">
      <el-input v-model="queryParams.name" placeholder="" clearable />
    </el-form-item>
    <el-form-item prop="beanName" label="Bean 名称">
      <el-select
        v-model="queryParams.beanName"
        clearable
        :filterable="true"
        :multiple="false"
        placeholder="请选择"
      >
        <el-option v-for="item in taskBeanNames" :key="item" :label="item" :value="item" />
      </el-select>
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
    <el-col
      :span="1.5"
      v-if="false"
      v-show="selected.length"
      v-permission="'monitor:schedulingTask:remove'"
    >
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
      <el-table-column v-if="columns.name.visible" prop="name" label="任务名称" min-width="180" />
      <el-table-column v-if="columns.cron.visible" prop="cron" label="CRON 表达式" width="120">
        <template #default="{ row }">
          <el-tooltip
            :content="cronstrue.toString(row.cron, { locale: 'zh_CN', use24HourTimeFormat: true })"
            placement="top"
          >
            <span>{{ row.cron }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column v-if="columns.cron.visible" prop="cron" label="CRON 执行描述" width="160">
        <template #default="{ row }">
          <span>{{
            cronstrue.toString(row.cron, { locale: "zh_CN", use24HourTimeFormat: true })
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        v-if="columns.beanName.visible"
        prop="beanName"
        label="执行 Bean 名称"
        width="240"
      />
      <el-table-column v-if="columns.args.visible" prop="args" label="执行参数" min-width="120" />
      <el-table-column
        v-if="columns.disabled.visible"
        prop="disabled"
        label="状态"
        width="80"
        align="center"
      >
        <template #default="{ row }">
          <VtTagDict :code="'vt_disabled'" :value="row.disabled" :size="size"></VtTagDict>
        </template>
      </el-table-column>
      <el-table-column v-if="columns.remark.visible" prop="remark" label="备注" min-width="160" />
      <el-table-column
        v-if="columns.createByName.visible"
        prop="createByName"
        label="创建者"
        align="center"
        width="100"
      />
      <el-table-column
        v-if="columns.createTime.visible"
        prop="createTime"
        label="创建时间"
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
      <el-table-column v-if="columns.operation.visible" label="操作" fixed="right" width="250">
        <template #default="scope">
          <div>
            <el-tooltip content="启用" placement="top" v-if="scope.row.disabled === 'Y'">
              <el-button type="primary" text :size="size" @click="handleEnableTask(scope.row)">
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ant-design:check-circle-outlined"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="停用" placement="top" v-if="scope.row.disabled === 'N'">
              <el-button type="primary" text :size="size" @click="handleDisableTask(scope.row)">
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ri:forbid-line"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="执行日志" placement="top">
              <el-button
                type="primary"
                text
                :size="size"
                style="margin-left: 0px"
                @click="handleViewTaskLog(scope.row)"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:tickets"></Icon>
                  </el-icon>
                </template>
              </el-button>
            </el-tooltip>
            <el-tooltip content="立即执行" placement="top">
              <el-button
                type="primary"
                text
                :size="size"
                style="margin-left: 0px"
                @click="handleRunTask(scope.row)"
                v-permission="'monitor:schedulingTask:run'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:video-play"></Icon>
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
                v-permission="'monitor:schedulingTask:update'"
              >
                <template #icon>
                  <el-icon :size="size">
                    <Icon icon="ep:edit"></Icon>
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
                      v-permission="'monitor:schedulingTask:remove'"
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

  <SchedulingTaskEdit
    ref="schedulingTaskEditRef"
    @refresh-table="loadTableData"
  ></SchedulingTaskEdit>
</template>

<style scoped></style>
