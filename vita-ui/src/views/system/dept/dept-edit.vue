<script setup>
import { addFullPath, copyDefinedProperties } from "@/utils/tool";
import { deptApi } from "@/api/system/dept-api";
import { toArrayTree } from "xe-utils";

const visible = ref(false);

const data = ref({});

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则二次打开可能就打不开了 */
const form = reactive({
  id: undefined,
  parentId: undefined,
  name: undefined,
  seq: 1,
  disabled: 'N',
});

const onDialogOpen = () => {
  copyDefinedProperties(form, data.value);
}

const formRef = ref(null);

const resetForm = () => {
  formRef.value.resetFields();
  onDialogOpen();
};

const title = computed(() => {
  return data?.id ? '编辑' : '新增';
});

const onSubmit = async () => {
  await formRef.value.validate((valid, fields) => {
    if (!valid) {
      // fields 只有在验证失败的情况下才有值
      console.log(fields)
      return;
    }
    if (form.id) {
      deptApi.update(form).then((r) => {
        emit('refresh-table');
        closeDialog();
      });
    } else {
      deptApi.create(form).then((r) => {
        emit('refresh-table');
        closeDialog();
      });
    }
  });
}

const emit = defineEmits(['refresh-table']);

const closeDialog = () => {
  formRef.value.resetFields();
  visible.value = false;
};

const deptList = ref([]);

const deptTreeSelectOptions = computed(() => {
  deptList.value.forEach((item) => item.disabled = false);
  addFullPath(deptList.value, { pathKey: 'name' })
  return toArrayTree(deptList.value, { sortKey: 'seq' });
});

onMounted(() => {
  deptApi.list().then(res => deptList.value = res);
});

/** 暴露给父组件，父组件可通过 deptEditRef.value.visible = true; 来赋值 */
defineExpose({ visible, data })
</script>

<template>
  <el-dialog v-model="visible" :title="title" destroy-on-close align-center @opened="onDialogOpen" width="40%">
    <el-form ref="formRef" :model="form" label-width="auto">
      <el-form-item prop="parentId" label="父部门">
        <el-tree-select v-model="form.parentId" :data="deptTreeSelectOptions"
          :props="{ label: 'nameFullPath', value: 'id', children: 'children' }" check-strictly filterable clearable
          default-expand-all placeholder="请选择">
          <template #default="{ data: { name } }">
            {{ name }}
          </template>
        </el-tree-select>
      </el-form-item>

      <el-form-item prop="name" label="名称" :rules="[{ required: true, message: '必填', trigger: 'blur' }]">
        <el-input v-model="form.title" maxlength="30" autocomplete="off" />
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
        <el-button type="primary" @click="closeDialog">
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
