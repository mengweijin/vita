<script setup>
import utils from '@/utils/utils.js';
import { useDictStore } from '@/store/dict-store.js';

const dictStore = useDictStore();

const props = defineProps({
  code: {
    type: String,
    required: true,
  },
  value: {
    type: String,
    required: true,
  },
  size: {
    type: String,
    default: 'default',
  },
  separator: {
    type: String,
    default: ',',
  },
});

const options = ref([]);

const values = computed(() => {
  if (!props.value || utils.isEmpty(props.value)) {
    return [];
  }
  if (Array.isArray(props.value)) {
    return props.value;
  } else {
    return String(props.value).split(props.separator);
  }
});

onMounted(() => {
  options.value = dictStore.get(props.code);
});
</script>
<template>
  <div>
    <template v-for="(item, index) in options">
      <template v-if="values.includes(item.val)">
        <el-tag :key="item.val + ''" :size="props.size" :index="index" :type="item.tag" effect="dark">
          {{ item.label + '' }}
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
