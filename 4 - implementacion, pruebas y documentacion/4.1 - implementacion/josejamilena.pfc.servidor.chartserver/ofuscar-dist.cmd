rem @echo off
rem java -jar proguardgui.jar pfc.pro
java -jar proguard.jar @pfc.pro
del dist\josejamilena.pfc.servidor.chartserver.jar
move dist\josejamilena.pfc.servidor.chartserver-pg.jar dist\josejamilena.pfc.servidor.chartserver.jar
pause
