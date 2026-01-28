#!/bin/bash
# Spring Boot启动脚本

# 加载环境配置
source "$(dirname "$0")/env.sh"

# 检查应用是否已在运行
if [ -f "${PID_FILE}" ]; then
    pid=$(cat "${PID_FILE}")
    if kill -0 "${pid}" 2>/dev/null; then
        echo "应用已在运行 (PID: ${pid})"
        exit 1
    fi
    rm -f "${PID_FILE}"
fi

# 检查jar文件
if [ ! -f "${JAR_FILE}" ]; then
    echo "错误: 找不到JAR文件 ${JAR_FILE}"
    exit 1
fi

# 检查配置文件
CONFIG_PARAMS=""
if [ -f "${CONFIG_DIR}/application.yml" ]; then
    CONFIG_PARAMS="${CONFIG_PARAMS} --spring.config.location=file:${CONFIG_DIR}/application.yml"
fi

if [ -f "${CONFIG_DIR}/log4j2-spring.xml" ]; then
    CONFIG_PARAMS="${CONFIG_PARAMS} -Dlogging.config=file:${CONFIG_DIR}/log4j2-spring.xml"
fi

# 启动应用
echo "正在启动应用..."
echo "使用配置文件: ${CONFIG_DIR}/"
nohup ${JAVA} ${JVM_OPTS} ${CONFIG_PARAMS} -jar ${JAR_FILE} > ${LOG_DIR}/console.log 2>&1 &
echo $! > ${PID_FILE}

# 等待10秒后显示日志
echo "等待10秒后显示启动日志..."
sleep 10

if [ -f "${PID_FILE}" ]; then
    pid=$(cat "${PID_FILE}")
    if kill -0 "${pid}" 2>/dev/null; then
        echo "应用启动成功 (PID: ${pid})"
        tail -f100 ${LOG_DIR}/console.log
    else
        echo "应用启动失败，请查看日志: ${LOG_DIR}/console.log"
    fi
else
    echo "应用启动失败"
fi