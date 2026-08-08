<script setup>
import { formApi } from "@/api/system/form-api.js";

const props = defineProps({
  /** 表单 ID */
  id: {
    type: String,
    required: true,
  },
  title: {
    type: String,
    default: "表单设计器",
  },
});

const visible = defineModel({ default: false, type: Boolean });

const designer = useTemplateRef("designer");

const config = reactive({
  showSaveBtn: true,
  //控制字段ID输入框能否输入
  fieldReadonly: false,
});

const handleSave = ({ rule, options }) => {
  let ruleObject = JSON.parse(rule);
  let optionsObject = JSON.parse(options);
  formApi.update({ id: props.id, rules: ruleObject, options: optionsObject });
};

const onOpen = () => {
  formApi.getById(props.id).then((res) => {
    designer.value.setRule(res.rules);
    designer.value.setOptions(res.options);
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
    fullscreen
  >
    <fc-designer ref="designer" @save="handleSave" :config="config" height="90vh" />
  </el-dialog>
</template>

<style scoped></style>
