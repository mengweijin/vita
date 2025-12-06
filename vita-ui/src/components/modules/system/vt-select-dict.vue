<script setup>
import { useDictStore } from "@/store/dict-store.js";

const dictStore = useDictStore();

const props = defineProps({
	code: {
		required: true,
		type: String,
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
  <el-select v-model="selectValue" clearable :filterable="props.filterable" :multiple="props.multiple"
    :size="props.size" :style="props.style" placeholder="请选择">
    <el-option v-for="item in options" :key="item.val" :label="item.label" :value="item.val"
      :disabled="item.disabled === 'Y'" />
  </el-select>
</template>

<style scoped></style>
