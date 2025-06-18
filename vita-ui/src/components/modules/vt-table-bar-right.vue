<script setup>
import { useFullscreen } from '@vueuse/core';

const props = defineProps({
  tableRef: {
    type: Object,
    required: true,
  },
  shows: {
    type: Array,
    default: ['print', 'size', 'fullscreen', 'columns'],
  },
  columns: {
    type: Object,
    default: null,
  },
});

const emit = defineEmits(['update-size'])

const handleSizeCommand = (command) => {
  emit('update-size', command);
}

const onToggleFullscreen = async () => {
  await nextTick();
  await props.tableRef.$nextTick();
  const { toggle: toggleFullscreen } = useFullscreen(props.tableRef);
  toggleFullscreen();
}

const onPrint = () => {
  // 通过 CSS 选择器定位元素
  const element = document.querySelector('.el-table');

  if (element) {
    const printWindow = window.open('', '_blank');
    printWindow.document.write(`
      <html>
        <head>
          <title></title>
          <!-- 复制原页面的样式 -->
          ${document.head.innerHTML}
          <style type="text/css">
            .el-table {
              height: fit-content !important;
            }
            .el-table--striped .el-table__body tr.el-table__row--striped td.el-table__cell {
                background: transparent !important;
            }
          </style>
        </head>
        <body style="display: flex; justify-content: center; ">
          <div style="display: inline-block; ">
            ${element.outerHTML}
          <div>
        </body>
      </html>
    `);
    // 停止加载，否则页签一直是 loading 状态
    printWindow.document.close();

    // 监听打印后操作
    printWindow.onafterprint = function () {
      // 关闭当前页
      printWindow.close();
    };

    // 调用打印
    printWindow.print();
  } else {
    console.error(`No element was found by selector=${props.selector}`);
  }
};

const columnList = computed(() => {
  let list = [];
  for (const [key, value] of Object.entries(props.columns)) {
    list.push({ key: key, label: value.label, visible: value.visible });
  }
  return list;
});

const columnCheckedList = ref([]);

const columnChange = () => {
  for (const [key, value] of Object.entries(props.columns)) {
    value.visible = columnCheckedList.value.includes(key);
  }
}

const columnGroupChange = (checkedKeys) => {
  columnCheckedList.value = checkedKeys;
  columnChange();
}

const columnCheckAllChange = (isChecked) => {
  columnCheckedList.value = isChecked ? Object.keys(props.columns) : [];
  columnChange();
}

onMounted(() => {
  columnCheckedList.value = columnList.value?.filter((item) => item.visible).map((item) => item.key)
})
</script>

<template>
  <el-col :span="1.5" style="margin-left: auto;">

    <el-tooltip content="打印，请在打印窗口手动调整缩放比例，以适应纸张页面。" placement="top" v-if="props.shows.includes('print')">
      <el-button text circle @click="onPrint">
        <template #icon>
          <Icon icon="ep:printer" width="24" height="24"></Icon>
        </template>
      </el-button>
    </el-tooltip>

    <el-tooltip content="全屏" placement="top" v-if="props.shows.includes('fullscreen')">
      <el-button text circle @click="onToggleFullscreen">
        <template #icon>
          <Icon icon="ri:fullscreen-fill" width="24" height="24"></Icon>
        </template>
      </el-button>
    </el-tooltip>
    <el-tooltip content="密度" placement="top" v-if="props.shows.includes('size')">
      <el-dropdown placement="bottom" trigger="click" style="margin: 0 10px;" @command="handleSizeCommand">
        <el-button text circle>
          <template #icon>
            <Icon icon="ri:expand-height-fill" width="24" height="24" />
          </template>
        </el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="large">宽松</el-dropdown-item>
            <el-dropdown-item command="default">默认</el-dropdown-item>
            <el-dropdown-item command="small">紧凑</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-tooltip>

    <el-tooltip content="列设置" placement="top" v-if="props.shows.includes('columns')">
      <div style="display: inline-block;">
        <el-popover placement="bottom" trigger="click">
          <div>
            <div style="padding: 5px 0;"><span>显示/隐藏列</span></div>
            <el-divider style="margin: 0px;" />
            <div style="max-height: 260px; overflow-y: auto;">
              <el-checkbox label="全选" :value="-1" :key="-1" @change="columnCheckAllChange" />
              <el-divider style="margin: 0px;" />
              <el-checkbox-group v-model="columnCheckedList" @change="columnGroupChange">
                <el-checkbox v-for="(item, index) in columnList" :label="item.label" :value="item.key"
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
      </div>
    </el-tooltip>
  </el-col>
</template>

<style scoped>
.el-button {
  font-size: 20px;
}
</style>
