DECLARE
  square INT;
BEGIN
  FOR i IN 1..10 LOOP
    square := i * i;
    DBMS_OUTPUT.put_line( to_char(square) || ' ' );
  END LOOP;
END;
/
BEGIN
  DBMS_OUTPUT.put_line('Hello World');
END;
/