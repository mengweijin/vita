<script setup>
import { roleApi } from "@/api/system/role-api.js";

const props = defineProps({
  filterable: {
    default: true,
    type: Boolean,
  },
  multiple: {
    default: true,
    type: Boolean,
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

const roleList = ref([]);

onMounted(async () => {
  roleList.value = await roleApi.list({ disabled: "N" });
});
</script>

<template>
  <el-select
    v-model="modelValue"
    clearable
    :filterable="props.filterable"
    :multiple="props.multiple"
    :disabled="props.disabled"
    :size="props.size"
    :style="props.style"
    placeholder="请选择"
  >
    <el-option
      v-for="item in roleList"
      :key="item.id"
      :label="`${item.name}`"
      :value="item.id"
      :disabled="item.disabled === 'Y'"
    />
  </el-select>
</template>

<style scoped></style>
