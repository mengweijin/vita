<script setup>

const props = defineProps({
  showSearch: {
    type: Boolean,
    default: false,
  },
  refresh: {
    type: Boolean,
    default: true,
  },
  columns: {
    type: Array,
    default: null,
  },
  size: {
    type: String,
    default: 'default'
  },
});

const columnCheckedList = ref([]);

const columnPopoverRef = ref(null);

const columnChange = () => {
  props.columns?.forEach((item) => {
    item.visible = columnCheckedList.value.includes(item.key);
  });
  // 关闭 popover
  // columnPopoverRef.value.hide();
}

const columnGroupChange = (checkedKeys) => {
  columnCheckedList.value = checkedKeys;
  columnChange();
}

const columnCheckAllChange = (isChecked) => {
  if (isChecked) {
    columnCheckedList.value = props.columns?.map(item => item.key);
  } else {
    columnCheckedList.value = [];
  }
  columnChange();
}

onMounted(async () => {
  columnCheckedList.value = props.columns?.filter((item) => item.visible).map((item) => item.key);
})


</script>
<template>
  <el-col :span="1.5" style="margin-left: auto;">
    <el-tooltip content="显示搜索/隐藏搜索" placement="top" v-if="props.showSearch">
      <el-button text circle>
        <template #icon>
          <Icon icon="ep:search" width="24" height="24"></Icon>
        </template>
      </el-button>
    </el-tooltip>
    <el-tooltip content="刷新" placement="top" v-if="props.refresh">
      <el-button text circle>
        <template #icon>
          <Icon icon="ep:refresh" width="24" height="24"></Icon>
        </template>
      </el-button>
    </el-tooltip>
    <el-tooltip content="全屏" placement="top">
      <el-button text circle>
        <template #icon>
          <Icon icon="ri:fullscreen-fill" width="24" height="24"></Icon>
        </template>
      </el-button>
    </el-tooltip>
    <el-tooltip content="打印" placement="top">
      <el-button text circle>
        <template #icon>
          <Icon icon="ep:printer" width="24" height="24"></Icon>
        </template>
      </el-button>
    </el-tooltip>
    <el-tooltip content="密度" placement="top" v-if="false">
      <el-button text circle>
        <template #icon>
          <Icon icon="ri:expand-height-fill" width="24" height="24" />
        </template>
      </el-button>
    </el-tooltip>
    <el-tooltip v-if="props.columns" content="列设置" placement="top">
      <el-popover ref="columnPopoverRef" placement="bottom" trigger="hover">
        <div>
          <div style="padding: 5px 0;"><span>列设置</span></div>
          <el-divider style="margin: 0px;" />
          <div style="max-height: 260px; overflow-y: auto;">
            <el-checkbox label="全选" :value="-1" :key="-1" @change="columnCheckAllChange" />
            <el-checkbox-group v-model="columnCheckedList" @change="columnGroupChange">
              <el-checkbox v-for="(item, index) in props.columns" :label="item.label" :value="item.key"
                :key="item.key" />
            </el-checkbox-group>
          </div>
        </div>
        <template #reference>
          <el-button text circle>
            <template #icon>
              <Icon icon="ep:menu" width="24" height="24"></Icon>
            </template>
          </el-button>
        </template>
      </el-popover>
    </el-tooltip>
  </el-col>
</template>

<style scoped>
.el-button {
  font-size: 20px;
}
</style>
