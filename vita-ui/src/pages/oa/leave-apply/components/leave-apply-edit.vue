<script setup>
import { leaveApplyApi } from "@/api/oa/leave-apply-api.js";
import utils from "@/utils/utils.js";

const loading = ref(false);

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

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则影响重置 */
const INITIAL_FORM = {
  id: undefined,
  leaveType: undefined,
  startTime: undefined,
  endTime: undefined,
  leaveDays: undefined,
  remark: undefined,
  attachmentIds: undefined,
  workflowId: undefined,
};

const form = ref({ ...INITIAL_FORM });

const formRef = useTemplateRef("formRef");

// 是否为编辑态（有 id 视为编辑）。!! 是 JavaScript 里快速把一个值转换成布尔值（true/false）的简写，本质是两次取反
const isEdit = computed(() => !!props.data?.id);

// 重置
const onReset = () => {
  formRef.value?.resetFields();
  if (!isEdit) {
    form.value = { ...INITIAL_FORM };
  }
};

// 取消
const onCancel = () => {
  emit("update:visible", false);
};

// 提交
const onSubmit = () => {
  formRef.value.validate((valid, fields) => {
    if (!valid) {
      // fields 只有在验证失败的情况下才有值
      console.log(fields);
      return;
    }
    if (isEdit) {
      leaveApplyApi.update(form).then((r) => {
        emit("refresh");
        emit("update:visible", false);
      });
    } else {
      leaveApplyApi.create(form).then((r) => {
        emit("refresh");
        emit("update:visible", false);
      });
    }
  });
};

// 监听 data 变化：回填表单或重置
watch(
  () => props.data,
  (val) => {
    if (val) {
      // 回填表单，只回填 INITIAL_FORM 中定义的字段，避免冗余提交到后端
      form.value = utils.pick(val, Object.keys(INITIAL_FORM));
    } else {
      formRef.value?.resetFields();
      form.value = { ...INITIAL_FORM };
    }
  },
  { immediate: true },
);

onMounted(() => {});
</script>

<template>
  <el-dialog
    :model-value="visible"
    :title="isEdit ? '编辑' : '新增'"
    destroy-on-close
    align-center
    width="40%"
  >
    <div v-loading="loading" class="dialog-wrap">
      <el-form v-loading="loading" ref="formRef" :model="form" label-width="auto">
        <el-form-item
          prop="leaveType"
          label="休假类型"
          :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
        >
          <el-input v-model="form.leaveType" clearable maxlength="30" autocomplete="off" />
        </el-form-item>
        <el-form-item
          prop="startTime"
          label="开始时间"
          :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
        >
          <el-input v-model="form.startTime" clearable maxlength="30" autocomplete="off" />
        </el-form-item>
        <el-form-item
          prop="endTime"
          label="结束时间"
          :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
        >
          <el-input v-model="form.endTime" clearable maxlength="30" autocomplete="off" />
        </el-form-item>
        <el-form-item
          prop="leaveDays"
          label="休假天数"
          :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
        >
          <el-input v-model="form.leaveDays" clearable maxlength="30" autocomplete="off" />
        </el-form-item>
        <el-form-item prop="remark" label="备注">
          <el-input v-model="form.remark" clearable maxlength="30" autocomplete="off" />
        </el-form-item>
        <el-form-item prop="attachmentIds" label="附件">
          <el-input v-model="form.attachmentIds" clearable maxlength="30" autocomplete="off" />
        </el-form-item>
      </el-form>
    </div>

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
        <el-button type="primary" @click="onCancel">
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
