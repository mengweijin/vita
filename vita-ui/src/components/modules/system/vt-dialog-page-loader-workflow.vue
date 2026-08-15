<script setup>
import { dialogPageLoaderWorkflowBus } from "@/utils/event-bus.js";
import utils from "@/utils/utils.js";

const props = defineProps({
  definitionId: {
    type: String,
    required: true,
  },
  businessId: {
    type: String,
    required: false,
  },
  disabled: {
    type: Boolean,
    default: false,
  },
  title: {
    type: String,
    default: " ",
  },
});

const visible = defineModel({ default: false, type: Boolean });

const loading = ref(true);

const onOpened = async () => {
  loading.value = false;
};
const onClosed = () => {
  visible.value = false;
};

const onSave = () => {
  // 触发事件，通知孙子执行 onSubmit
  dialogPageLoaderWorkflowBus.emit("onSubmit");
};

const onSubmitSuccess = () => {
  onClosed();
};

onMounted(() => {
  // 组件挂载时订阅子孙组件调用 onSubmitSuccess 的事件
  dialogPageLoaderWorkflowBus.on(onSubmitSuccess);
});

// 组件卸载时取消订阅（防止内存泄漏）
onUnmounted(() => {
  dialogPageLoaderWorkflowBus.off(onSubmitSuccess);
});
</script>

<template>
  <el-dialog
    v-model="visible"
    :title="props.title"
    destroy-on-close
    align-center
    @opened="onOpened"
    @closed="onClosed"
    width="60%"
  >
    <el-scrollbar v-loading="loading" style="height: calc(100vh - 300px)">
      <VtPageLoaderWorkflow
        :definition-id="props.definitionId"
        :business-id="props.businessId"
        :disabled="props.disabled"
      />
    </el-scrollbar>
    <template #footer>
      <div>
        <el-button type="primary" @click="onSave">
          <template #icon>
            <el-icon>
              <Icon icon="ep:check"></Icon>
            </el-icon>
          </template>
          保存
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
.vt-dialog-hidden {
  display: none !important;
}
</style>
