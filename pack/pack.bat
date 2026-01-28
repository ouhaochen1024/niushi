@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

echo ==============================================
echo      Spring Boot Application Packaging Script
echo ==============================================
echo.

REM Get script directory
set "SCRIPT_DIR=%~dp0"
set "SCRIPT_DIR=%SCRIPT_DIR:~0,-1%"

echo Script directory: !SCRIPT_DIR!
echo.

REM Input JAR file path - with validation
:INPUT_JAR_PATH
echo Please enter the absolute path of the JAR file:
set /p "JAR_PATH="
if "!JAR_PATH!"=="" (
    echo ERROR: No JAR file path entered
    goto :INPUT_JAR_PATH
)

REM Check if file exists
if not exist "!JAR_PATH!" (
    echo ERROR: JAR file does not exist: !JAR_PATH!
    goto :INPUT_JAR_PATH
)

REM Check if it's a JAR file
set "JAR_EXT="
for %%I in ("!JAR_PATH!") do (
    set "JAR_FILE=%%~nxI"
    set "JAR_EXT=%%~xI"
)

if /i "!JAR_EXT!" NEQ ".jar" (
    echo ERROR: File is not a JAR file. Please enter a .jar file path.
    goto :INPUT_JAR_PATH
)

echo Found JAR file: !JAR_PATH!
echo.

REM Extract filename and directory
for %%I in ("!JAR_PATH!") do (
    set "JAR_FILE=%%~nxI"
    set "JAR_DIR=%%~dpI"
)

echo JAR filename: !JAR_FILE!
echo JAR file directory: !JAR_DIR!
echo.

REM Extract application name
set "APP_NAME=!JAR_FILE:~0,-4!"
echo Application name: !APP_NAME!
echo.

REM Check required script files
echo Checking script files
set REQUIRED_FILES=env.sh start.sh stop.sh restart.sh
set MISSING_FILES=

for %%f in (%REQUIRED_FILES%) do (
    if not exist "!SCRIPT_DIR!\%%f" (
        if not defined MISSING_FILES (
            set "MISSING_FILES=%%f"
        ) else (
            set "MISSING_FILES=!MISSING_FILES!, %%f"
        )
    )
)

if defined MISSING_FILES (
    echo ERROR: Missing required script files
    echo Missing files: !MISSING_FILES!
    echo Please ensure these files are in the same directory as pack.bat:
    for %%f in (%REQUIRED_FILES%) do echo   %%f
    pause
    exit /b 1
)

for %%f in (%REQUIRED_FILES%) do (
    echo ✓ %%f
)
echo.

REM Check configuration files
echo Checking configuration files
set CONFIG_FILES=application.yml log4j2-spring.xml

for %%f in (%CONFIG_FILES%) do (
    if exist "!SCRIPT_DIR!\%%f" (
        echo ✓ %%f
    ) else (
        echo WARNING: %%f not found, will not be included in package
    )
)
echo.

REM Set deployment directory - fix double backslash issue
REM Remove trailing backslash from JAR_DIR if present
if "!JAR_DIR:~-1!"=="\" set "JAR_DIR=!JAR_DIR:~0,-1!"
set "DIST_DIR=!JAR_DIR!\!APP_NAME!-deploy"
set "BIN_DIR=!DIST_DIR!\bin"
set "TARGET_DIR=!DIST_DIR!\target"
set "CONFIG_DIR=!DIST_DIR!\config"
set "LOG_DIR=!DIST_DIR!\logs"
set "GC_DIR=!DIST_DIR!\gc"

echo Deployment directory: !DIST_DIR!
echo.

echo Creating directory structure
if exist "!DIST_DIR!" (
    echo Cleaning existing deployment directory
    rmdir /s /q "!DIST_DIR!" >nul 2>&1
)
mkdir "!DIST_DIR!" >nul 2>&1
mkdir "!BIN_DIR!" >nul 2>&1
mkdir "!TARGET_DIR!" >nul 2>&1
mkdir "!CONFIG_DIR!" >nul 2>&1
mkdir "!LOG_DIR!" >nul 2>&1
mkdir "!GC_DIR!" >nul 2>&1

echo.
echo Copying files
echo 1. Copying JAR file
copy "!JAR_PATH!" "!TARGET_DIR!\" >nul
echo   ✓ !JAR_FILE!

echo 2. Copying and modifying script files
copy "!SCRIPT_DIR!\start.sh" "!BIN_DIR!\" >nul
copy "!SCRIPT_DIR!\stop.sh" "!BIN_DIR!\" >nul
copy "!SCRIPT_DIR!\restart.sh" "!BIN_DIR!\" >nul
echo   ✓ start.sh
echo   ✓ stop.sh
echo   ✓ restart.sh

REM 统一替换逻辑：将所有文件中的 "app-name" 替换为实际的应用程序名称
echo   Processing env.sh
copy "!SCRIPT_DIR!\env.sh" "!BIN_DIR!\env.sh" >nul
powershell -Command "(Get-Content '!BIN_DIR!\env.sh') -replace 'app-name', '!APP_NAME!' | Set-Content '!BIN_DIR!\env.sh'"
echo   ✓ env.sh ^(APP_NAME updated to: !APP_NAME!^)

echo 3. Copying and modifying configuration files

if exist "!SCRIPT_DIR!\application.yml" (
    echo   Processing application.yml
    copy "!SCRIPT_DIR!\application.yml" "!CONFIG_DIR!\" >nul
    powershell -Command "(Get-Content '!CONFIG_DIR!\application.yml') -replace 'app-name', '!APP_NAME!' | Set-Content '!CONFIG_DIR!\application.yml'"
    echo   ✓ application.yml ^(APP_NAME updated to: !APP_NAME!^)
)

if exist "!SCRIPT_DIR!\log4j2-spring.xml" (
    echo   Processing log4j2-spring.xml
    copy "!SCRIPT_DIR!\log4j2-spring.xml" "!CONFIG_DIR!\log4j2-spring.xml" >nul
    powershell -Command "(Get-Content '!CONFIG_DIR!\log4j2-spring.xml') -replace 'app-name', '!APP_NAME!' | Set-Content '!CONFIG_DIR!\log4j2-spring.xml'"
    echo   ✓ log4j2-spring.xml ^(APP_NAME updated to: !APP_NAME!^)
)

echo.
echo Creating ZIP archive
set "ZIP_FILE=!JAR_DIR!\!APP_NAME!-deploy.zip"
powershell -Command "Compress-Archive -Path '!DIST_DIR!\*' -DestinationPath '!ZIP_FILE!' -Force"

echo.
echo ==============================================
echo     Packaging Complete
echo ==============================================
echo.
echo Generated files
echo - Deployment directory: !DIST_DIR!
echo - ZIP archive: !ZIP_FILE!
echo.
echo Package contents
echo - bin/           ^# Script files
echo - target/        ^# JAR file
echo - config/        ^# Configuration files
echo - logs/          ^# Application logs directory
echo - gc/            ^# GC logs directory
echo.
echo Configuration details
echo 1. APP_NAME in env.sh: !APP_NAME!
echo 2. APP_NAME in application.yml: !APP_NAME!
echo 3. APP_NAME in log4j2-spring.xml: !APP_NAME!
echo 4. Log files will use !APP_NAME! as prefix
echo.
echo Tip: Upload !APP_NAME!-deploy directory or !APP_NAME!-deploy.zip to server
echo.

set "OPEN_DIR="
echo Open deployment directory? (Y/N, default N)
set /p "OPEN_DIR="
if /i "!OPEN_DIR!"=="Y" (
    explorer "!DIST_DIR!"
)

pause