create table employees (
  id BIGINT primary key auto_increment,
    user_id BIGINT UNIQUE,
    work_status VARCHAR(50),

    constraint fk_employees_user
        foreign key (user_id)
        references users(id)
        ON DELETE CASCADE
);