#!/bin/bash
# Spring Boot停止脚本

# 加载环境配置
source "$(dirname "$0")/env.sh"

if [ ! -f "${PID_FILE}" ]; then
    echo "应用未在运行"
    exit 1
fi

pid=$(cat "${PID_FILE}")

echo "正在停止应用 (PID: ${pid})..."
kill ${pid} 2>/dev/null

# 等待进程结束
wait_count=0
while kill -0 ${pid} 2>/dev/null; do
    sleep 1
    wait_count=$((wait_count + 1))
    if [ ${wait_count} -ge 30 ]; then
        echo "等待超时，强制停止..."
        kill -9 ${pid} 2>/dev/null
        break
    fi
done

rm -f "${PID_FILE}"
echo "应用已停止"