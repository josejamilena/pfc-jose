
sqlite3 commandline shell ported to 100% pure Java using NestedVM.

  http://sqlite.org/
  http://nestedvm.ibex.org/

Should run in Java 1.4.2 or later.

Contents:

  sqlite3j.jar: JIT mode

  sqlite3i.jar: interpreted mode

Some commandline examples:

  java -jar sqlite3j.jar your.db

  java -jar sqlite3i.jar -batch your.db .schema

  cat foo.sql | java -jar sqlite3j.jar -batch -bail new.db

Use sqlite3j.jar for long running SQL or complex queries.
sqlite3j.jar can run twice as fast as sqlite3i.jar in some cases.
Use the interpreted sqlite3i.jar when lower memory use or quick
start-up times are needed. sqlite3i.jar can run in under 20M of RAM.

PLEASE NOTE:

  This is not a JDBC driver.

  USE AT OWN RISK

  THIS IS NOT A STEP

  DO NOT EAT

