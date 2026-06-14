<script setup>
import { fileApi } from "@/api/system/file-api.js";
import { useLoginStore } from "@/store/login-store.js";
import utils from "@/utils/utils.js";

const loginStore = useLoginStore();
const { VITE_BASE_API } = import.meta.env;
// 处理路径
const uploadUrl = `${VITE_BASE_API}/system/file/upload`.replace("//", "/");
const previewUrl = `${VITE_BASE_API}/system/file/preview`.replace("//", "/");

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
  tip: {
    default: "建议文件大小不超过 10MB",
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

const fileViewerDialogVisible = ref(false);

const fileViewerDialogUrl = ref("");

const computedHeaders = computed(() => {
  return {
    ...props.headers,
    Authorization: `${loginStore.getBearerToken()}`,
  };
});

const handleUpload = (response, uploadFile, uploadFiles) => {
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

  console.log(modelValue);
};

const handleRemove = (uploadFile, uploadFiles) => {
  const id = uploadFile.response[0].id;
  // 从 modelValue 中移除 id
  modelValue.value = modelValue.value.filter((item) => item !== id);
  console.log(modelValue);
};

const handlePreview = (uploadFile) => {
  const fileVO = uploadFile.response[0];
  const id = fileVO.id;
  const name = fileVO.name;
  const suffix = fileVO.suffix;

  fileViewerDialogUrl.value = `${previewUrl}/${id}/${suffix}?Authorization=${loginStore.getBearerToken()}`;
  fileViewerDialogVisible.value = true;
  // fileApi.download(id, name);
};

onMounted(async () => {});
</script>

<template>
  <el-upload
    :multiple="props.multiple"
    :disabled="props.disabled"
    :drag="props.drag"
    :accept="props.accept"
    :file-list="fileList"
    :show-file-list="true"
    :action="uploadUrl"
    :on-success="handleUpload"
    :on-remove="handleRemove"
    :on-preview="handlePreview"
    :headers="computedHeaders"
    :with-credentials="props.withCredentials"
    :style="style"
  >
    <el-icon v-if="props.drag">
      <Icon icon="ep:upload-filled" style="color: var(--vt-primary-color)"></Icon>
    </el-icon>
    <el-button v-else type="primary">
      <el-icon :size="20"><Icon icon="ep:upload-filled"></Icon></el-icon>
      <span style="margin-left: 5px">点击上传</span>
    </el-button>
    <template #tip>
      <div class="el-upload__tip">{{ props.tip }}</div>
    </template>
  </el-upload>

  <VtDialogFileViewer v-model:visible="fileViewerDialogVisible" :url="fileViewerDialogUrl" />
</template>

<style scoped></style>
