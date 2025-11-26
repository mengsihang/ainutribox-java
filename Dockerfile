# =============== 第一阶段：构建 ==================
FROM maven:3.9.6-eclipse-temurin-21 AS build

# ✅ 一次性复制整个项目源码（关键！）
COPY . /app/
WORKDIR /app

# ✅ 1. 安装 dependencies 模块（它通常是一个 bom/pom-only 模块）
RUN mvn install -DskipTests -Drevision=2.1.0-SNAPSHOT -pl ainutribox-dependencies

# ✅ 2. 安装根 POM（非递归，-N 表示 no recursive）
RUN mvn install -DskipTests -Drevision=2.1.0-SNAPSHOT -N

# ✅ 3. 构建所有模块（此时 dependencies 和 parent 都已安装）
RUN mvn clean package -DskipTests -Drevision=2.1.0-SNAPSHOT

# =============== 第二阶段：运行 ==================
FROM eclipse-temurin:21-jre

RUN mkdir -p /ainutribox-server
WORKDIR /ainutribox-server

# ✅ 从 build 阶段拷贝最终 JAR
COPY --from=build /app/ainutribox-server/target/ainutribox-server.jar app.jar

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"
ENV ARGS=""

EXPOSE 8080

# 使用 Render 提供的 PORT 环境变量
CMD java ${JAVA_OPTS} -jar app.jar --server.port=${PORT} ${ARGS}