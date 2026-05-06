package com.practise.grower.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "logger")
@Getter
@Setter
public class Logger {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "user", nullable = false)
    private String user;

    @Column(name = "message", nullable = false)
    private String message;
}
