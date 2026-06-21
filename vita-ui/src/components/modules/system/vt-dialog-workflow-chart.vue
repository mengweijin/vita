<script setup>
import utils from "@/utils/utils.js";
import { useLoginStore } from "@/store/login-store.js";
const loginStore = useLoginStore();

const props = defineProps({
  visible: {
    type: Boolean,
    required: true,
  },
  /** 流程实例 ID */
  id: {
    type: String,
    default: "",
  },
  title: {
    type: String,
    default: "流程图",
  },
});

const emit = defineEmits(["update:visible"]);

const { VITE_BASE_API } = import.meta.env;

const url = computed(() => {
  let basePath = utils.trimSpecified(VITE_BASE_API, "/");
  let bearerToken = loginStore.getBearerToken();
  return `${basePath}/warm-flow-ui/index.html?id=${props.id}&type=FlowChart&Authorization=${bearerToken}&t=${new Date().getTime()}`;
});

const onClosed = () => {
  emit("update:visible", false);
};
</script>

<template>
  <el-dialog
    :model-value="visible"
    :title="title"
    destroy-on-close
    align-center
    @closed="onClosed"
    width="80%"
  >
    <div class="vt-dialog-workflow-designer-wrapper">
      <iframe
        title="warmflow"
        :src="url"
        style="width: 100%; height: 100%; border: none; display: block"
      ></iframe>
    </div>
  </el-dialog>
</template>

<style scoped>
.vt-dialog-workflow-designer-wrapper {
  height: calc(100vh - 200px);
}
</style>
