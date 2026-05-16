<script setup>
const props = defineProps({
  disabled: {
    default: false,
    type: Boolean,
  },
});

const modelValue = defineModel({
  type: Object,
  default: () => ({ name: undefined, code: undefined }),
});

const formRef = useTemplateRef("formRef");

const onReset = () => {
  formRef.value?.resetFields();
  // 重置后清除验证错误
  formRef.value?.clearValidate();
};

/** 暴露给父组件，父组件可通过 xxxRef.value.getData 来获取 */
defineExpose({ onReset });
</script>

<template>
  <el-form ref="formRef" :model="modelValue" label-width="auto">
    <el-form-item
      prop="name"
      label="名称"
      :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
    >
      <el-input
        v-model="modelValue.name"
        clearable
        :disabled="props.disabled"
        maxlength="30"
        autocomplete="off"
      />
    </el-form-item>
    <el-form-item
      prop="code"
      label="编码"
      :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
    >
      <el-input
        v-model="modelValue.code"
        clearable
        :disabled="props.disabled"
        maxlength="30"
        autocomplete="off"
      />
    </el-form-item>
  </el-form>
</template>
