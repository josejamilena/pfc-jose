rem @echo off
rem java -jar proguardgui.jar pfc.pro
java -jar proguard.jar @pfc.pro
del dist\analizador.jar
move dist\analizador-pg.jar dist\analizador.jar
pause
