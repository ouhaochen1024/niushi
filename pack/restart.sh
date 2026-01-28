#!/bin/bash
# Spring Boot重启脚本

echo "正在重启应用..."

# 先停止
source "$(dirname "$0")/env.sh"
if [ -f "${PID_FILE}" ]; then
    pid=$(cat "${PID_FILE}")
    if kill -0 "${pid}" 2>/dev/null; then
        echo "停止运行中的应用..."
        kill ${pid} 2>/dev/null
        sleep 5
    fi
    rm -f "${PID_FILE}"
fi

# 再启动
echo "启动新应用..."
"$(dirname "$0")/start.sh"