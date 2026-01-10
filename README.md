# Vita（微塔）

<p align="center">
    <a target="_blank" href="https://github.com/mengweijin/vita">
		<img src="https://img.shields.io/badge/repo-Github-purple" />
	</a>
    <a target="_blank" href="https://gitee.com/mengweijin/vita">
		<img src="https://img.shields.io/badge/repo-码云 Gitee-purple" />
	</a>
	<a target="_blank" href="https://central.sonatype.com/artifact/com.github.mengweijin/vita-parent/versions">
		<img src="https://img.shields.io/maven-central/v/com.github.mengweijin/vita-parent" />
	</a>
	<a target="_blank" href="https://github.com/mengweijin/vita/blob/master/LICENSE">
		<img src="https://img.shields.io/badge/license-Apache2.0-blue.svg" />
	</a>
	<a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
		<img src="https://img.shields.io/badge/JDK-17+-green.svg" />
	</a>
	<a target="_blank" href="https://gitee.com/mengweijin/vita/stargazers">
		<img src="https://gitee.com/mengweijin/vita/badge/star.svg?theme=dark" alt='gitee star'/>
	</a>
    <a href='https://gitee.com/mengweijin/vita/members'>
      <img src='https://gitee.com/mengweijin/vita/badge/fork.svg?theme=dark' alt='gitee fork'>
    </a>
	<a target="_blank" href='https://github.com/mengweijin/vita'>
		<img src="https://img.shields.io/github/stars/mengweijin/vita?style=social" alt="github star"/>
	</a>
	<a target="_blank" href='https://github.com/mengweijin/vita'>
		<img src="https://img.shields.io/github/forks/mengweijin/vita?style=social" alt="github fork"/>
	</a>
    <br>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/quality_gate?project=mengweijin_vita&branch=master" />
	</a>
    <br>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=alert_status&branch=master" />
	</a>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=reliability_rating&branch=master" />
	</a>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=security_rating&branch=master" />
	</a>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=sqale_rating&branch=master" />
	</a>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=vulnerabilities&branch=master" />
	</a>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=bugs&branch=master" />
	</a>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=ncloc&branch=master" />
	</a>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=duplicated_lines_density&branch=master" />
	</a>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=code_smells&branch=master" />
	</a>
</p>

## 介绍

**Vita（中文名：微塔）**：是一款**轻量级单机部署快速开发平台应用系统**。

基于 SpringBoot 3、sa-token、mybatis-plus、vite、vue 3、element-plus、javascript 等技术，不依赖任何第三方服务。

有时候我们就想做一个简单的东西，采用已有的开源框架却要依赖一大堆东西，和很复杂的配置文件，自己从零搭建又太耗费时间，**真的太麻烦了！**

于是，就有了 **Vita**，它可以帮你节省很多时间和精力，非常适合一个人即一个团队的工作环境。

## 在线演示
|           版本           |          演示链接          |
|:----------------------:|:----------------------:|
|       Vita（开发中）        | https://vita.aday.fun  |
| Vitality Layui 版（停止维护） | https://layui.aday.fun |

## 启动应用

### 源码启动

默认前后端一体化部署，因此你只需要一个 JDK 17 的运行环境，然后直接启动即可！

```shell
java -jar vita-admin.jar

# 或指定参数
java -Dname=vita-admin -Dspring.profiles.active=h2 -Dfile.encoding=utf-8 -Duser.timezone=Asia/Shanghai -Xms128m -Xmx512m -jar vita-admin.jar
```

浏览器访问：http://localhost:8080

注：当然，你也可以使用 nginx 代理服务器前后端分离部署。更多文档参考：

- [打包指南](docs/package.md)
- [部署指南](docs/deploy.md)

### 后端 SDK 化启动

使用方式可参考 SDK 化的示例工程：[vita-sdk-demo](./vita-sdk-demo)

很多时候，我们依赖上游应用，就希望可以在 maven 中直接依赖一个 jar 包，而不是克隆所有源代码，以方便基础工程的更新迭代。

此时就需要将基础工程 SDK 化，打包成 jar 发布到 maven 中央仓库。幸运的是，本项目已经将 jar 发布到了 maven 中央仓库。

核心操作如下：

1. 将 pom.xml 中的 parent 配置为如下示例：

    ```xml
        <parent>
            <groupId>com.github.mengweijin</groupId>
            <artifactId>vita-parent</artifactId>
            <version>${vita.version}</version>
        </parent>
    ```

2. 将 pom.xml 中的依赖增加如下示例：

    ```xml
    <dependency>
        <groupId>com.github.mengweijin</groupId>
        <artifactId>vita-framework</artifactId>
    </dependency>
    <dependency>
        <!-- 可选 -->
        <groupId>com.github.mengweijin</groupId>
        <artifactId>vita-generator</artifactId>
    </dependency>
    ```

3. 然后增加一个 @SpringBootApplication 启动类，直接启动就可以了。

### 功能矩阵

| 系统功能 | 系统管理 | 系统监控 | 开发工具  | 开发功能（列 1） | 开发功能（列 2） |
|------|------|------|-------|-----------|-----------|
| 首页   | 菜单管理 | 调度任务 | 接口文档  | 角色授权      | SSE 推送    |
| 系统公告 | 部门管理 | 应用监控 | 代码生成器 | 数据脱敏      | 一系列前端组件   |
| 消息管理 | 岗位管理 | 缓存监控 |       | 字典翻译      |           |
|      | 用户管理 | 在线用户 |       | 接口限流      |           |
|      | 角色管理 | 登录日志 |       | 缓存过期      |           |
|      | 分类管理 | 操作日志 |       | 接口防抖      |           |
|      | 字典管理 | 系统日志 |       | 全局异常      |           |
|      | 配置管理 |      |       | 数据权限      |           |
|      | 文件管理 |      |       | 自动刷新配置    |           |

### 演示图
| ![image](docs/readme/images/1.png) | ![image](docs/readme/images/2.png) |    
|-----------------------------------:|:-----------------------------------|
| ![image](docs/readme/images/3.png) | ![image](docs/readme/images/4.png) | 
| ![image](docs/readme/images/5.png) | ![image](docs/readme/images/6.png) | 

## ⭐Star Vita on GitHub

[![Stargazers over time](https://starchart.cc/mengweijin/vita.svg)](https://starchart.cc/mengweijin/vita)
