package com.practise.grower.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "modules")
@Getter
@Setter
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module_name", nullable = false)
    private String moduleName;

    @Column(name = "description")
    private String description;

    @Column(name = "resource_link")
    private String resourceLink;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;


}
