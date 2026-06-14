<script setup>
const props = defineProps({
  visible: {
    default: false,
    type: Boolean,
  },
  url: {
    default: "",
    type: String,
  },
});

const emit = defineEmits(["update:visible"]);

const onClosed = () => {
  emit("update:visible", false);
};

onMounted(() => {
  console.log(props.url);
});
</script>

<template>
  <el-dialog
    :model-value="props.visible"
    :title="'文件预览'"
    destroy-on-close
    align-center
    @closed="onClosed"
    width="800px"
    style="height: 600px"
  >
    <div style="height: 100%">
      <file-viewer :url="props.url" />
    </div>
  </el-dialog>
</template>

<style scoped></style>
