package com.practise.grower.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "description")
    private String description;

    @Column(name = "prerequisites")
    private String prerequisites;

    @Column(name = "skill_set")
    private String skillSet;

    @Column(name = "course_duration")
    private Integer courseDuration;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "course",orphanRemoval = true)
    private List<Module> modules;

    public void addModule(Module module) {
        if(this.modules == null) {
            this.modules = new ArrayList<>();
        }
        this.modules.add(module);
        module.setCourse(this);
    }


    @OneToMany(mappedBy = "course", orphanRemoval = true)
    private List<Certificate> certificates;

    public void addCertificate(Certificate certificate) {
        if(this.certificates == null) {
            this.certificates = new ArrayList<>();
        }
        this.certificates.add(certificate);
        certificate.setCourse(this);
    }

    @OneToMany(mappedBy = "course", orphanRemoval = true)
    private List<Enrollment> enrollments;

    public void addEnrollment(Enrollment enrollment) {
        if(this.enrollments == null) {
            this.enrollments = new ArrayList<>();
        }
        this.enrollments.add(enrollment);
        enrollment.setCourse(this);
    }
}
