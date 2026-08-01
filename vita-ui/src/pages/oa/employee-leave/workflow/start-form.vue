<route lang="yaml">
meta:
  title: 员工请假
</route>

<script setup>
import { employeeLeaveApi } from "@/api/oa/employee-leave-api.js";
import utils from "@/utils/utils.js";

const router = useRouter();

const loading = ref(false);

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
  leaveType: "personal_leave",
  startTime: `${utils.toDateString(new Date(), "yyyy-MM-dd")} 09:00`,
  endTime: `${utils.toDateString(new Date(), "yyyy-MM-dd")} 18:00`,
  leaveDays: 1,
  remark: undefined,
  attachmentIds: [],
  workflowId: undefined,
  createByName: undefined,
};

const form = ref({ ...INITIAL_FORM });

const formRef = useTemplateRef("formRef");

// 是否为编辑态（有 id 视为编辑）。!! 是 JavaScript 里快速把一个值转换成布尔值（true/false）的简写，本质是两次取反
const isEdit = computed(() => !!form?.id);

// 自定义验证器：验证时间范围
const validateTimeRange = (rule, value, callback) => {
  if (!form.value.startTime || !form.value.endTime) {
    callback(new Error("请选择完整的起止时间"));
  } else if (new Date(form.value.startTime) >= new Date(form.value.endTime)) {
    callback(new Error("开始时间必须早于结束时间"));
  } else {
    callback();
  }
};

const onSubmit = () => {
  formRef.value.validate((valid, fields) => {
    if (!valid) {
      // fields 只有在验证失败的情况下才有值
      console.log(fields);
      return;
    }
    employeeLeaveApi.saveWorkflow(form.value).then((r) => {
      if (r.code === 200) {
        router.push("/oa/my-workflow");
      }
    });
  });
};

// Expose a generic performAction that calls the existing onSubmit logic.
const performAction = async (...args) => {
  return await onSubmit();
};

defineExpose({ performAction });

watch(
  () => props.businessId,
  async (businessId) => {
    if (businessId) {
      loading.value = true;
      const employeeLeave = await employeeLeaveApi.getById(businessId);
      // 回填表单，只回填 INITIAL_FORM 中定义的字段，避免冗余提交到后端
      form.value = utils.pick(employeeLeave, Object.keys(INITIAL_FORM));
      loading.value = false;
    }
  },
  { immediate: true },
);
</script>

<template>
  <el-scrollbar>
    <div class="vt-leave-apply-container" v-loading="loading">
      <el-form ref="formRef" :model="form" label-width="auto">
        <el-form-item v-if="props.readonly" prop="remark" label="休假人">
          <el-input v-model="form.createByName" :readonly="true" />
        </el-form-item>
        <el-form-item
          prop="leaveType"
          label="休假类型"
          :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
        >
          <VtSelectDict
            v-model="form.leaveType"
            :code="'vt_oa_leave_type'"
            :disabled="props.readonly"
          ></VtSelectDict>
        </el-form-item>

        <el-form-item
          prop="startTime"
          label="起止时间"
          :rules="[
            { required: true, message: '请选择开始时间', trigger: 'blur' },
            { validator: validateTimeRange, trigger: 'change' },
          ]"
        >
          <el-col :span="11">
            <el-date-picker
              v-model="form.startTime"
              clearable
              :readonly="props.readonly"
              type="datetime"
              value-format="YYYY-MM-DD HH:mm"
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
              :readonly="props.readonly"
              type="datetime"
              value-format="YYYY-MM-DD HH:mm"
              :format="'YYYY-MM-DD HH:mm'"
              placeholder="选择结束时间"
              style="width: 100%"
            />
          </el-col>
        </el-form-item>

        <el-form-item
          prop="leaveDays"
          label="休假天数（工作日）"
          :rules="[{ required: true, message: '必填', trigger: 'blur' }]"
        >
          <el-input-number
            v-model="form.leaveDays"
            :readonly="props.readonly"
            :min="0.5"
            :step="0.5"
          />
        </el-form-item>

        <el-form-item prop="remark" label="备注">
          <el-input
            v-model="form.remark"
            type="textarea"
            :readonly="props.readonly"
            :autosize="{ minRows: 3, maxRows: 8 }"
          />
        </el-form-item>
        <el-form-item prop="attachmentId" label="附件">
          <VtUpload v-model="form.attachmentIds" :disabled="props.readonly" :drag="true" />
        </el-form-item>
      </el-form>
    </div>
  </el-scrollbar>
</template>

<style scoped>
.vt-leave-apply-container {
  padding: 0 15px;
  max-height: calc(100vh - 200px);
}
</style>
