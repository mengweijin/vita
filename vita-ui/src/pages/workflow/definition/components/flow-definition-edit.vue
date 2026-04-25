<script setup>
import { flowDefinitionApi } from "@/api/workflow/flow-definition-api.js";

const loading = ref(true);

const visible = ref(false);

const data = ref({});

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则影响重置 */
const form = reactive({
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
});

const init = () => {
  form.id = data.value.id ?? undefined;
  form.flowCode = data.value.flowCode ?? undefined;
  form.flowName = data.value.flowName ?? undefined;
  form.modelValue = data.value.modelValue ?? undefined;
  form.category = data.value.category ?? undefined;
  form.formCustom = data.value.formCustom ?? undefined;
  form.formPath = data.value.formPath ?? undefined;
  form.listenerType = data.value.listenerType ?? undefined;
  form.listenerPath = data.value.listenerPath ?? undefined;
  form.ext = data.value.ext ?? undefined;
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
      flowDefinitionApi.update(form).then((r) => {
        emit("refresh-table");
        onClosed();
      });
    } else {
      flowDefinitionApi.create(form).then((r) => {
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

/** 暴露给父组件，父组件可通过 editRef.value.visible = true; 来赋值 */
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
        prop="category"
        label="流程分类"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <VtSelectCategory
          code="vt_workflow"
          v-model="form.category"
          :containRootNode="false"
          :filterable="true"
        />
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
