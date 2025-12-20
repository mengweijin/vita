import { fileURLToPath, URL } from "node:url";
import legacy from "@vitejs/plugin-legacy";
import vue from "@vitejs/plugin-vue";
import AutoImport from "unplugin-auto-import/vite";
import { ElementPlusResolver } from "unplugin-vue-components/resolvers";
import Components from "unplugin-vue-components/vite";
import { VueRouterAutoImports } from "unplugin-vue-router";
import VueRouter from "unplugin-vue-router/vite";
import { defineConfig, loadEnv } from "vite";
import vueDevTools from "vite-plugin-vue-devtools";
import svgLoader from "vite-svg-loader";

// https://cn.vitejs.dev/config/
export default defineConfig(({ mode }) => {
	// 根据当前工作目录中的 `mode` 加载 .env 文件
	// 以 process.cwd() 作为环境变量文件的根目录。
	// 设置第三个参数为 '' 来加载所有环境变量，而不管是否有 `VITE_` 前缀。
	// eslint-disable-next-line no-undef
	const env = loadEnv(mode, process.cwd(), "");

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
		build: {
			// 会先使用 legacy 中的 targets
			// https://cn.vitejs.dev/guide/build.html#browser-compatibility
			//target: 'es2015',
			// https://cn.vitejs.dev/config/build-options.html#build-assetsinlinelimit
			// 小于此阈值的导入或引用资源将内联为 base64 编码，以避免额外的 http 请求。设置为 0 可以完全禁用此项。
			assetsInlineLimit: 0,
			// 消除打包大小超过500kb警告
			chunkSizeWarningLimit: 4000,
			// https://rollupjs.org/configuration-options/
			rollupOptions: {
				// Rollup 打包配置选项
				output: {
					// 静态资源文件命名规则（图片、字体、CSS等）
					// [name]: 资源文件名称
					// [hash]: 基于文件内容生成的哈希值
					// [ext]: 文件扩展名（如: png, jpg, css, svg）
					assetFileNames: "src/[ext]/[name]-[hash].[ext]",
					// 代码分割产生的 chunk 文件命名规则。例如: src/js/vendor-abc123.js
					// [name]: 模块名称
					// [hash]: 基于文件内容生成的哈希值（用于缓存控制）
					chunkFileNames: "src/js/[name]-[hash].js",
					// 入口文件命名规则。例如: src/js/main-def456.js
					entryFileNames: "src/js/[name]-[hash].js",
				},
			},
			sourcemap: false,
		},
		// 设为 false 可以避免 Vite 清屏而错过在终端中打印某些关键信息。
		clearScreen: false,
		plugins: [
			VueRouter({
				// 更多选项见文档
				dts: true, // TypeScript 类型生成
				// 排除特定文件
				exclude: ["**/components/**", "**/test/**"],
				extensions: [".vue"], // 文件扩展名
				// 自动导入布局
				importMode: "async",
				// 启用 route 块解析
				routeBlockLang: "yaml", // 路由块的语言
				// 配置选项，例如：
				routesFolder: "src/pages", // 路由文件夹，默认是 'src/pages'
			}),
			// ⚠️ Vue 必须放在 VueRouter() 之后
			vue(),
			vueDevTools(),
			svgLoader(),
			legacy({
				targets: ["defaults", "not IE 11"],
			}),
			AutoImport({
				// 是否生成 TypeScript 类型声明（即使是纯 JS 项目也建议生成，以便获得更好的类型提示）
				dts: true,
				// 自动导入 vue, pinia 等相关函数，如：ref, reactive, toRef, storeToRefs 等
				imports: ["vue", "pinia", VueRouterAutoImports],
				// 自动导入 Element Plus 相关函数，如：ElMessage, ElMessageBox... (带样式)
				resolvers: [ElementPlusResolver()],
				// 启用 Vue 3 的模板自动导入功能
				vueTemplate: true,
			}),
			Components({
				// 是否生成组件的 TypeScript 类型声明（即使是纯 JS 项目也建议生成，以便获得更好的类型提示）
				dts: true,
				resolvers: [
					// 自动注册 Element Plus 组件
					ElementPlusResolver(),
				],
			}),
		],
		resolve: {
			alias: {
				/**
				 * import.meta.url 表示当前模块的 URL。
				 * new URL(file, import.meta.url) ：创建一个基于 import.meta.url 的绝对 URL 对象。
				 * fileURLToPath(...)：将 URL 对象转换为文件系统路径，这个绝对路径将作为对象的值。
				 */
				"@": fileURLToPath(new URL("./src", import.meta.url)),
			},
			// 导入时可以省略的后缀名，可根据需求扩展。若同一目录下存在同名 `.js` 和 `.vue` 文件，按照配置的顺序优先加载。
			// extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json'], // 默认值
			extensions: [".js"],
		},
		server: {
			host: "0.0.0.0",
			// 开发服务器启动时，自动在浏览器中打开应用程序。
			open: true,
			// 端口号
			port: 5173,
			proxy: {
				[env.VITE_BASE_API]: {
					changeOrigin: true,
					rewrite: (path) => path.replace(new RegExp(`^\\${env.VITE_BASE_API}`), ""),
					// target: "https://vita.aday.fun",
					target: "http://localhost:8080",
				},
			},
		},
	};
});
