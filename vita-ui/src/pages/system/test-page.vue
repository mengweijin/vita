<route lang="yaml">
meta:
  title: 表单加载器测试页
</route>

<script setup>
const testMessage = ref("这是一个测试页面，用于验证 VtFormLoader 的功能");

defineProps({
  customTitle: {
    type: String,
    default: "默认标题"
  },
  showExtra: {
    type: Boolean,
    default: false
  }
});
</script>

<template>
  <el-card>
    <template #header>
      <h2>{{ customTitle }}</h2>
    </template>
    
    <div style="padding: 20px">
      <el-alert
        :title="testMessage"
        type="success"
        :closable="false"
        style="margin-bottom: 20px"
      />
      
      <el-descriptions title="页面信息" :column="2" border>
        <el-descriptions-item label="当前时间">
          {{ new Date().toLocaleString() }}
        </el-descriptions-item>
        <el-descriptions-item label="页面路径">
          system/test-page.vue
        </el-descriptions-item>
        <el-descriptions-item label="组件状态">
          <el-tag type="success">正常运行</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="Vue 版本">
          {{ Vue.version }}
        </el-descriptions-item>
      </el-descriptions>
      
      <div v-if="showExtra" style="margin-top: 20px">
        <el-divider content-position="left">额外内容</el-divider>
        <p>这是通过 props 控制显示的额外内容区域。</p>
        <el-button type="primary">测试按钮 1</el-button>
        <el-button type="success">测试按钮 2</el-button>
      </div>
    </div>
  </el-card>
</template>

<script>
import { version as VueVersion } from 'vue'
export default {
  computed: {
    Vue() {
      return { version: VueVersion }
    }
  }
}
</script>
