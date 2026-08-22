<script setup>
const props = defineProps({
  routePath: {
    type: String,
    required: true,
    default: null,
  },
  title: {
    type: String,
    default: "表单预览",
  },
});

const visible = defineModel({ default: false, type: Boolean });

const onClosed = () => {
  visible.value = false;
};
</script>

<template>
  <el-dialog
    v-model="visible"
    :title="props.title"
    @closed="onClosed"
    destroy-on-close
    align-center
    width="60%"
  >
    <el-scrollbar style="height: calc(100vh - 300px)">
      <VtPageLoader :route-path="props.routePath" />
    </el-scrollbar>
  </el-dialog>
</template>

<style scoped></style>
