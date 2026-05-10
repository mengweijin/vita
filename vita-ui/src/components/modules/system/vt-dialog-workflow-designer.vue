<script setup>
import utils from "@/utils/utils.js";
import { useLoginStore } from "@/store/login-store.js";
const loginStore = useLoginStore();

const props = defineProps({
  visible: {
    type: Boolean,
    required: true,
  },
  id: {
    type: String,
    default: "",
  },
  onlyDesignShow: {
    type: Boolean,
    default: false,
  },
  disabled: {
    type: Boolean,
    default: false,
  },
  title: {
    type: String,
    default: "流程定义",
  },
});

const emit = defineEmits(["update:visible", "refresh"]);

const { VITE_BASE_API } = import.meta.env;

const url = computed(() => {
  let basePath = utils.trimSpecified(VITE_BASE_API, "/");
  let bearerToken = loginStore.getBearerToken();
  return `${basePath}/warm-flow-ui/index.html?id=${props.id}&onlyDesignShow=${props.onlyDesignShow}&disabled=${props.disabled}&Authorization=${bearerToken}`;
});

const onClosed = () => {
  emit("update:visible", false);
};

const iframeLoaded = () => {
  // iframe监听组件内设计器保存事件
  window.onmessage = (event) => {
    switch (event.data.method) {
      case "close":
        emit("refresh");
        emit("update:visible", false);
        break;
    }
  };
};

onMounted(() => {
  iframeLoaded();
});
</script>

<template>
  <el-dialog
    :model-value="visible"
    :title="title"
    destroy-on-close
    align-center
    @closed="onClosed"
    fullscreen
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
  height: calc(100vh - 80px);
}
</style>
