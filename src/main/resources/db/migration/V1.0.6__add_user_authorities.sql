create table if not exists authorities(
    id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL
    );

create table if not exists user_authority(
    user_id BIGINT(20) NOT NULL,
    authority_id BIGINT(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (authority_id) REFERENCES authorities(id)
    );


