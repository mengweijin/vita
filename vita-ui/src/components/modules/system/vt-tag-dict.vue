<script setup>
import { useDictStore } from "@/store/dict-store.js";
import utils from "@/utils/utils.js";

const dictStore = useDictStore();

const props = defineProps({
  code: {
    required: true,
    type: String,
  },
  separator: {
    default: ",",
    type: String,
  },
  size: {
    default: "default",
    type: String,
  },
  value: {
    required: false,
    type: [String, Number, Array],
  },
});

const options = ref([]);

const values = computed(() => {
  if (utils.isBlank(props.value)) {
    return [];
  }
  if (Array.isArray(props.value)) {
    return props.value;
  }
  return String(props.value).split(props.separator);
});

onMounted(() => {
  options.value = dictStore.get(props.code);
});
</script>
<template>
  <div>
    <template v-for="(item, index) in options">
      <template v-if="values.includes(item.val)">
        <el-tag
          :key="item.val + ''"
          :size="props.size"
          :index="index"
          :type="item.tag"
          effect="dark"
        >
          {{ item.label + "" }}
        </el-tag>
      </template>
    </template>
  </div>
</template>
<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
</style>
