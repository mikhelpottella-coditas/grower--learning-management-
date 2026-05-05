create table manager
(
    id          BIGINT primary key auto_increment,
    user_id     BIGINT UNIQUE,
    department VARCHAR(50),

    constraint fk_manager_user
        foreign key (user_id)
            references users (id)
            ON DELETE CASCADE
);