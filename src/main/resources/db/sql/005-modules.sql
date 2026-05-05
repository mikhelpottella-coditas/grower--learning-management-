create table modules (
    id BIGINT primary key auto_increment,
    module_name VARCHAR(255) NOT NULL,
    resource_link TEXT,
    description TEXT,
    course_id BIGINT,

    constraint fk_module_course
        FOREIGN KEY (course_id)
        REFERENCES courses (id)
        ON DELETE CASCADE
);