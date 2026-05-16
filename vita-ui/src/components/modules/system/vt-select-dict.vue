<script setup>
import { useDictStore } from "@/store/dict-store.js";

const dictStore = useDictStore();

const props = defineProps({
  clearable: {
    default: true,
    type: Boolean,
  },
  code: {
    required: true,
    type: String,
  },
  disabled: {
    default: false,
    type: Boolean,
  },
  filterable: {
    default: false,
    type: Boolean,
  },
  multiple: {
    default: false,
    type: Boolean,
  },
  size: {
    default: "default",
    type: String,
  },
  style: {
    default: "width: 200px;",
    type: String,
  },
});

const modelValue = defineModel({ type: String || Array });

const emit = defineEmits(["change"]);

const options = ref([]);
const handleChange = (val) => {
  emit("change", val);
};

onMounted(() => {
  options.value = dictStore.get(props.code);
});
</script>

<template>
  <el-select
    v-model="modelValue"
    :clearable="props.clearable"
    :filterable="props.filterable"
    :multiple="props.multiple"
    :disabled="props.disabled"
    :size="props.size"
    :style="props.style"
    placeholder="请选择"
    @change="handleChange"
  >
    <template v-for="item in options" :key="item.val">
      <el-option :label="item.label" :value="item.val" v-if="item.disabled === 'N'" />
    </template>
  </el-select>
</template>

<style scoped></style>
