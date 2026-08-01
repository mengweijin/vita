<script setup>
import { flowHisTaskApi } from "@/api/workflow/flow-his-task-api.js";
import utils from "@/utils/utils.js";

const props = defineProps({
  /** 流程实例 ID */
  id: {
    type: String,
    default: "",
  },
});

const emit = defineEmits(["callback"]);

const loading = ref(true);

const size = ref("default");

const tableRef = useTemplateRef("tableRef");

const tableData = ref([]);

const loadTableData = () => {
  loading.value = true;
  flowHisTaskApi
    .listApproveLog(props.id)
    .then((res) => {
      tableData.value = res;
    })
    .finally(() => {
      loading.value = false;
      emit("callback");
    });
};

const costTime = (createTime, updateTime) => {
  const format = "yyyy-MM-dd HH:mm:ss";
  let create = utils.timestamp(createTime, format);
  let update = utils.timestamp(updateTime, format);
  return utils.timeAgo((update - create) / 1000);
};

onMounted(() => {
  loadTableData();
});
</script>

<template>
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
  >
    <el-table-column v-if="false" type="selection" width="55" />
    <el-table-column v-if="true" type="index" label="序号" width="60" />
    <el-table-column v-if="false" prop="id" label="ID" min-width="180" />
    <el-table-column v-if="false" prop="definitionId" label="流程定义ID" min-width="180" />
    <el-table-column v-if="false" prop="instanceId" label="流程编号" min-width="180" />
    <el-table-column v-if="false" prop="flowName" label="流程名称" min-width="100" />
    <el-table-column v-if="false" prop="taskId" label="任务ID" min-width="100" />
    <el-table-column v-if="false" prop="nodeCode" label="节点编码" min-width="100" />
    <el-table-column v-if="true" prop="nodeName" label="节点名称" min-width="120" />
    <el-table-column v-if="true" prop="nodeType" label="节点类型" width="100" align="center">
      <template #default="{ row }">
        <VtTagDict :code="'vt_warmflow_node_type'" :value="row.nodeType" :size="size"></VtTagDict>
      </template>
    </el-table-column>
    <el-table-column v-if="false" prop="targetNodeCode" label="目标节点编码" min-width="100" />
    <el-table-column v-if="false" prop="targetNodeName" label="目标节点名称" min-width="100" />
    <el-table-column v-if="true" prop="approver" label="审批人" width="80" align="center" />
    <el-table-column
      v-if="false"
      prop="cooperateType"
      label="协作类型"
      min-width="100"
      align="center"
    >
      <template #default="{ row }">
        <VtTagDict
          :code="'vt_warmflow_node_cooperate_type'"
          :value="row.cooperateType"
          :size="size"
        ></VtTagDict>
      </template>
    </el-table-column>
    <el-table-column v-if="true" prop="collaborator" label="协作者" width="80" align="center" />
    <el-table-column v-if="true" prop="skipType" label="跳转类型" min-width="90" align="center">
      <template #default="{ row }">
        <VtTagDict
          :code="'vt_warmflow_node_skip_type'"
          :value="row.skipType"
          :size="size"
        ></VtTagDict>
      </template>
    </el-table-column>
    <el-table-column v-if="false" prop="flowStatus" label="流程状态" min-width="100" align="center">
      <template #default="{ row }">
        <VtTagDict
          :code="'vt_warmflow_flow_status'"
          :value="row.flowStatus"
          :size="size"
        ></VtTagDict>
      </template>
    </el-table-column>
    <el-table-column
      v-if="false"
      prop="formCustom"
      label="自定义表单"
      min-width="100"
      align="center"
    >
      <template #default="{ row }">
        <VtTagDict
          :code="'vt_warmflow_form_custom'"
          :value="row.formCustom"
          :size="size"
        ></VtTagDict>
      </template>
    </el-table-column>
    <el-table-column v-if="false" prop="formPath" label="表单路径" min-width="100" />
    <el-table-column v-if="true" prop="message" label="消息" min-width="110" />
    <el-table-column v-if="false" prop="variable" label="变量" min-width="100" />
    <el-table-column v-if="false" prop="ext" label="扩展属性" min-width="100" />
    <el-table-column v-if="false" prop="createTime" label="创建时间" align="center" width="160" />
    <el-table-column v-if="true" prop="updateTime" label="审批时间" align="center" width="160" />
    <el-table-column v-if="true" prop="updateTime" label="审批耗时" align="center" width="160">
      <template #default="{ row }">
        {{ costTime(row.createTime, row.updateTime) }}
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped></style>
