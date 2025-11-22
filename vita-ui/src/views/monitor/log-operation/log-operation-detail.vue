<script setup>
import "vue-json-pretty/lib/styles.css";
import VueJsonPretty from "vue-json-pretty";
import utils from "@/utils/utils.js";

const loading = ref(true);

const size = ref('default');

const visible = ref(false);

const data = ref({});

const onOpened = () => {
  loading.value = false;
}

const onClosed = () => {
  visible.value = false;
  data.value = {};

}

/** 暴露给父组件，父组件可通过 deptEditRef.value.visible = true; 来赋值 */
defineExpose({ visible, data })
</script>

<template>
  <el-dialog v-model="visible" :title="'操作日志详情'" destroy-on-close align-center @opened="onOpened" @closed="onClosed"
    width="80%">
    <div v-loading="loading">
      <el-descriptions title="" :column="2" :size="size" border>
        <el-descriptions-item label="模块名称" label-align="right" min-width="100">
          {{ data?.title }}
        </el-descriptions-item>
        <el-descriptions-item label="操作类型" label-align="right">
          <VtTagDict :code="'vt_operation_log_type'" :value="data?.operationType" :size="'default'"></VtTagDict>
        </el-descriptions-item>

        <el-descriptions-item label="请求方法名称" label-align="right" :span="2">
          {{ data?.methodName }}
        </el-descriptions-item>

        <el-descriptions-item label=" 请求 URL" label-align="right">
          {{ data?.url }}
        </el-descriptions-item>
        <el-descriptions-item label="http 请求方式" label-align="right">
          <VtTagDict :code="'vt_http_request_type'" :value="data?.httpMethod" :size="'default'"></VtTagDict>
        </el-descriptions-item>

        <el-descriptions-item label=" 请求数据" label-align="right">
          <template v-if="data?.requestData != null">
            <vue-json-pretty v-if="utils.isJSON(data?.requestData)" :data="JSON.parse(data?.requestData)" />
            <div v-else>{{ data?.requestData }}</div>
          </template>
        </el-descriptions-item>
        <el-descriptions-item label="响应数据" label-align="right">
          <template v-if="data?.responseData != null">
            <vue-json-pretty v-if="utils.isJSON(data?.responseData)" :data="JSON.parse(data?.responseData)" />
            <div v-else>{{ data?.responseData }}</div>
          </template>
        </el-descriptions-item>

        <el-descriptions-item label="执行消耗时间" label-align="right">
          {{ data?.costTime }} 毫秒
        </el-descriptions-item>
        <el-descriptions-item label="操作是否成功" label-align="right">
          <VtTagDict :code="'vt_succeeded'" :value="data?.success" :size="'default'"></VtTagDict>
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
