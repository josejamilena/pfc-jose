rem @echo off
rem java -jar proguardgui.jar pfc.pro
java -jar proguard.jar @pfc.pro
del dist\josejamilena.pfc.servidor.tcp.jar
move dist\josejamilena.pfc.servidor.tcp-pg.jar dist\josejamilena.pfc.servidor.tcp.jar
pause
