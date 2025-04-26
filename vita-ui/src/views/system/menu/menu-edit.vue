<script setup>
import { isEmpty, clone } from 'xe-utils';
import { useDictStore } from "@/store/dict-store.js";
const dictStore = useDictStore();

const props = defineProps({
  data: {
    type: Object,
  }
});

const form = ref({});

const formRef = ref(null);

const emit = defineEmits(['close']);

const closeDialog = () => {
  emit('close');
};

const resetForm = () => {
  formRef.value.resetFields();
};

const onSubmit = () => {

}

onMounted(() => {
  form.value = {
    id: props.data?.id ?? null,
    title: props.data?.title ?? '',
    type: props.data?.type ?? ['BTN'],
    disabled: props.data?.disabled ?? ['N'],
  };
});

</script>

<template>
  <!--
      1. 父组件已经使用 v-if 来控制 dialog 组件了，所以这里面直接 model-value 固定写 true，即 el-dialog 内部逻辑一直显示 dialog。
      2. 如果使用 model 控制弹层显示/隐藏：直接绑定 v-model 会有警告，使用 model-value 绑定则没有警告
   -->
  <el-dialog :model-value="true" :title="isEmpty(props.data) ? '新增' : '编辑'" width="500">
    <el-form ref="formRef" :model="form" label-width="auto" @submit.prevent="onSubmit">
      <el-form-item prop="title" label="菜单标题">
        <el-input v-model="form.title" autocomplete="off" />
      </el-form-item>
      <el-form-item prop="type" label="菜单类型">
        <el-select v-model="form.type" clearable style="width: 160px" placeholder="请选择">
          <el-option v-for="item in dictStore.getDicts('vt_menu_type')" :key="item.val" :label="item.label"
            :value="item.val" :disabled="item.disabled === 'Y'" />
        </el-select>
      </el-form-item>
      <el-form-item prop="disabled" label="菜单状态">
        <el-select v-model="form.disabled" clearable style="width: 160px" placeholder="请选择">
          <el-option v-for="item in dictStore.getDicts('vt_disabled')" :key="item.val" :label="item.label"
            :value="item.val" :disabled="item.disabled === 'Y'" />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div>
        <el-button type="primary" native-type="submit">
          <template #icon>
            <el-icon>
              <Icon icon="ep:check"></Icon>
            </el-icon>
          </template>
          确定
        </el-button>
        <el-button type="warning" @click="resetForm">
          <template #icon>
            <el-icon>
              <Icon icon="ep:refresh-left"></Icon>
            </el-icon>
          </template>
          重置
        </el-button>
        <el-button type="primary" @click="closeDialog">
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
