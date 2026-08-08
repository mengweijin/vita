<script setup>
import { formApi } from "@/api/system/form-api.js";

const props = defineProps({
  /** 表单 ID */
  formId: {
    type: String,
    required: true,
  },
  businessId: {
    type: String,
    default: null,
    required: false,
  },
  api: {
    type: Object,
    required: false,
  },
});

const rule = ref([]);
const options = ref({});
const formData = ref({});

onMounted(async () => {
  const form = await formApi.getById(props.formId);
  rule.value = form.rules;
  options.value = form.options;

  if (props.businessId && typeof props.api?.getById === "function") {
    // api 有 getById 方法
    formData.value = await props.api.getById(props.businessId);
  } else {
    // api 没有 getById 方法，直接使用业务 ID 作为表单数据的 ID
    formData.value.id = props.businessId;
  }
});
</script>

<template>
  <formCreate
    :rule="rule"
    :option="options"
    v-model="formData"
    :api="props.api"
  ></formCreate>
</template>

<style scoped></style>
