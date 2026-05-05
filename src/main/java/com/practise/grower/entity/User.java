package com.practise.grower.entity;

import com.practise.grower.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false,unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Manager manager;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Employee employee;


    @OneToMany(mappedBy = "createdBy")
    private List<Course> course;


    public void addCourse(Course course) {
        if(this.course == null) {
            this.course = new ArrayList<>();
        }
        this.course.add(course);
        course.setCreatedBy(this);
    }

    @OneToMany(mappedBy = "user")
    private List<Certificate> certificates;

    public void addCertificate(Certificate certificate) {
        if(this.certificates == null) {
            this.certificates = new ArrayList<>();
        }
        this.certificates.add(certificate);
        certificate.setUser(this);
    }


    @OneToMany(mappedBy = "user")
    private List<Enrollment> enrollments;

    public void addEnrollment(Enrollment enrollment) {
        if(this.enrollments == null) {
            this.enrollments = new ArrayList<>();
        }
        this.enrollments.add(enrollment);
        enrollment.setUser(this);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}
