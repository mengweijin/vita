<script setup>
import VueJsonPretty from "vue-json-pretty";
import "vue-json-pretty/lib/styles.css";

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
  <el-dialog v-model="visible" :title="'数据变动日志详情'" destroy-on-close align-center @opened="onOpened" @closed="onClosed"
    width="80%">
    <div v-loading="loading">
      <el-descriptions title="" :column="2" :size="size" border>
        <el-descriptions-item label="表名称" label-align="right" min-width="100">
          {{ data?.tableName }}
        </el-descriptions-item>
        <el-descriptions-item label="业务数据 ID" label-align="right">
          {{ data?.businessId }}
        </el-descriptions-item>

        <el-descriptions-item label="变更记录数据" label-align="right">
          <el-scrollbar max-height="300px">
            <template v-if="data?.changeData != null">
              <vue-json-pretty :data="data?.changeData" />
            </template>
          </el-scrollbar>
        </el-descriptions-item>
        <el-descriptions-item label="变更阅读信息" label-align="right">
          <el-scrollbar max-height="300px">
            <template v-if="data?.readableMessages != null">
              <vue-json-pretty :data="data?.readableMessages" />
            </template>
          </el-scrollbar>
        </el-descriptions-item>

        <el-descriptions-item label="创建者" label-align="right">
          {{ data?.createByName }}
        </el-descriptions-item>
        <el-descriptions-item label="创建时间" label-align="right">
          {{ data?.createTime }}
        </el-descriptions-item>

        <el-descriptions-item label="更新者" label-align="right">
          {{ data?.updateByName }}
        </el-descriptions-item>
        <el-descriptions-item label="更新时间" label-align="right">
          {{ data?.updateTime }}
        </el-descriptions-item>
        <el-descriptions-item label="失败信息" label-align="right" :span="2" v-if="data?.success === 'N'">
          <div class="vt-descriptions-item">
            {{ data?.errorMsg }}
          </div>
        </el-descriptions-item>
      </el-descriptions>
    </div>
  </el-dialog>
</template>

<style scoped>
.vt-descriptions-item {
  /* 根据需求调整高度 */
  max-height: 350px;
  /* 启用水平滚动 */
  overflow-x: auto;
  /* 启用垂直滚动 */
  overflow-y: auto;
  /* 长单词自动换行 */
  word-break: break-all;
  /** 保留所有空白符 */
  white-space: pre-wrap;
}
</style>
