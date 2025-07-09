<script setup>
import { dictDataApi } from "@/api/system/dict-api";
import VtDictSelect from "@/components/modules/system/vt-dict-select.vue";
import { isEmpty } from 'xe-utils';

const loading = ref(true);

const visible = ref(false);

const data = ref({});

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则影响重置 */
const form = reactive({
  id: undefined,
  code: undefined,
  label: undefined,
  val: undefined,
  tag: undefined,
  seq: undefined,
  disabled: undefined,
  remark: undefined,
});

const init = () => {
  form.id = data.value.id ?? undefined;
  form.code = data.value.code ?? undefined;
  form.label = data.value.label ?? undefined;
  form.val = data.value.val ?? undefined;
  form.tag = data.value.tag ?? undefined;
  form.seq = data.value.seq ?? 0;
  form.disabled = data.value.disabled ?? 'N';
  form.remark = data.value.remark ?? undefined;
};

const formRef = ref(null);

const onSubmit = () => {
  formRef.value.validate((valid, fields) => {
    if (!valid) {
      // fields 只有在验证失败的情况下才有值
      console.log(fields)
      return;
    }
    if (form.id) {
      dictDataApi.update(form).then((r) => {
        emit('refresh-table');
        onClosed();
      });
    } else {
      dictDataApi.create(form).then((r) => {
        emit('refresh-table');
        onClosed();
      });
    }
  });
}

const emit = defineEmits(['refresh-table']);

const onOpened = () => {
  loading.value = true;
  init();
  loading.value = false;
}

const onClosed = () => {
  visible.value = false;
  data.value = {};
  init();
}

/** 暴露给父组件，父组件可通过 deptEditRef.value.visible = true; 来赋值 */
defineExpose({ visible, data })
</script>

<template>
  <el-dialog v-model="visible" :title="data?.id ? '编辑' : '新增'" destroy-on-close align-center @opened="onOpened"
    @closed="onClosed" width="40%">
    <el-form v-loading="loading" ref="formRef" :model="form" label-width="auto">

      <el-form-item prop="code" label="字典编码" :rules="[{ required: true, message: '必填', trigger: 'blur' }]">
        <el-input v-model="form.code" clearable disabled="" maxlength="100" autocomplete="off" />
      </el-form-item>

      <el-form-item prop="label" label="字典标签" :rules="[{ required: true, message: '必填', trigger: 'blur' }]">
        <el-input v-model="form.label" clearable maxlength="100" autocomplete="off" />
      </el-form-item>

      <el-form-item prop="val" label="字典值">
        <el-input v-model="form.val" type="textarea" :autosize="{ minRows: 1, maxRows: 8 }" />
      </el-form-item>

      <el-form-item prop="tag" label="标签样式">
        <VtDictSelect v-model="form.tag" :code="'vt_dict_tag_style'"></VtDictSelect>
        <el-tag :size="'large'" :type="form.tag" effect="dark" style="margin-left: 10px;" v-if="form.tag">
          {{ isEmpty(form.label) ? form.tag : form.label }}
        </el-tag>
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

      <el-form-item prop="remark" label="备注">
        <el-input v-model="form.remark" type="textarea" :autosize="{ minRows: 3, maxRows: 8 }" />
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
