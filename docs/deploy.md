# 部署指南

## 私有镜像挂载到 app.jar

```shell
docker run \
--name vita \
-p 9001:8080 \
--restart=unless-stopped \
-e JVM_OPTS="-Dname=vita-admin -Duser.timezone=Asia/Shanghai -Dfile.encoding=UTF-8 -Xms128m -Xmx512m -XX:+HeapDumpOnOutOfMemoryError" \
-e SPRING_OPTS="--spring.profiles.active=h2 --spring.datasource.password=<密码> --spring.h2.console.enabled=true --spring.h2.console.settings.webAllowOthers=true" \
-v /opt/vita/:/opt/app/ \
-d runner:17.0.19
```
