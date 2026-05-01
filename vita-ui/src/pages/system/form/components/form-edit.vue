<script setup>
import { formApi } from "@/api/system/form-api.js";

const loading = ref(true);

const visible = ref(false);

const data = ref({});

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则影响重置 */
const form = reactive({
  staticFormRoute: undefined,
  id: undefined,
  name: undefined,
  type: undefined,
});

const init = () => {
  form.id = data.value.id ?? undefined;
  form.name = data.value.name ?? undefined;
  form.type = data.value.type ?? undefined;
  form.staticFormRoute = data.value.staticFormRoute ?? undefined;
};

const formRef = useTemplateRef("formRef");

const onSubmit = () => {
  formRef.value.validate((valid, fields) => {
    if (!valid) {
      // fields 只有在验证失败的情况下才有值
      console.log(fields);
      return;
    }
    if (form.id) {
      formApi.update(form).then((r) => {
        emit("refresh-table");
        onClosed();
      });
    } else {
      formApi.create(form).then((r) => {
        emit("refresh-table");
        onClosed();
      });
    }
  });
};

const emit = defineEmits(["refresh-table"]);

const onOpened = () => {
  loading.value = true;
  init();
  loading.value = false;
};

const onClosed = () => {
  visible.value = false;
  data.value = {};
  init();
};

defineExpose({ data, visible });
</script>

<template>
  <el-dialog
    v-model="visible"
    :title="data?.id ? '编辑' : '新增'"
    destroy-on-close
    align-center
    @opened="onOpened"
    @closed="onClosed"
    width="40%"
  >
    <el-form v-loading="loading" ref="formRef" :model="form" label-width="auto">
      <el-form-item
        prop="type"
        label="表单类型"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <VtSelectDict v-model="form.type" :code="'vt_form_type'"></VtSelectDict>
      </el-form-item>
      <el-form-item
        prop="name"
        label="表单名称"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <el-input v-model="form.name" clearable maxlength="30" autocomplete="off" />
      </el-form-item>
      <el-form-item
        prop="staticFormRoute"
        label="表单路由路径"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <el-input v-model="form.staticFormRoute" clearable maxlength="300" autocomplete="off" />
      </el-form-item>
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
        <el-button type="warning" @click="init">
          <template #icon>
            <el-icon>
              <Icon icon="ep:refresh-left"></Icon>
            </el-icon>
          </template>
          重置
        </el-button>
        <el-button type="primary" @click="onClosed">
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
