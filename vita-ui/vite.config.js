import { fileURLToPath, URL } from 'node:url'

import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import legacy from '@vitejs/plugin-legacy'
import svgLoader from 'vite-svg-loader'

import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

import Icons from 'unplugin-icons/vite'
import IconsResolver from 'unplugin-icons/resolver'

// https://cn.vitejs.dev/config/
export default defineConfig(({ mode }) => {
  // 根据当前工作目录中的 `mode` 加载 .env 文件
  // 以 process.cwd() 作为环境变量文件的根目录。
  // 设置第三个参数为 '' 来加载所有环境变量，而不管是否有 `VITE_` 前缀。
  // eslint-disable-next-line no-undef
  const env = loadEnv(mode, process.cwd(), '')

  return {
    /**
     * 资源路径前缀：base 值为所有静态资源添加统一的前缀路径。例如，配置 base: '/my-app/' 后，所有资源路径会变为 /my-app/assets/xxx。这对部署到子目录或 CDN 的场景至关重要。
     * 与路由配置协同：若项目使用 Vue Router 的 history 模式，需同时配置路由的 base 选项，确保前端路由路径与静态资源路径一致，避免页面空白或资源加载失败。
     * 环境区分：可通过 .env 文件（如 .env.production ）动态设置 base，实现开发环境与生产环境路径的自动切换
     *
     * 注意事项
     * 斜杠结尾：base 值需以斜杠结尾（如 /subdir/），否则可能引发路径错误。
     * 与路由同步：若使用 Vue Router，需设置 createWebHistory('/subdir/')，使路由路径与资源路径匹配。
     * 缓存问题：修改 base 后需清理浏览器缓存，避免旧路径资源被强缓存影响加载
     */
    base: env.VITE_PUBLIC_PATH,
    resolve: {
      alias: {
        /**
         * import.meta.url 表示当前模块的 URL。
         * new URL(file, import.meta.url) ：创建一个基于 import.meta.url 的绝对 URL 对象。
         * fileURLToPath(...)：将 URL 对象转换为文件系统路径，这个绝对路径将作为对象的值。
         */
        '@': fileURLToPath(new URL('./src', import.meta.url)),
      },
    },
    // 设为 false 可以避免 Vite 清屏而错过在终端中打印某些关键信息。
    clearScreen: false,
    server: {
      // 端口号
      port: 5173,
      host: '0.0.0.0',
      // 开发服务器启动时，自动在浏览器中打开应用程序。
      open: false,
      proxy: {
        [env.VITE_API_BASE]: {
          target: 'http://localhost:8080',
          // target: 'https://vita.aday.fun',
          changeOrigin: true,
          rewrite: (path) => path.replace(new RegExp(`^\\${env.VITE_API_BASE}`), ''),
        },
      },
    },
    plugins: [
      vue(),
      vueDevTools(),
      svgLoader(),
      legacy({
        targets: ['defaults', 'not IE 11'],
      }),
      AutoImport({
        // 自动导入 Vue 相关函数，如：ref, reactive, toRef 等
        imports: ['vue'],
        // 自动导入 Element Plus 相关函数，如：ElMessage, ElMessageBox... (带样式)
        resolvers: [ElementPlusResolver()],
        eslintrc: {
          enabled: true, // 自动生成 ESLint 全局变量声明文件
          filepath: './.eslintrc-auto-import.json',
        },
        dts: false, // 禁用 TypeScript 类型声明生成（纯 JS 项目无需此文件）
      }),
      Components({
        resolvers: [
          IconsResolver({
            // 组件前缀。使用格式：{prefix}-{collection}-{icon} 或动态调用：<component :is="ep:home-filled" />
            prefix: 'icon',
            // 自动注册 iconify 图标组件。限制仅加载 ep、ri 和 mdi 图标集（collection）
            enabledCollections: ['ep', 'ri', 'mdi'],
          }),
          // 自动注册 Element Plus 组件
          ElementPlusResolver(),
        ],
      }),
      Icons({
        // 关闭自动联网安装。本项目采用离线的方式使用图标，因此关闭自动安装图标的功能。
        autoInstall: false,
        // 明确指定 Vue3 语法
        compiler: 'vue3',
      }),
    ],
    build: {
      // 会先使用 legacy 中的 targets
      // https://cn.vitejs.dev/guide/build.html#browser-compatibility
      //target: 'es2015',
      // https://cn.vitejs.dev/config/build-options.html#build-assetsinlinelimit
      // 小于此阈值的导入或引用资源将内联为 base64 编码，以避免额外的 http 请求。设置为 0 可以完全禁用此项。
      assetsInlineLimit: 0,
      sourcemap: false,
      // 消除打包大小超过500kb警告
      chunkSizeWarningLimit: 4000,
      // https://rollupjs.org/configuration-options/
      rollupOptions: {
        // 静态资源分类打包
        output: {
          chunkFileNames: 'src/static/js/[name]-[hash].js',
          entryFileNames: 'src/static/js/[name]-[hash].js',
          assetFileNames: 'src/static/[ext]/[name]-[hash].[ext]',
        },
      },
    },
  }
})
