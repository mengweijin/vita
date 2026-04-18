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
	showDisabled: {
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

const selectValue = defineModel({ type: String || Array });

const options = ref([]);

onMounted(() => {
	options.value = dictStore.get(props.code);
});
</script>

<template>
	<el-select v-model="selectValue" :clearable="props.clearable" :filterable="props.filterable"
		:multiple="props.multiple" :disabled="props.disabled" :size="props.size" :style="props.style" placeholder="请选择">
		<template v-for="item in options" :key="item.val">
			<template v-if="item.disabled === 'Y'">
				<el-option :label="item.label" :value="item.val" disabled v-show="props.showDisabled" />
			</template>
			<template v-else>
				<el-option :label="item.label" :value="item.val" />
			</template>
		</template>
	</el-select>
</template>

<style scoped></style>
