<script setup>
import { postApi } from "@/api/system/post-api.js";

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

const postList = ref([]);

const initPostList = () => {
	postApi.list({ disabled: "N" }).then((res) => {
		postList.value = res;
	});
};

onMounted(() => {
	initPostList();
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
      v-for="item in postList"
      :key="item.id"
      :label="`${item.name}`"
      :value="item.id"
      :disabled="item.disabled === 'Y'"
    />
  </el-select>
</template>

<style scoped></style>
