<script setup>
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
  title: {
    type: String,
    default: " ",
  },
});

const visible = defineModel({ default: false, type: Boolean });

const onOpen = () => {};

const onClosed = () => {
  visible.value = false;
};
</script>

<template>
  <el-dialog
    v-model="visible"
    :title="props.title"
    destroy-on-close
    align-center
    @open="onOpen"
    @closed="onClosed"
    width="60%"
  >
    <VtPageLoaderWorkflowForm
      :definition-id="props.definitionId"
      :business-id="props.businessId"
      :api="props.api"
      :readonly="props.readonly"
    />
  </el-dialog>
</template>

<style scoped></style>
