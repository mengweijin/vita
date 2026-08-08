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
    default: "动态表单",
  },
});

const visible = defineModel({ default: false, type: Boolean });

const rule = ref([]);
const options = ref({});
const formData = ref({});

const onOpen = () => {
  formApi.getById(props.formId).then((res) => {
    rule.value = res.rules;
    options.value = res.options;
  });
};

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
    <formCreate
      :rule="rule"
      :option="options"
      v-model="formData"
      v-model:api="props.api"
    ></formCreate>
  </el-dialog>
</template>

<style scoped></style>
