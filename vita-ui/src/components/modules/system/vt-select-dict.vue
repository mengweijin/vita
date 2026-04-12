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
	filterable: {
		default: false,
		type: Boolean,
	},
	multiple: {
		default: false,
		type: Boolean,
	},
	showDisabled: {
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

const options = ref([]);

onMounted(() => {
	options.value = dictStore.get(props.code);
});
</script>

<template>
	<el-select v-model="selectValue" :clearable="props.clearable" :filterable="props.filterable"
		:multiple="props.multiple" :size="props.size" :style="props.style" placeholder="请选择">
		<template v-for="item in options" :key="item.val">
			<template v-if="item.disabled === 'Y'">
				<el-option :label="item.label" :value="item.val" :disabled="true" v-show="props.showDisabled"/>
			</template>
			<template v-else>
				<el-option :label="item.label" :value="item.val" :disabled="false"/>
			</template>
		</template>
	</el-select>
</template>

<style scoped></style>
