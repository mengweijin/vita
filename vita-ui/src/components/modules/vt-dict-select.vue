<script setup>
import { useDictStore } from "@/store/dict-store.js";
const dictStore = useDictStore();

const props = defineProps({
  code: {
    type: String,
    required: true,
  },
  size: {
    type: String,
    default: 'default'
  },
  filterable: {
    type: Boolean,
    default: false,
  },
  multiple: {
    type: Boolean,
    default: false,
  },
  style: {
    type: String,
    default: 'width: 160px;',
  },
});

const selectValue = defineModel({ type: String || Array });

const options = ref([]);

onMounted(() => {
  options.value = dictStore.getDicts(props.code);
});

</script>

<template>
  <el-select v-model="selectValue" clearable :filterable="props.filterable" :multiple="props.multiple"
    :size="props.size" :style="props.style" placeholder="请选择">
    <el-option v-for="item in options" :key="item.val" :label="item.label" :value="item.val"
      :disabled="item.disabled === 'Y'" />
  </el-select>
</template>

<style scoped></style>
