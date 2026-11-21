<script setup>
import { deptApi } from "@/api/system/dept-api.js";
import utils from "@/utils/utils.js";

const props = defineProps({
  filterable: {
    default: false,
    type: Boolean,
  },
  multiple: {
    default: false,
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

const modelValue = defineModel({ type: [String, Array] });

const treeDatas = ref([]);

const toDeptArrayTree = async function () {
  const deptList = await deptApi.list({ disabled: "N" });
  const list = deptList.map((item) => ({ ...item, disabled: item.disabled === "Y" }));
  utils.addFullPath(list, { pathKey: "name" });
  return utils.toArrayTree(list, { sortKey: "seq" });
};

onMounted(async () => {
  treeDatas.value = await toDeptArrayTree();
});
</script>

<template>
  <el-tree-select
    v-model="modelValue"
    :data="treeDatas"
    :props="{ label: 'nameFullPath', value: 'id', children: 'children' }"
    check-strictly
    clearable
    :disabled="props.disabled"
    :filterable="props.filterable"
    :multiple="props.multiple"
    :size="props.size"
    :style="props.style"
    default-expand-all
    placeholder="请选择"
  >
    <template #default="{ data: { name } }">
      {{ name }}
    </template>
  </el-tree-select>
</template>

<style scoped></style>
