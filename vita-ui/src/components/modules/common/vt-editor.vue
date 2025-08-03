<script setup>
// 引入 css
import '@wangeditor-next/editor/dist/css/style.css';
import { Editor, Toolbar } from '@wangeditor-next/editor-for-vue';

const props = defineProps({
  // 'default' 或 'simple'
  mode: {
    type: String,
    default: 'default'
  },
  toolbarConfig: {
    type: Object,
    default: {},
  },
  editorConfig: {
    type: Object,
    default: {
      placeholder: '请输入内容...',
    },
  },
});

const modelValue = defineModel({ type: String });

// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef();

onBeforeUnmount(() => {
  // 组件销毁时，也及时销毁编辑器
  editorRef.value?.destroy();
})

const handleCreated = (editor) => {
  // 记录 editor 实例，重要！
  editorRef.value = editor;
}
</script>

<template>
  <div class="vt-editor-container">
    <Toolbar :editor="editorRef" :defaultConfig="props.toolbarConfig" :mode="props.mode" class="vt-editor-toolbar" />
    <Editor v-model="modelValue" :defaultConfig="props.editorConfig" :mode="props.mode" @onCreated="handleCreated"
      class="vt-editor" />
  </div>
</template>

<style scoped>
.vt-editor-container {
  border: 1.5px solid var(--el-border-color);
  border-radius: 3px;
}

.vt-editor-toolbar {
  border-bottom: 1px solid var(--el-border-color);
}

.vt-editor {
  height: 350px !important;
  width: 100%;
  overflow-y: hidden;
}
</style>
