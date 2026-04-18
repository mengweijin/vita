<script setup>
import { categoryApi } from "@/api/system/category-api.js";
import utils from "@/utils/utils.js";

const props = defineProps({
	code: {
		required: true,
		type: String,
	},
	containRootNode: {
		default: true,
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

const categoryList = ref([]);

const initCategoryList = () => {
	if (props.containRootNode) {
		categoryApi.listChildrenWithParentByCode(props.code).then((res) => {
			categoryList.value = res;
		});
	} else {
		categoryApi.listChildrenByParentCode(props.code).then((res) => {
			categoryList.value = res;
		});
	}
};

const categoryTreeSelectOptions = computed(() => {
	categoryList.value.map((item) => {
		item.disabled = item.disabled === "Y";
		return item;
	});
	utils.addFullPath(categoryList.value, { pathKey: "name" });
	const tree = utils.toArrayTree(categoryList.value, { sortKey: "seq" });
	return tree;
});

onMounted(() => {
	initCategoryList();
});
</script>

<template>
	<el-tree-select v-model="selectValue" :data="categoryTreeSelectOptions"
		:props="{ label: 'nameFullPath', value: 'id', children: 'children' }" check-strictly clearable
		:filterable="props.filterable" :multiple="props.multiple" :size="props.size" :style="props.style"
		default-expand-all placeholder="请选择">
		<template #default="{ data: { name } }">
			{{ name }}
		</template>
	</el-tree-select>
</template>

<style scoped></style>
