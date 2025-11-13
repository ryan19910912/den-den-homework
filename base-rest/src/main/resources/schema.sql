DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS verification_code;
DROP TABLE IF EXISTS login_record;

CREATE TABLE member (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(200),
    password VARCHAR(200),
    create_time TIMESTAMP
);

CREATE TABLE verification_code (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(200),
    code VARCHAR(200),
    action_type VARCHAR(200),
    state VARCHAR(200),
    expire_time TIMESTAMP
);

CREATE TABLE login_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(200),
    last_login_time TIMESTAMP
);