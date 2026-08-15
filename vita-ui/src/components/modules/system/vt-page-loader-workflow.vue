<script setup>
import { flowDefinitionApi } from "@/api/workflow/flow-definition-api.js";

const props = defineProps({
  definitionId: {
    type: String,
    required: true,
  },
  businessId: {
    type: String,
    required: false,
  },
  disabled: {
    type: Boolean,
    default: false,
  },
});

const definition = ref(null);

onMounted(async () => {
  if (props.definitionId) {
    definition.value = await flowDefinitionApi.queryById(props.definitionId);
  }
});
</script>

<template>
  <VtPageLoader
    v-if="definition?.formPath"
    :route-path="definition.formPath"
    :business-id="props.businessId"
    :disabled="props.disabled"
  />
</template>

<style scoped></style>
