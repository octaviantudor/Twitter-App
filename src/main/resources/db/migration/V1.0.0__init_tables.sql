create table if not exists users(
    id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(60),
    last_name VARCHAR(60),
    mail VARCHAR(60),
    password VARCHAR(60)
);

create table if not exists posts(
    id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT(20) NOT NULL,
    message VARCHAR(60),
    time_stamp TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

create table if not exists replies(
    id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT(20) NOT NULL,
    is_public BOOLEAN,
    FOREIGN KEY (post_id) REFERENCES posts(id)
);

create table if not exists likes(
    id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT(20) NOT NULL,
    user_id BIGINT(20) NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

create table if not exists follows(
    id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    from_user_id BIGINT(20) NOT NULL,
    to_user_id BIGINT(20) NOT NULL,
    time_stamp TIMESTAMP,
    FOREIGN KEY (from_user_id) REFERENCES users(id),
    FOREIGN KEY (to_user_id) REFERENCES users(id)
);

create table if not exists mentions(
    id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT(20) NOT NULL,
    user_id BIGINT(20) NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
