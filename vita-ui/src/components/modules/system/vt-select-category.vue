<script setup>
import { categoryApi } from "@/api/system/category-api.js";
import utils from "@/utils/utils.js";

const props = defineProps({
  code: {
    required: true,
    type: String,
  },
  containRoot: {
    default: true,
    type: Boolean,
  },
  filterable: {
    default: true,
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

const modelValue = defineModel({ type: String || Array });

const categoryList = ref([]);

const treeOptions = computed(() => {
  const list = categoryList.value.map((item) => ({ ...item, disabled: item.disabled === "Y" }));
  utils.addFullPath(list, { pathKey: "name" });
  const tree = utils.toArrayTree(list, { sortKey: "seq" });
  return tree;
});

onMounted(async () => {
  categoryList.value = await categoryApi.listChildrenByCode(props.code, props.containRoot, false);
});
</script>

<template>
  <el-tree-select
    v-model="modelValue"
    :data="treeOptions"
    :props="{ label: 'nameFullPath', value: 'id', children: 'children' }"
    check-strictly
    clearable
    :filterable="props.filterable"
    :multiple="props.multiple"
    :disabled="props.disabled"
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
