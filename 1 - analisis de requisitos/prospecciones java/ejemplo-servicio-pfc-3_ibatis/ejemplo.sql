DROP TABLE news_abc;
CREATE TABLE news_abc(
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    text VARCHAR2(50)
);
INSERT INTO news_abc(start_date, end_date, text) VALUES(SYSDATE, SYSDATE+10, 'This is a test news!');
INSERT INTO news_abc(start_date, end_date, text) VALUES(SYSDATE+2, SYSDATE+12, 'Future News!');
INSERT INTO news_abc(start_date, end_date, text) VALUES(SYSDATE-3, SYSDATE+4, 'Newsflash!');
INSERT INTO news_abc(start_date, end_date, text) VALUES(SYSDATE-10, SYSDATE-4, 'Old news :-(');
SELECT * FROM USER_TABLES;
