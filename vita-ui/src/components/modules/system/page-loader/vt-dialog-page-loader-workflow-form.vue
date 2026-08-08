<script setup>
const props = defineProps({
  definitionId: {
    type: String,
    required: true,
  },
  businessId: {
    type: String,
    required: false,
  },
  api: {
    type: Object,
    required: false,
  },
  readonly: {
    type: Boolean,
    default: false,
  },
  title: {
    type: String,
    default: " ",
  },
});

const visible = defineModel({ default: false, type: Boolean });

const onOpen = () => {};

const onClosed = () => {
  visible.value = false;
};
</script>

<template>
  <el-dialog
    v-model="visible"
    :title="props.title"
    destroy-on-close
    align-center
    @open="onOpen"
    @closed="onClosed"
    width="60%"
  >
    <el-scrollbar height="450px">
      <VtPageLoaderWorkflowForm
        :definition-id="props.definitionId"
        :business-id="props.businessId"
        :api="props.api"
        :readonly="props.readonly"
      />
    </el-scrollbar>
    <template #footer>
      <div v-if="false">
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

<style scoped></style>
