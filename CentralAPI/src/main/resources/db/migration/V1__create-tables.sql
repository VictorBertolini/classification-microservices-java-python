CREATE TABLE user_tb (
     user_id BIGINT NOT NULL AUTO_INCREMENT,
     username VARCHAR(255) NOT NULL,
     email VARCHAR(255) NOT NULL,
     password VARCHAR(255) NOT NULL,
     role VARCHAR(50) NOT NULL,
     requests_remain INT NOT NULL,
     PRIMARY KEY (user_id)
);

CREATE TABLE text_classify_tb (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    text TEXT NOT NULL,
    label VARCHAR(255) NOT NULL,
    score DOUBLE NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_text_user FOREIGN KEY (user_id) REFERENCES user_tb(user_id)
);