rem @echo off
rem java -jar proguardgui.jar pfc.pro
java -jar proguard.jar @pfc.pro
del dist\ejemplo-servicio-pfc-5.jar
move dist\ejemplo-servicio-pfc-5-pg.jar dist\ejemplo-servicio-pfc-5.jar

