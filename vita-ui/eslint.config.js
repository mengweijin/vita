// 导入基础ESLint配置（来自eslint官方推荐规则）
import js from '@eslint/js';
// 导入Vue.js ESLint插件，用于Vue单文件组件的代码检查
import vuePlugin from 'eslint-plugin-vue';
// 导入全局变量定义，如浏览器环境下的window、document等
import globals from 'globals';
// 导入Prettier配置，用于禁用与Prettier冲突的格式规则
import skipFormatting from '@vue/eslint-config-prettier/skip-formatting';
// 导入自动生成的配置（通常由unplugin-auto-import等工具生成）
// 该文件包含自动导入的Vue APIs、组件等的全局变量声明
import autoImportConfig from './eslintrc-auto-import.json';

// 导出扁平化(flat)配置数组
export default [
  // 配置1：定义需要检查的文件模式
  {
    // 配置名称（用于调试和识别）
    name: 'app/files-to-lint',
    // 指定要检查的文件模式
    // **/* 表示所有目录下的文件
    // {js,mjs,jsx,vue} 表示匹配这些扩展名的文件
    files: ['**/*.{js,mjs,jsx,vue}'],
  },
  // 配置2：定义需要忽略的目录/文件
  {
    // 配置名称
    name: 'app/files-to-ignore',
    // 忽略检查的目录模式
    // '**/dist/**' - 忽略所有dist目录（构建输出）
    // '**/dist-ssr/**' - 忽略SSR构建输出目录
    // '**/coverage/**' - 忽略测试覆盖率报告目录
    ignores: ['**/dist/**', '**/dist-ssr/**', '**/coverage/**'],
  },
  // 配置3：设置语言环境选项
  {
    languageOptions: {
      // 定义全局变量，使ESLint知道这些变量已存在
      // 这里合并了浏览器环境的全局变量（如window、document等）
      globals: {
        ...globals.browser,
      },
      // 注意：可以在此处添加其他配置，如解析器选项(parserOptions)等
    },
  },
  // 配置4：应用ESLint官方推荐的JavaScript规则
  // 这是基础规则集，包含ESLint团队推荐的常见最佳实践
  js.configs.recommended,
  // 配置5：应用Vue.js基本规则集
  // 'flat/essential'表示扁平化配置格式的基本规则
  // 包含Vue 3基本语法和防止常见错误的规则
  // 使用扩展运算符(...)将数组配置扁平化到主配置数组中
  ...vuePlugin.configs['flat/essential'],
  // 配置6：应用Prettier兼容配置
  // 禁用所有与Prettier冲突的格式相关规则
  // 确保ESLint只检查代码质量，Prettier处理代码格式
  skipFormatting,
  // 配置7：合并自动导入生成的配置
  // 该配置通常包含自动导入的Vue组合式API、组件等的全局变量声明
  // 防止ESLint将自动导入的标识符报告为未定义
  // 使用扩展运算符将配置扁平化到主配置数组中
  ...autoImportConfig,
];
