# 前端文档

本项目采用前后端分离开发，前后端不分离的方式一键部署。

## 开发环境搭建

依赖 nodejs

从仓库克隆代码后，进入 vita-ui 目录，执行命令：

```shell
# 安装依赖
pnpm install

# 启动开发环境
pnpm dev
```

## 生产环境构建

### 前后端不分离部署

前后端不分离部署是本项目默认的部署方式，目的是为了直接简单的启动应用，此时只需要 java -jar 启动 spring boot 应用即可。

这就避免了既要单独部署后端，又要单独部署前端带来的繁琐。

产生的资源文件自动放到 vita-admin\\src\\main\\resources\\static\\vita 目录下。

```shell
pnpm build:staging
```

打包后，直接 java -jar vita-admin.jar 启动后端服务，然后浏览器访问 http://localhost:8080 即可。

### 前后端分离部署

现代 web 应用基本都是前后端分离部署的，后端启动 spring boot 服务，前端使用 nginx 代理打包后的静态资源，需要额外配置 nginx。

```shell
pnpm build
```
