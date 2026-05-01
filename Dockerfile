FROM amazoncorretto:17.0.19

LABEL maintainer="aday.fun@outlook.com"

ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
ENV TZ=Asia/Shanghai
ENV FILE_ENCODING=UTF-8
ENV JVM_OPTS=""
ENV SPRING_OPTS=""
ENV JAR="vita-admin.jar"

WORKDIR /opt/vita

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

VOLUME /opt/vita/

ADD ./vita-admin/target/${JAR} ./${JAR}

ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -Dname=${NAME:-$JAR} -Duser.timezone=${TZ} -Dfile.encoding=${FILE_ENCODING} -XX:+HeapDumpOnOutOfMemoryError -jar ${JAR} ${SPRING_OPTS}"]
