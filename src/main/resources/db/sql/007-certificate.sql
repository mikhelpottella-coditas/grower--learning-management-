CREATE TABLE certificates
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT,
    course_id       BIGINT,
    issued_at       TIMESTAMP,
    certificate_url TEXT,

    CONSTRAINT fk_certificate_user
        FOREIGN KEY (user_id)
            REFERENCES users (id),

    CONSTRAINT fk_certificate_course
        FOREIGN KEY (course_id)
            REFERENCES courses (id)
);