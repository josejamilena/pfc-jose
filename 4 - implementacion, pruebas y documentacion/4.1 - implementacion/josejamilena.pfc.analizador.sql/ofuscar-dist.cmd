rem @echo off
rem java -jar proguardgui.jar pfc.pro
java -jar proguard.jar @pfc.pro
del dist\josejamilena.pfc.analizador.sql.jar
move dist\josejamilena.pfc.analizador-pg.jar dist\josejamilena.pfc.analizador.sql.jar
pause
