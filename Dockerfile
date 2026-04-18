FROM amazoncorretto:17.0.18

LABEL maintainer="aday.fun@outlook.com"

ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
ENV TZ=Asia/Shanghai
ENV FILE_ENCODING=UTF-8
ENV SERVER_PORT=8080
ENV JVM_OPTS="-Duser.timezone=$TZ -Dfile.encoding=${FILE_ENCODING} -Dserver.port=${SERVER_PORT}"
ENV JAR="vita-admin.jar"

WORKDIR /opt/vita

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

VOLUME /opt/vita/h2/
VOLUME /opt/vita/logs/
VOLUME /opt/vita/uploads/

EXPOSE ${SERVER_PORT}

ADD ./vita-admin/target/${JAR} ./${JAR}

ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom ${JVM_OPTS} -jar ${JAR}"]
