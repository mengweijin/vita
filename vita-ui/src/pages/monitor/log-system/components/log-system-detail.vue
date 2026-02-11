<script setup>
const loading = ref(true);

const size = ref("default");

const visible = ref(false);

const data = ref({});

const onOpened = () => {
  loading.value = false;
};

const onClosed = () => {
  visible.value = false;
  data.value = {};
};

/** 暴露给父组件，父组件可通过 deptEditRef.value.visible = true; 来赋值 */
defineExpose({ data, visible });
</script>

<template>
  <el-dialog v-model="visible" :title="'系统日志详情'" destroy-on-close align-center @opened="onOpened" @closed="onClosed"
    width="80%">
    <div v-loading="loading">
      <el-descriptions title="" :column="2" :size="size" border>
        <el-descriptions-item label="日志名称" label-align="right" :span="2" min-width="100">
          {{ data?.loggerName }}
        </el-descriptions-item>
        <el-descriptions-item label="日志级别" label-align="right">
          <VtTagDict :code="'vt_log_level'" :value="data?.loggerLevel" :size="'default'"></VtTagDict>
        </el-descriptions-item>
        <el-descriptions-item label="线程名称" label-align="right" :span="2">
          {{ data?.threadName }}
        </el-descriptions-item>

        <el-descriptions-item label="日志信息" label-align="right" :span="2">
          {{ data?.formattedMessage }}
        </el-descriptions-item>

        <el-descriptions-item label="创建者" label-align="right">
          {{ data?.createByName }}
        </el-descriptions-item>
        <el-descriptions-item label="创建时间" label-align="right">
          {{ data?.createTime }}
        </el-descriptions-item>

        <el-descriptions-item label="堆栈追踪" label-align="right" :span="2">
          <el-scrollbar max-height="300px">
            <div class="vt-descriptions-item">{{ data?.stackTrace }}</div>
          </el-scrollbar>
        </el-descriptions-item>
      </el-descriptions>
    </div>
  </el-dialog>
</template>

<style scoped>
.vt-descriptions-item {
  /* 长单词自动换行 */
  word-break: break-all;
  /** 保留所有空白符 */
  white-space: pre-wrap;
}
</style>
