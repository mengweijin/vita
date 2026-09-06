<script setup>
import { formCreateApi } from "@/api/system/form-create-api.js";

const props = defineProps({
  /** 表单编码 */
  formCode: {
    type: String,
    required: true,
  },
});

const formData = defineModel({ default: {}, type: Object });

const rule = ref([]);
const options = ref({});

onMounted(async () => {
  const form = await formCreateApi.getByCode(props.formCode);
  rule.value = form.rules;
  options.value = form.options;
});
</script>

<template>
  <formCreate v-model="formData" :rule="rule" :option="options"></formCreate>
</template>

<style scoped></style>
