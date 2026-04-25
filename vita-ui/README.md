# vita-ui

This template should help get you started developing with Vue 3 in Vite.

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/)

## Customize configuration

See [Vite Configuration Reference](https://vite.dev/config/).

## Project Setup

```sh
# 自行安装好 nodejs
node -v

npm -v

# 设置源
npm config set registry https://registry.npmmirror.com

# 全局安装 pnpm
npm install -g pnpm

pnpm install
```

### Compile and Hot-Reload for Development

```sh
pnpm dev
```

### Compile and Minify for Production

```sh
pnpm build
```

## scripts 命令

```json5
"scripts": {
    // 启动开发环境
    "dev": "vite",
    // 本地预览打包后的结果
    "preview": "vite preview",
    // 生产环境打包
    "build": "vite build",
    // 测试环境打包
    "build:staging": "vite build --mode staging",
    // 打包并自动复制到 Java 项目
    "build-to-jar": "vite build --mode staging && node copy-dist.js",
    // 单独执行复制脚本
    "copy-dist": "node copy-dist.js",
    // 检查代码错误、不规范写法（只报问题，不修改）
    "lint": "oxlint",
    // CI 自动化检查。GitHub 流水线专用
    "lint:github": "oxlint --format=github",
    // 自动修复代码问题
    "lint:fix": "oxlint --fix",
    // 一键格式化代码
    "fmt": "oxfmt",
    // 检查代码是否格式化过（不修改代码）
    "fmt:check": "oxfmt --check",
    // 彻底清理依赖 + 缓存
    "clean": "rimraf pnpm-lock.yaml && rimraf node_modules && pnpm store prune",
    // 强制只能用 pnpm
    "preinstall": "npx only-allow pnpm",
    // 可视化更新依赖
    "check": "npm-check -u"
}
```

## 常用命令速查表

```shell
pnpm dev            # 开发启动
pnpm build          # 正式打包
pnpm build:staging  # 测试环境打包
pnpm preview        # 预览打包结果
pnpm lint:fix       # 自动修复代码（代码提交前执行一下）
pnpm fmt            # 格式化代码（代码提交前执行一下）
```
