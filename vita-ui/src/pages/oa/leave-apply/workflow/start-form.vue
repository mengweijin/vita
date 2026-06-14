<route lang="yaml">
meta:
  title: 请假申请
</route>

<script setup>
import { fileApi } from "@/api/system/file-api.js";
import { useLoginStore } from "@/store/login-store.js";
import { leaveApplyApi } from "@/api/oa/leave-apply-api.js";
import utils from "@/utils/utils.js";

const loginStore = useLoginStore();

const props = defineProps({
  readonly: {
    type: Boolean,
    default: false,
  },
  businessId: {
    type: String,
    required: false,
  },
});

/** 必须先把表单字段定义出来，然后再在打开的时候赋初始值，否则影响重置 */
const INITIAL_FORM = {
  id: undefined,
  leaveType: undefined,
  startTime: undefined,
  endTime: undefined,
  leaveDays: undefined,
  remark: undefined,
  attachmentIds: [],
  workflowId: undefined,
};

const form = ref({ ...INITIAL_FORM });

const formRef = useTemplateRef("formRef");

// 是否为编辑态（有 id 视为编辑）。!! 是 JavaScript 里快速把一个值转换成布尔值（true/false）的简写，本质是两次取反
const isEdit = computed(() => !!form?.id);

const { VITE_BASE_API } = import.meta.env;
// 处理路径
let uploadUrl = `${VITE_BASE_API}/system/file/upload`.replace("//", "/");

const handleUpload = (res) => {
  ElMessage.success({ duration: 3000, message: `【${res[0]?.name}】上传成功!`, showClose: true });
  const ids = res.map((f) => f.id);

  form.value.attachmentIds.push(ids);
};

const onStart = () => {
  alert("start");
};

const onSave = () => {
  formRef.value.validate((valid, fields) => {
    if (!valid) {
      // fields 只有在验证失败的情况下才有值
      console.log(fields);
      return;
    }
    leaveApplyApi.saveOrUpdate(form).then(() => {
      alert("save");
    });
  });
};

const onReset = () => {
  formRef.value?.resetFields();
  if (!isEdit) {
    form.value = { ...INITIAL_FORM };
  }
};

watch(
  () => props.businessId,
  async (businessId) => {
    if (businessId) {
      const leaveApply = await leaveApplyApi.getById(businessId);
      // 回填表单，只回填 INITIAL_FORM 中定义的字段，避免冗余提交到后端
      form.value = utils.pick(leaveApply, Object.keys(INITIAL_FORM));
    }
  },
  { immediate: true },
);

onMounted(async () => {});
</script>

<template>
  <div class="vt-leave-apply-container">
    <el-form ref="formRef" :model="form" label-width="auto">
      <el-form-item
        prop="leaveType"
        label="休假类型"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <VtSelectDict
          v-model="form.leaveType"
          :code="'vt_oa_leave_type'"
          :disabled="props.readonly"
          :style="'width: 240px;'"
        ></VtSelectDict>
      </el-form-item>

      <el-form-item
        label="起止时间"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <el-col :span="11">
          <el-date-picker
            v-model="form.startTime"
            clearable
            :disabled="props.readonly"
            type="datetime"
            :format="'YYYY-MM-DD HH:mm'"
            placeholder="选择开始时间"
            style="width: 100%"
          />
        </el-col>
        <el-col :span="2" style="text-align: center">
          <span style="color: gray">-</span>
        </el-col>
        <el-col :span="11">
          <el-date-picker
            v-model="form.endTime"
            clearable
            :disabled="props.readonly"
            type="datetime"
            :format="'YYYY-MM-DD HH:mm'"
            placeholder="选择结束时间"
            style="width: 100%"
          />
        </el-col>
      </el-form-item>

      <el-form-item
        v-if="false"
        prop="leaveDays"
        label="休假天数（工作日）"
        :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
      >
        <el-input-number
          v-model="form.leaveDays"
          :disabled="props.readonly"
          :min="0.5"
          :step="0.5"
        />
      </el-form-item>

      <el-form-item prop="remark" label="备注">
        <el-input v-model="form.remark" type="textarea" :autosize="{ minRows: 3, maxRows: 8 }" />
      </el-form-item>
      <el-form-item prop="attachmentId" label="附件">
        <VtUpload v-model="form.attachmentIds" :disabled="props.readonly" :drag="false" />
      </el-form-item>
    </el-form>

    <div class="vt-leave-apply-button-container">
      <el-button type="primary" @click="onStart">
        <template #icon>
          <el-icon>
            <Icon icon="ep:promotion"></Icon>
          </el-icon>
        </template>
        发起流程
      </el-button>
      <el-button type="primary" @click="onSave">
        <template #icon>
          <el-icon>
            <Icon icon="ep:check"></Icon>
          </el-icon>
        </template>
        保存
      </el-button>
      <el-button type="warning" @click="onReset">
        <template #icon>
          <el-icon>
            <Icon icon="ep:refresh-left"></Icon>
          </el-icon>
        </template>
        重置
      </el-button>
    </div>
  </div>
</template>

<style scoped>
.vt-leave-apply-container {
  width: 600px;
}
.vt-leave-apply-button-container {
  display: flex;
  justify-content: flex-end;
  gap: 0px;
}
</style>
