@echo off
:: Gradle start up script for Windows

set DIR=%~dp0
set JAVA_EXE=java
"%JAVA_EXE%" -cp "%DIR%gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %*