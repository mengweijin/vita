<script setup>
import hljs from 'highlight.js';
// 深色主题（ONE DARK 风格，最常用深色）
import utils from '@/utils/utils.js';
import 'highlight.js/styles/atom-one-dark.css';

const props = defineProps({
  // 要高亮的代码内容
  code: {
    default: '', 
    type: String
  },
  fileName: {
    default: 'download.txt', 
    type: String
  }, 
  // 手动指定语言（不填则自动检测）
  language: {
    default: '', 
    type: String
  }
});

// 直接计算高亮后的 HTML
const highlightedHtml = computed(() => {
  if (!props.code) return '';

  // 保持与原来一致的格式化（加换行并去首尾空白）
  const codeText = `\n${props.code.trim()}`;

  try {
    if (props.language) {
      const result = hljs.highlight(codeText, {
        ignoreIllegals: true,   // 避免语法错误导致中断
        language: props.language
      });
      return result.value;
    } else {
      const result = hljs.highlightAuto(codeText);
      return result.value;
    }
  } catch (e) {
    // 出错时至少做 HTML 转义，防止 XSS
    return codeText.replace(/</g, '&lt;').replace(/>/g, '&gt;');
  }
});

const handleCopy = () => {
  navigator.clipboard.writeText(props.code).then(() => {
    ElMessage.success('代码已复制到剪贴板');
  }).catch(() => {
    ElMessage.error('复制失败，请手动复制');
  });
};

const handleDownload = () => {
  utils.download(props.code, props.fileName);
};
</script>

<template>
  <div class="vt-code-header">
    <span style="margin-right: 15px;" v-if="utils.isNotBlank(props.code)">{{ props.fileName }}</span>
    <el-tooltip content="复制代码" placement="top">
      <el-button type="primary" text :size="'small'" @click="handleCopy" :disabled="utils.isBlank(props.code)">
        <template #icon>
          <el-icon :size="'small'">
            <Icon icon="ep:document-copy"></Icon>
          </el-icon>
        </template>
      </el-button>
    </el-tooltip>
    <el-tooltip content="下载代码" placement="top">
      <el-button type="primary" text :size="'small'" @click="handleDownload" :disabled="utils.isBlank(props.code)">
        <template #icon>
          <el-icon :size="'small'">
            <Icon icon="ep:download"></Icon>
          </el-icon>
        </template>
      </el-button>
    </el-tooltip>
  </div>
  <div style="height: calc(100% - 90px);">
    <el-scrollbar>
      <pre class="vt-code-pre">
        <code class="vt-code" v-html="highlightedHtml"></code>
      </pre>
    </el-scrollbar>
  </div>
</template>

<style scoped>
.vt-code-header {
  padding-left: 15px;
  height: 40px;
  line-height: 40px;
  border-radius: 8px 8px 0 0;
  color: white;
  background: #313131;
}

.vt-code-pre {
  margin: 0 0 0 0;
  padding: 0 1em 0 1em;
  background: #1e1e1e;
  overflow-x: auto;
  height: calc(var(--vt-tab-content-height) - 90px);
}
.vt-code {
  font-family: Consolas, "Courier New", monospace;
  font-size: 14px;
  line-height: 1.6;
}
</style>