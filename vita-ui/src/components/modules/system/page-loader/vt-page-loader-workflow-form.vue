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
  api: {
    type: Object,
    required: false,
  },
  readonly: {
    type: Boolean,
    default: false,
  },
});

const definition = ref(null);

const isFormCustom = computed(() => {
  return definition.value && definition.value.formCustom === "Y";
});

onMounted(async () => {
  if (props.definitionId) {
    definition.value = await flowDefinitionApi.queryById(props.definitionId);
  }
});
</script>

<template>
  <VtPageLoaderForm
    v-if="isFormCustom"
    :form-id="definition?.formPath"
    :business-id="props.businessId"
    :api="props.api"
  />

  <template v-else>
    <VtPageLoaderRoute
      v-if="definition?.formPath"
      :route-path="definition.formPath"
      :business-id="props.businessId"
      :readonly="props.readonly"
    />
  </template>
</template>

<style scoped></style>
