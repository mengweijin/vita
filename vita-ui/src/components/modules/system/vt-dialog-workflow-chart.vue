<script setup>
import utils from "@/utils/utils.js";
import { useLoginStore } from "@/store/login-store.js";
import { flowDefinitionApi } from "@/api/workflow/flow-definition-api.js";
import { flowInstanceApi } from "@/api/workflow/flow-instance-api.js";
import WorkflowApproveLogList from "@/pages/workflow/instance/components/workflow-approve-log-list.vue";
import WorkflowApproveData from "@/pages/workflow/instance/components/workflow-approve-data.vue";
const loginStore = useLoginStore();

const props = defineProps({
  visible: {
    type: Boolean,
    required: true,
  },
  /** 流程实例 ID */
  id: {
    type: String,
    default: "",
  },
  title: {
    type: String,
    default: "查看流程",
  },
});

const loading = ref(false);

const activeTabName = ref("tab1");

const emit = defineEmits(["update:visible"]);

const { VITE_BASE_API } = import.meta.env;

const url = computed(() => {
  let basePath = utils.trimSpecified(VITE_BASE_API, "/");
  let bearerToken = loginStore.getBearerToken();
  return `${basePath}/warm-flow-ui/index.html?id=${props.id}&type=FlowChart&Authorization=${bearerToken}&t=${new Date().getTime()}`;
});

const handleTabChange = () => {
  loading.value = true;
};

const closeLoading = () => {
  loading.value = false;
};

const onOpen = () => {
  loading.value = true;
};

const onClosed = () => {
  emit("update:visible", false);
};
</script>

<template>
  <el-dialog
    :model-value="visible"
    :title="title"
    destroy-on-close
    align-center
    @open="onOpen"
    @closed="onClosed"
    width="80%"
  >
    <el-scrollbar class="vt-dialog-workflow-designer-wrapper" v-loading="loading">
      <el-tabs v-model="activeTabName" type="border-card" @tab-change="handleTabChange">
        <el-tab-pane label="审批数据" name="tab1">
          <WorkflowApproveData
            v-if="activeTabName === 'tab1'"
            :id="props.id"
            @callback="closeLoading"
          />
        </el-tab-pane>
        <el-tab-pane label="流程图" name="tab2">
          <iframe
            v-if="activeTabName === 'tab2'"
            title="warmflow"
            :src="url"
            style="width: 100%; height: 100%; min-height: 300px; border: none; display: block"
            :onload="closeLoading"
          ></iframe>
        </el-tab-pane>
        <el-tab-pane label="审批记录" name="tab3">
          <WorkflowApproveLogList
            v-if="activeTabName === 'tab3'"
            :id="props.id"
            @callback="closeLoading"
          />
        </el-tab-pane>
      </el-tabs>
    </el-scrollbar>
  </el-dialog>
</template>

<style scoped>
.vt-dialog-workflow-designer-wrapper {
  height: calc(100vh - 200px);
}
</style>
