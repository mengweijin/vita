<script setup>
const props = defineProps({
  columns: {
    type: Array,
    default: [],
  },
  size: {
    type: String,
    default: 'default'
  },
});
</script>
<template>
  <el-col :span="1.5" style="margin-left: auto;">
    <el-tooltip content="显示搜索/隐藏搜索" placement="top" v-if="false">
      <el-button text circle style="font-size: 22px;">
        <template #icon>
          <Icon icon="ep:search" width="24" height="24"></Icon>
        </template>
      </el-button>
    </el-tooltip>
    <el-tooltip content="刷新" placement="top" v-if="false">
      <el-button text circle style="font-size: 22px;">
        <template #icon>
          <Icon icon="ep:refresh" width="24" height="24"></Icon>
        </template>
      </el-button>
    </el-tooltip>
    <el-tooltip content="全屏" placement="top">
      <el-button text circle style="font-size: 22px;">
        <template #icon>
          <Icon icon="ri:fullscreen-fill" width="24" height="24"></Icon>
        </template>
      </el-button>
    </el-tooltip>
    <el-tooltip content="打印" placement="top">
      <el-button text circle style="font-size: 22px;">
        <template #icon>
          <Icon icon="ep:printer" width="24" height="24"></Icon>
        </template>
      </el-button>
    </el-tooltip>
    <el-tooltip content="密度" placement="top">
      <el-button text circle style="font-size: 22px;">
        <template #icon>
          <Icon icon="ri:expand-height-fill" width="24" height="24" />
        </template>
      </el-button>
    </el-tooltip>
    <el-tooltip content="列设置" placement="top">
      <el-button text circle style="font-size: 22px;">
        <template #icon>
          <Icon icon="ep:menu" width="24" height="24"></Icon>
        </template>
      </el-button>
    </el-tooltip>
  </el-col>
</template>
