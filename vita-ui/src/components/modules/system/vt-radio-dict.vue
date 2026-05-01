<script setup>
import { useDictStore } from "@/store/dict-store.js";

const dictStore = useDictStore();

const props = defineProps({
  code: {
    required: true,
    type: String,
  },
  disabled: {
    default: false,
    type: Boolean,
  },
  size: {
    default: "default",
    type: String,
  },
  style: {
    default: "min-width: 200px;",
    type: String,
  },
});

const modelValue = defineModel({ type: String || Array });

const options = ref([]);

onMounted(() => {
  options.value = dictStore.get(props.code);
});
</script>

<template>
  <el-radio-group
    v-model="modelValue"
    :disabled="props.disabled"
    :size="props.size"
    :style="props.style"
  >
    <template v-for="item in options" :key="item.val">
      <el-radio :value="item.val" v-if="item.disabled === 'N'">{{ item.label }}</el-radio>
    </template>
  </el-radio-group>
</template>

<style scoped></style>
