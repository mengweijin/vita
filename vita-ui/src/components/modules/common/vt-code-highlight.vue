<script setup>
import hljs from 'highlight.js';
import { onMounted, watch } from 'vue';
// 核心：引入所有语言支持（支持任意语言）
import 'highlight.js/lib/common';
// 深色主题（ONE DARK 风格，最常用深色）
import 'highlight.js/styles/atom-one-dark.css';

const props = defineProps({
  // 代码内容（必填）
  code: {
    type: String,
    required: true,
    default: ''
  },
  // 代码语言（如 java、js、vue、python 等）
  language: {
    type: String,
    default: 'plaintext' // 默认纯文本
  },
  // 是否显示语言标签头部
  showHeader: {
    type: Boolean,
    default: true
  }
})

const codeRef = useTemplateRef("codeRef");

// 高亮代码核心方法
const highlight = () => {
  if (!codeRef.value) { 
	return;
  }
  // 赋值代码
  codeRef.value.textContent = props.code;
  // 自动高亮
  hljs.highlightElement(codeRef.value);
}

// 初始化高亮
onMounted(() => highlight());

// 代码/语言变化时自动重新高亮
watch(
  () => [props.code, props.language],
  () => highlight(),
  { deep: true }
)
</script>

<template>
  <div class="vt-code-box">
    <!-- 代码头部：显示语言类型 -->
    <div class="code-header" v-if="showHeader">
      <span class="lang-tag">{{ language.toUpperCase() }}</span>
    </div>
    <!-- 代码内容区域 -->
    <pre class="code-pre">
      <code 
        ref="codeRef" 
        :class="`language-${language}`" 
        class="code-block"
      ></code>
    </pre>
  </div>
</template>

<style scoped>
.vt-code-box {
  /* margin: 12px 0; */
  border-radius: 8px;
  overflow: auto;
  /* 深色背景统一 */
  background: #282c34; 
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
  height: 100%;
}

/* 语言标签头部 */
.code-header {
  padding: 8px 16px;
  background: #21252b;
  border-bottom: 1px solid #333842;
}

.lang-tag {
  font-size: 12px;
  color: #abb2bf;
  font-family: 'Consolas', monospace;
}

/* 代码区域样式 */
.code-pre {
  margin: 0;
  /* padding: 16px; */
  /* 长代码自动横向滚动 */
  /* overflow-x: auto;  */
  overflow: auto;
}

.code-block {
  font-size: 14px;
  line-height: 1.6;
  font-family: 'Consolas', 'Monaco', monospace;
  color: #abb2bf;
  overflow: auto;
}
</style>