<script setup>
import { flowInstanceApi } from "@/api/workflow/flow-instance-api.js";
import { flowDefinitionApi } from "@/api/workflow/flow-definition-api.js";
import utils from "@/utils/utils.js";

const props = defineProps({
  /** 流程实例 ID */
  id: {
    type: String,
    default: "",
  },
});

const emit = defineEmits(["callback"]);

const routePath = ref("");
const businessId = ref("");
const readonly = ref(true);

onMounted(async () => {
  const flowInstance = await flowInstanceApi.queryById(props.id);
  businessId.value = flowInstance.businessId;

  const flowDefinition = await flowDefinitionApi.queryById(flowInstance.definitionId);
  routePath.value = await flowDefinitionApi.queryPublishedDefinitionStartFormRoutePathByFlowCode(
    flowDefinition.flowCode,
  );

  utils.sleep(500).then(() => {
    emit("callback");
  });
});
</script>

<template>
  <VtRoutePageLoader :route-path="routePath" :readonly="readonly" :businessId="businessId" />
</template>

<style scoped></style>
