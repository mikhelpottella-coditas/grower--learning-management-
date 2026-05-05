CREATE TABLE enrollments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    status varchar(50),
    start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_date TIMESTAMP,


    constraint fk_enrollments_user
        FOREIGN KEY (user_id)
        REFERENCES users (id),

    constraint fk_enrollments_course
        FOREIGN KEY (course_id)
        REFERENCES courses (id)
);