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
const disabled = ref(true);

onMounted(async () => {
  const flowInstance = await flowInstanceApi.queryById(props.id);
  const definition = await flowDefinitionApi.queryById(flowInstance.definitionId);
  businessId.value = flowInstance.businessId;
  routePath.value = definition.formPath;

  utils.sleep(500).then(() => {
    emit("callback");
  });
});
</script>

<template>
  <VtPageLoader :route-path="routePath" :businessId="businessId" :disabled="disabled" />
</template>

<style scoped></style>
