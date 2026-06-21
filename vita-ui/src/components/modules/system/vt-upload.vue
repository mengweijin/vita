<script setup>
import { fileApi } from "@/api/system/file-api.js";
import { useLoginStore } from "@/store/login-store.js";
import utils from "@/utils/utils.js";
import { useFilePreviewStore } from "@/store/file-preview-store.js";
const filePreviewStore = useFilePreviewStore();
const { filePreviewDialogVisible, filePreviewId, filePreviewName } = storeToRefs(filePreviewStore);

const loginStore = useLoginStore();
const { VITE_BASE_API } = import.meta.env;
// 处理路径
const uploadUrl = `${VITE_BASE_API}/system/file/upload`.replace("//", "/");

const props = defineProps({
  showFileList: {
    default: true,
    type: Boolean,
  },
  multiple: {
    default: true,
    type: Boolean,
  },
  disabled: {
    default: false,
    type: Boolean,
  },
  drag: {
    default: false,
    type: Boolean,
  },
  accept: {
    default: "",
    type: String,
  },
  /** 提示信息
   * 比如：建议文件大小不超过 10MB。
   */
  tip: {
    default: "",
    type: String,
  },
  headers: {
    default: () => {},
    type: Object,
  },
  withCredentials: {
    default: false,
    type: Boolean,
  },
  style: {
    default: "width: 100%;",
    type: String,
  },
});

const modelValue = defineModel({ type: Array, default: () => [] });

const fileList = ref([]);

const computedHeaders = computed(() => {
  return {
    ...props.headers,
    Authorization: `${loginStore.getBearerToken()}`,
  };
});

const handleSuccess = (response, uploadFile, uploadFiles) => {
  // 提取ID
  const ids = response.map((f) => f.id);
  // 合并，去重
  modelValue.value = utils.union(modelValue.value, ids);

  // 构建更友好的提示信息
  const fileNames = response.map((f) => f.name);
  let message = fileNames.join("、") + " 上传成功!";

  ElMessage.success({
    duration: 3000,
    message: message,
    showClose: true,
  });

  emit("callback");
};

const handleRemove = (uploadFile, uploadFiles) => {
  const id = uploadFile.response[0].id;
  // 从 modelValue 中移除 id
  modelValue.value = modelValue.value.filter((item) => item !== id);
};

const handlePreview = (uploadFile) => {
  const fileVO = uploadFile.response[0];
  filePreviewId.value = fileVO.id;
  filePreviewName.value = fileVO.name;
  filePreviewDialogVisible.value = true;
};

const emit = defineEmits(["callback"]);

onMounted(async () => {});
</script>

<template>
  <el-upload
    :multiple="props.multiple"
    :disabled="props.disabled"
    :drag="props.drag"
    :accept="props.accept"
    :file-list="fileList"
    :show-file-list="props.showFileList"
    :action="uploadUrl"
    :on-success="handleSuccess"
    :on-remove="handleRemove"
    :on-preview="handlePreview"
    :headers="computedHeaders"
    :with-credentials="props.withCredentials"
    :style="style"
    :class="{ 'vt-upload-drag': props.drag }"
  >
    <template v-if="props.drag">
      <el-icon :size="45">
        <Icon icon="ep:upload-filled" style="color: var(--vt-primary-color)"></Icon>
      </el-icon>
      <div class="el-upload__text" style="margin-top: -10px">
        将文件拖放至此处 或 <em>点击上传</em>
      </div>
    </template>
    <el-button v-else type="primary">
      <el-icon :size="20"><Icon icon="ep:upload-filled"></Icon></el-icon>
      <span style="margin-left: 5px">点击上传</span>
    </el-button>

    <!-- 只有当 tip 有值时才渲染提示区域 -->
    <template v-if="props.tip" #tip>
      <div class="el-upload__tip">{{ props.tip }}</div>
    </template>
  </el-upload>
</template>

<style scoped>
/* 调整拖拽上传区域的高度 */
.vt-upload-drag :deep(.el-upload-dragger) {
  padding: 10px;
}
</style>
