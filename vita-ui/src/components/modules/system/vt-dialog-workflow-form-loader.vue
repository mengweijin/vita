<script setup>
import { flowDefinitionApi } from "@/api/workflow/flow-definition-api.js";
import { deptApi } from "@/api/system/dept-api.js";
import utils from "@/utils/utils.js";

const props = defineProps({
  definitionId: {
    type: String,
    required: true,
  },
  readonly: {
    type: Boolean,
    default: false,
  },
  businessId: {
    type: String,
    required: false,
  },
});

const visible = defineModel({ default: false, type: Boolean });

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
  <VtDialogFormRender
    v-if="isFormCustom"
    v-model="visible"
    :form-id="definition?.formPath"
    :business-id="props.businessId"
    :readonly="props.readonly"
  />

  <VtDialogRoutePageLoader
    v-else
    v-model="visible"
    :route-path="definition?.formPath"
    :business-id="props.businessId"
    :readonly="props.readonly"
  />
</template>

<style scoped></style>
