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

const roleList = ref([]);

const initRoleList = () => {
	roleApi.list({ disabled: "N" }).then((res) => {
		roleList.value = res;
	});
};

onMounted(() => {
	initRoleList();
});
</script>

<template>
  <el-select
    v-model="selectValue"
    clearable
    :filterable="props.filterable"
    :multiple="props.multiple"
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
