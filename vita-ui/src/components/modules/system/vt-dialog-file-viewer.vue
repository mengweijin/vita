<script setup>
import { useLoginStore } from "@/store/login-store.js";
import { useFilePreviewStore } from "@/store/file-preview-store.js";

const filePreviewStore = useFilePreviewStore();
const { filePreviewDialogVisible, filePreviewId, filePreviewName } = storeToRefs(filePreviewStore);
const loginStore = useLoginStore();

const { VITE_BASE_API } = import.meta.env;

// 从 Store 中读取 id 和 name,而不是从 props
const url = computed(() => {
  if (!filePreviewId.value) {
    return "";
  }
  const fileName = encodeURIComponent(filePreviewName.value || "");
  const prefix = `${VITE_BASE_API}/system/file/preview`.replace("//", "/");
  return `${prefix}/${filePreviewId.value}/${fileName}?Authorization=${loginStore.getBearerToken()}`;
});

const onClosed = () => {
  filePreviewId.value = null;
  filePreviewName.value = null;
  filePreviewDialogVisible.value = false;
};
</script>

<template>
  <el-dialog
    :model-value="filePreviewDialogVisible"
    :title="'文件预览'"
    destroy-on-close
    align-center
    @closed="onClosed"
    width="80%"
  >
    <!-- el-dialog 的内容区域需要明确高度才能滚动 -->
    <el-scrollbar style="height: 80vh">
      <file-viewer v-if="url" :url="url" />
    </el-scrollbar>
  </el-dialog>
</template>

<style scoped></style>
