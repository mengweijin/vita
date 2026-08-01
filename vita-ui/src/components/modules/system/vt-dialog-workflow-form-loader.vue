<script setup>
import { flowDefinitionApi } from "@/api/workflow/flow-definition-api.js";

const props = defineProps({
  visible: {
    type: Boolean,
    required: true,
  },
  /** 流程编码 */
  flowCode: {
    type: String,
    default: "",
  },
  /** 业务 ID */
  businessId: {
    type: String,
    default: "",
  },
  readonly: {
    type: Boolean,
    default: false,
  },
  title: {
    type: String,
    default: "创建流程",
  },
});

const loading = ref(false);

const emit = defineEmits(["callback"]);

const onClosed = () => {
  emit("update:visible", false);
};

const loadDone = () => {
  loading.value = false;
};

// 动态路由地址
const routePath = ref("");

watchEffect(async () => {
  if (props.flowCode) {
    loading.value = true;
    routePath.value = await flowDefinitionApi.queryPublishedDefinitionStartFormRoutePathByFlowCode(
      props.flowCode,
    );
  }
});
</script>

<template>
  <el-dialog :model-value="visible" :title="title" destroy-on-close align-center @closed="onClosed">
    <VtRoutePageLoader
      v-loading="loading"
      :route-path="routePath"
      :readonly="props.readonly"
      :businessId="props.businessId"
      @callback="loadDone"
    />
  </el-dialog>
</template>

<style scoped></style>
