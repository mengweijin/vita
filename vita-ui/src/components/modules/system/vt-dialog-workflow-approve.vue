<script setup>
import { flowTaskApi } from "@/api/workflow/flow-task-api.js";

const props = defineProps({
  visible: {
    type: Boolean,
    required: true,
  },
  /** 流程任务 ID */
  taskId: {
    type: String,
    default: "",
  },
  title: {
    type: String,
    default: "流程审批",
  },
});

const loading = ref(false);
const formRef = useTemplateRef("formRef");

const form = reactive({
  message: "",
});

const emit = defineEmits(["update:visible", "refresh"]);

const onClosed = () => {
  emit("update:visible", false);
};

const onPass = () => {
  loading.value = true;
  form.message = form.message || "同意";
  flowTaskApi
    .pass(props.taskId, form.message, null)
    .then(() => {
      emit("refresh");
      onClosed();
    })
    .finally(() => {
      loading.value = false;
    });
};

const onReject = () => {
  loading.value = true;
  form.message = form.message || "不同意";
  flowTaskApi
    .reject(props.taskId, form.message, null)
    .then(() => {
      emit("refresh");
      onClosed();
    })
    .finally(() => {
      loading.value = false;
    });
};
</script>

<template>
  <el-dialog
    :model-value="visible"
    :title="title"
    destroy-on-close
    align-center
    @closed="onClosed"
    width="500px"
  >
    <el-form v-loading="loading" ref="formRef" :model="form" label-width="80px">
      <el-form-item prop="message" :label="'审批意见'">
        <el-input
          v-model="form.message"
          type="textarea"
          placeholder="请输入审批意见"
          maxlength="500"
          :autosize="{ minRows: 3, maxRows: 8 }"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <div>
        <el-button type="primary" :disabled="loading" @click="onPass">
          <template #icon>
            <el-icon>
              <Icon icon="ep:check"></Icon>
            </el-icon>
          </template>
          通过
        </el-button>
        <el-button type="danger" :disabled="loading" @click="onReject">
          <template #icon>
            <el-icon>
              <Icon icon="ep:circle-close"></Icon>
            </el-icon>
          </template>
          拒绝
        </el-button>
        <el-button type="info" :disabled="loading" @click="onClosed">
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
