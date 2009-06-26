rem @echo off
rem java -jar proguardgui.jar pfc.pro
java -jar proguard.jar @pfc.pro
del dist\servicio.jar
move dist\servicio-pg.jar dist\servicio.jar
pause
