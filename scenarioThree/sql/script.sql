CREATE SCHEMA RestfulTest;

CREATE TABLE ACCOUNT(
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    isAdmin SMALLINT DEFAULT FALSE
)

INSERT INTO ACCOUNT(id,email,password,isAdmin) VALUES (1,'admin@example.com','admin',1);
INSERT INTO ACCOUNT(id,email,password,isAdmin) VALUES (2,'user@example.com','user',0);