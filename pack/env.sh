#!/bin/bash
# Spring Boot环境配置

# 应用基本信息
export APP_NAME="app-name"
export BASE_DIR=$(cd "$(dirname "$0")/.." && pwd)

# 目录路径
export TARGET_DIR="${BASE_DIR}/target"
export LOG_DIR="${BASE_DIR}/logs"
export GC_DIR="${BASE_DIR}/gc"
export PID_FILE="${BASE_DIR}/app.pid"

# jar文件
export JAR_FILE="${TARGET_DIR}/${APP_NAME}.jar"

# Java环境
if [ -n "$JAVA_HOME" ]; then
    export JAVA="$JAVA_HOME/bin/java"
else
    export JAVA=$(which java 2>/dev/null)
fi

if [ -z "$JAVA" ]; then
    echo "错误: 未找到Java环境"
    exit 1
fi

# JVM参数
export JVM_OPTS="\
-server \
-Xms2g \
-Xmx2g \
-Xmn1g \
-XX:MetaspaceSize=256m \
-XX:MaxMetaspaceSize=512m \
-XX:+UseG1GC \
-XX:MaxGCPauseMillis=200 \
-Xlog:gc*:file=${GC_DIR}/gc.log:time:filecount=5,filesize=50M"

# 创建目录
mkdir -p "${LOG_DIR}" "${GC_DIR}"