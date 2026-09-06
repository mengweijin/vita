<script setup>
import { formWorkflowApi } from "@/api/workflow/form-workflow-api.js";
import utils from "@/utils/utils.js";

const props = defineProps({
  visible: {
    type: Boolean,
    required: true,
  },
  data: {
    type: Object,
    default: null,
  },
});

const emit = defineEmits(["update:visible", "refresh"]);

// 是否为编辑态（有 id 视为编辑）。!! 是 JavaScript 里快速把一个值转换成布尔值（true/false）的简写，本质是两次取反
const isEdit = computed(() => !!props.data?.id);

const INITIAL_FORM = {
  id: undefined,
  name: undefined,
  routePath: undefined,
  remark: undefined,
};

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则影响重置 */
const form = ref({ ...INITIAL_FORM });

const formRef = useTemplateRef("formRef");

const onReset = () => {
  formRef.value?.resetFields();
  // 重置后清除验证错误
  formRef.value?.clearValidate();
};

const onClosed = () => {
  emit("update:visible", false);
};

const onSubmit = async () => {
  const valid = await formRef.value.validate().catch(() => false);
  if (valid) {
    if (isEdit.value) {
      await formWorkflowApi.update(form.value);
    } else {
      await formWorkflowApi.create(form.value);
    }
    emit("refresh");
    emit("update:visible", false);
  }
};

// 监听 data 变化：回填表单或重置
watch(
  () => props.data,
  (val) => {
    if (val) {
      // 填充表单，仅挑选 INITIAL_FORM 中定义的字段以避免冗余提交
      form.value = utils.pick(val, Object.keys(INITIAL_FORM));
    } else {
      // 当关闭弹窗或清除数据时，重置表单
      formRef.value?.clearValidate();
      form.value = { ...INITIAL_FORM };
    }
  },
  { immediate: true },
);
</script>

<template>
  <el-dialog
    :model-value="visible"
    :title="isEdit ? '编辑' : '新增'"
    destroy-on-close
    align-center
    @closed="onClosed"
    width="40%"
  >
    <el-form ref="formRef" :model="form" label-width="auto">
      <el-form-item
        prop="name"
        label="表单名称"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <el-input v-model="form.name" clearable maxlength="30" autocomplete="off" />
      </el-form-item>
      <el-form-item
        prop="routePath"
        label="路由路径"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <el-input v-model="form.routePath" clearable autocomplete="off" />
      </el-form-item>
      <el-form-item prop="remark" label="备注">
        <el-input
          v-model="form.remark"
          type="textarea"
          :readonly="props.readonly"
          :autosize="{ minRows: 3, maxRows: 8 }"
        />
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
        <el-button type="warning" @click="onReset">
          <template #icon>
            <el-icon>
              <Icon icon="ep:refresh-left"></Icon>
            </el-icon>
          </template>
          重置
        </el-button>
        <el-button type="info" @click="onClosed">
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
