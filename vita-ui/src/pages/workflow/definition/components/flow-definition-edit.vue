<script setup>
import { flowDefinitionApi } from "@/api/workflow/flow-definition-api.js";
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
  category: undefined,
  ext: undefined,
  flowCode: undefined,
  flowName: undefined,
  formCustom: undefined,
  formPath: undefined,
  id: undefined,
  listenerPath: undefined,
  listenerType: undefined,
  modelValue: undefined,
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
      await flowDefinitionApi.update(form.value);
    } else {
      await flowDefinitionApi.create(form.value);
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
    <el-form v-loading="loading" ref="formRef" :model="form" label-width="auto">
      <el-form-item
        prop="category"
        label="流程分类"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <VtSelectCategory v-model="form.category" code="vt_workflow" :containRoot="false" />
      </el-form-item>
      <el-form-item
        prop="flowName"
        label="流程名称"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <el-input v-model="form.flowName" clearable maxlength="30" autocomplete="off" />
      </el-form-item>
      <el-form-item
        prop="flowCode"
        label="流程编码"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <el-input v-model="form.flowCode" clearable maxlength="30" autocomplete="off" />
      </el-form-item>
      <el-form-item
        prop="modelValue"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <template #label>
          <div class="vt-question-icon-container">
            <span>设计器模型</span>
            <el-tooltip placement="top">
              <template #content>
                设计器模型一旦选定保存后，就不能修改了，因为流程定义和流程实例都要关联这个设计器模型。<br />
                如果需要修改设计器模型，可以先删除流程定义，然后重新创建流程定义。
              </template>
              <el-icon class="vt-question-icon">
                <Icon icon="ep:question-filled" width="24" height="24" />
              </el-icon>
            </el-tooltip>
          </div>
        </template>
        <VtRadioDict
          :code="'vt_warmflow_designer_model'"
          :disabled="form.id"
          v-model="form.modelValue"
        />
      </el-form-item>
      <el-form-item
        prop="formCustom"
        label="表单是否自定义"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <VtRadioDict :code="'vt_warmflow_form_custom'" v-model="form.formCustom" />
      </el-form-item>
      <el-form-item
        prop="formPath"
        label="表单路径"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
        v-if="form.formCustom === 'Y'"
      >
        <el-input v-model="form.formPath" clearable maxlength="30" autocomplete="off" />
      </el-form-item>
      <el-form-item
        prop="listenerType"
        label="监听器类型"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <el-input v-model="form.listenerType" clearable maxlength="30" autocomplete="off" />
      </el-form-item>
      <el-form-item
        prop="listenerPath"
        label="监听器路径"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <el-input v-model="form.listenerPath" clearable maxlength="30" autocomplete="off" />
      </el-form-item>
      <el-form-item
        prop="ext"
        label="扩展属性"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <el-input v-model="form.ext" clearable maxlength="30" autocomplete="off" />
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

<style scoped>
.vt-question-icon-container {
  display: flex;
  align-items: center;
}

.vt-question-icon:hover {
  /* 默认显示手型 */
  cursor: pointer;
}
</style>
