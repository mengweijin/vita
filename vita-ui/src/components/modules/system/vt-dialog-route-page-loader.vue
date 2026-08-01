<script setup>
const props = defineProps({
  visible: {
    type: Boolean,
    required: true,
  },
  /** 流程表单路由路径 */
  routePath: {
    type: String,
    default: "",
  },
  /** 业务 ID */
  businessId: {
    type: String,
    default: "",
  },
  readonly: {
    type: Boolean,
    default: false,
  },
  title: {
    type: String,
    default: "流程表单",
  },
});

const emit = defineEmits(["callback", "update:visible"]);

const loading = ref(false);
const loaderRef = ref(null);

const onSave = async () => {
  loading.value = true;
  await loaderRef.value.performAction();
  emit("callback");
  loading.value = false;
  emit("update:visible", false);
};

const onCancel = () => {
  emit("update:visible", false);
};

const loadDone = () => {
  loading.value = false;
};
</script>

<template>
  <el-dialog :model-value="visible" :title="title" destroy-on-close align-center>
    <VtRoutePageLoader
      ref="loaderRef"
      v-loading="loading"
      :route-path="props.routePath"
      :readonly="props.readonly"
      :businessId="props.businessId"
      @callback="loadDone"
    />
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
        <el-button type="info" @click="onCancel">
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
