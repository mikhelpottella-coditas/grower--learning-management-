create table courses (

    id BIGINT primary key auto_increment,
    course_name VARCHAR(255) NOT NULL,
    description TEXT,
    prerequisites TEXT,
    skill_set TEXT,
    course_duration INT,
    created_by BIGINT,

    constraint fk_course_admin
        FOREIGN KEY (created_by)
        REFERENCES users (id)
);