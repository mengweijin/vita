# 打包

## 前后端不分离部署（把前端打包放在后端 jar 里）

```shell
# 前端打包

cd $WORKSPACE/vita/vita-ui
npm config set registry https://registry.npmmirror.com
pnpm clean
pnpm install
pnpm build:staging
# linux 环境下执行
pnpm copy:linux
# windows 环境下执行
pnpm copy:windows
# -----------------------------

# 后端打包
cd $WORKSPACE/vita
mvn clean package -Dmaven.test.skip=true -Ph2
```

此种方式部署启动时，只需要：

```shell
java -jar vita-admin.jar

# 或指定参数
java -Dname=vita-admin -Dspring.profiles.active=h2 -Dfile.encoding=utf-8 -Duser.timezone=Asia/Shanghai -Xms128m -Xmx512m -jar vita-admin.jar
```


## 前后端分离部署

```shell
# 前端打包

cd $WORKSPACE/vita/vita-ui
npm config set registry https://registry.npmmirror.com
pnpm clean
pnpm install
pnpm build
# -----------------------------

# 后端打包
cd $WORKSPACE/vita
mvn clean package -Dmaven.test.skip=true -Ph2
```

此种方式部署启动时，需要：

- 把前端打包后的文件部署到 Nginx 服务器，并做一定配置后，单独启动。
- 后端通过 java -jar vita-admin.jar 单独启动。
