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
  showDisabled: {
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

const selectValue = defineModel({ type: String || Array });

const options = ref([]);

onMounted(() => {
  options.value = dictStore.get(props.code);
});
</script>

<template>
  <el-radio-group
    v-model="selectValue"
    :disabled="props.disabled"
    :size="props.size"
    :style="props.style"
  >
    <template v-for="item in options" :key="item.val">
      <template v-if="item.disabled === 'Y'">
        <el-radio :value="item.val" disabled v-show="props.showDisabled">{{ item.label }}</el-radio>
      </template>
      <template v-else>
        <el-radio :value="item.val">{{ item.label }}</el-radio>
      </template>
    </template>
  </el-radio-group>
</template>

<style scoped></style>
