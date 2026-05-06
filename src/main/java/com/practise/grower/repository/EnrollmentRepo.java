package com.practise.grower.repository;

import com.practise.grower.entity.Enrollment;
import com.practise.grower.entity.User;
import com.practise.grower.enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUserAndStatus(User user, EnrollmentStatus enrollmentStatus);

    List<Enrollment> findByStatus(EnrollmentStatus enrollmentStatus);
}
