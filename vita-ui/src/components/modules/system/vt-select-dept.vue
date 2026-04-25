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

const deptList = ref([]);

const initDeptList = () => {
  deptApi.list({ disabled: "N" }).then((res) => {
    deptList.value = res;
  });
};

const deptTreeSelectOptions = computed(() => {
  deptList.value.forEach((item) => {
    item.disabled = false;
  });
  utils.addFullPath(deptList.value, { pathKey: "name" });
  return utils.toArrayTree(deptList.value, { sortKey: "seq" });
});

onMounted(() => {
  initDeptList();
});
</script>

<template>
  <el-tree-select
    v-model="selectValue"
    :data="deptTreeSelectOptions"
    :props="{ label: 'nameFullPath', value: 'id', children: 'children' }"
    check-strictly
    clearable
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
