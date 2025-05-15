<script setup>
import { copyDefinedProperties } from "@/utils/tool";
import { postApi } from "@/api/system/post-api";
import { toArrayTree } from "xe-utils";

const loading = ref(true);

const visible = ref(false);

const data = ref({});

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则二次打开可能就打不开了 */
const form = reactive({
  id: undefined,
  name: undefined,
  seq: 1,
  disabled: 'N',
});

const formRef = ref(null);

const resetForm = () => {
  formRef.value.resetFields();
  copyDefinedProperties(form, data.value);
};

const title = computed(() => {
  return data.value?.id ? '编辑' : '新增';
});

const onSubmit = async () => {
  await formRef.value.validate((valid, fields) => {
    if (!valid) {
      // fields 只有在验证失败的情况下才有值
      console.log(fields)
      return;
    }
    if (form.id) {
      postApi.update(form).then((r) => {
        emit('refresh-table');
        onClose();
      });
    } else {
      postApi.create(form).then((r) => {
        emit('refresh-table');
        onClose();
      });
    }
  });
}

const emit = defineEmits(['refresh-table']);

const onOpen = () => {
  copyDefinedProperties(form, data.value);
  nextTick(() => { loading.value = false; });
}

const onClose = () => {
  formRef.value.resetFields();
  visible.value = false;
}

/** 暴露给父组件，父组件可通过 deptEditRef.value.visible = true; 来赋值 */
defineExpose({ visible, data })
</script>

<template>
  <el-dialog v-model="visible" :title="title" destroy-on-close align-center @open="onOpen" @close="onClose" width="40%">
    <el-form v-loading="loading" ref="formRef" :model="form" label-width="auto">

      <el-form-item prop="name" label="名称" :rules="[{ required: true, message: '必填', trigger: 'blur' }]">
        <el-input v-model="form.name" maxlength="30" autocomplete="off" />
      </el-form-item>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="disabled" label="状态">
            <el-switch v-model="form.disabled" inline-prompt active-text="启用" inactive-text="停用" active-value="N"
              inactive-value="Y" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="seq" label="排序">
            <el-input-number v-model="form.seq" :min="1" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <div>
        <el-button type="primary" @click="onSubmit">
          <template #icon>
            <el-icon>
              <Icon icon="ep:check"></Icon>
            </el-icon>
          </template>
          确定
        </el-button>
        <el-button type="warning" @click="resetForm">
          <template #icon>
            <el-icon>
              <Icon icon="ep:refresh-left"></Icon>
            </el-icon>
          </template>
          重置
        </el-button>
        <el-button type="primary" @click="onClose">
          <template #icon>
            <el-icon>
              <Icon icon="ep:close"></Icon>
            </el-icon>
          </template>
          取消
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped></style>
