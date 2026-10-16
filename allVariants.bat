@echo off
setlocal enabledelayedexpansion

set continue_on_error=0
if x%1x==x--continue-on-errorx (
  set continue_on_error=1
  shift
)

set gradle_args=
:loop
if x%1x==xx goto end_loop
if x!gradle_args!x==xx (
  set gradle_args=%1
) else (
  set gradle_args=!gradle_args! %1
)
shift
goto loop

:end_loop
for %%v in (2.5 3.0 4.0 5.0) do (
    echo(
    echo Variant: %%v
    gradlew.bat -Dvariant=%%v %gradle_args%
    set err=!errorlevel!
    if !err! neq 0 if %continue_on_error% neq 1 exit /b !err!
)
