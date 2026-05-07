package com.practise.grower.service;

import com.practise.grower.dto.Admin.CourseDto;
import com.practise.grower.dto.User.CertificateDto;
import com.practise.grower.dto.User.UserProfileResponseDto;
import com.practise.grower.entity.Course;
import com.practise.grower.entity.Employee;
import com.practise.grower.entity.Enrollment;
import com.practise.grower.enums.EnrollmentStatus;
import com.practise.grower.exception.CustomException;
import com.practise.grower.repository.CourseRepo;
import com.practise.grower.repository.EmployeeRepo;
import com.practise.grower.repository.EnrollmentRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepo employeeRepo;
    private final CourseRepo courseRepo;
    private final EnrollmentRepo enrollmentRepo;
    private final PasswordEncoder passwordEncoder;


    // get user profile
    public UserProfileResponseDto getUserProfile(Long employeeId) {
        logger.info("Fetching user profile for employee: {}", employeeId);
        Employee employee = employeeRepo.findById(employeeId).orElseThrow(() -> {
            logger.warn("Employee not found with id: {}", employeeId);
            return new CustomException(HttpStatus.NOT_FOUND, "Employee not found");
        });
        UserProfileResponseDto userProfileResponseDto = new UserProfileResponseDto(employee.getId(), employee.getUser().getUsername(), employee.getUser().getEmail(), employee.getWorkStatus());
        logger.info("User profile retrieved for employee: {}", employeeId);
        return userProfileResponseDto;
    }


    // update user profile
    public String updateUserProfile(Long employeeId, UserProfileResponseDto userProfileResponseDto) {
        logger.info("Updating user profile for employee: {}", employeeId);
        Employee employee = employeeRepo.findById(employeeId).orElseThrow(() -> {
            logger.warn("Employee not found with id: {}", employeeId);
            return new CustomException(HttpStatus.NOT_FOUND, "Employee not found");
        });

        if (userProfileResponseDto.name() != null) employee.getUser().setUsername(userProfileResponseDto.name());
        if (userProfileResponseDto.email() != null) employee.getUser().setEmail(userProfileResponseDto.email());
        if (userProfileResponseDto.workStatus() != null) employee.setWorkStatus(userProfileResponseDto.workStatus());

        employeeRepo.save(employee);
        logger.info("User profile updated successfully for employee: {}", employeeId);
        return "User profile updated successfully";
    }


    // change password
    public String changePassword(Long employeeId, String oldPassword, String newPassword) {
        logger.info("Changing password for employee: {}", employeeId);
        Employee employee = employeeRepo.findById(employeeId).orElseThrow(() -> {
            logger.warn("Employee not found with id: {}", employeeId);
            return new CustomException(HttpStatus.NOT_FOUND, "Employee not found");
        });

        if (!passwordEncoder.matches(oldPassword, employee.getUser().getPassword())) {
            logger.warn("Incorrect old password provided for employee: {}", employeeId);
            throw new CustomException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
        }

        if(newPassword.length() < 6){
            logger.warn("New password too short for employee: {}", employeeId);
            throw new CustomException(HttpStatus.BAD_REQUEST, "New password must be at least 6 characters long");
        }

        employee.getUser().setPassword(passwordEncoder.encode(newPassword));
        employeeRepo.save(employee);
        logger.info("Password changed successfully for employee: {}", employeeId);
        return "Password changed successfully";
    }


    public String enrollCourse(Long employeeId, Long courseId) {
        logger.info("Employee {} enrolling in course: {}", employeeId, courseId);
        Enrollment enrollment = new Enrollment();
        Employee employee = employeeRepo.findById(employeeId).orElseThrow(() -> {
            logger.warn("Employee not found with id: {}", employeeId);
            return new CustomException(HttpStatus.NOT_FOUND, "Employee not found");
        });
        enrollment.setUser(employee.getUser());
        Course course = courseRepo.findById(courseId).orElseThrow(() -> {
            logger.warn("Course not found with id: {}", courseId);
            return new CustomException(HttpStatus.NOT_FOUND, "Course not found");
        });
        enrollment.setCourse(course);
        enrollment.setStartDate(LocalDateTime.now());
        enrollment.setEndDate(LocalDateTime.now().plusMonths(1));
        enrollment.setStatus(EnrollmentStatus.NOT_STARTED);

        course.addEnrollment(enrollment);
        employee.getUser().addEnrollment(enrollment);

        courseRepo.save(course);
        employeeRepo.save(employee);
        enrollmentRepo.save(enrollment);
        logger.info("Employee {} successfully enrolled in course: {}", employeeId, courseId);
        return "Enrolled in course successfully";
    }

    public List<CourseDto> getCompletedCourses(Long employeeId) {
        logger.info("Fetching completed courses for employee: {}", employeeId);
        Employee employee = employeeRepo.findById(employeeId).orElseThrow(() -> {
            logger.warn("Employee not found with id: {}", employeeId);
            return new CustomException(HttpStatus.NOT_FOUND, "Employee not found");
        });
        List<Enrollment> completedEnrollments = enrollmentRepo.findByUserAndStatus(employee.getUser(), EnrollmentStatus.COMPLETED);
        logger.info("Retrieved {} completed courses for employee: {}", completedEnrollments.size(), employeeId);
        return completedEnrollments.stream().map(enrollment -> new CourseDto(enrollment.getCourse().getId(), enrollment.getCourse().getCourseName(), enrollment.getCourse().getDescription(),enrollment.getCourse().getCourseDuration(),enrollment.getCourse().getPrerequisites(),enrollment.getCourse().getSkillSet())).toList();
    }


    public List<CertificateDto> getAllCertificatesOfUser() {
        logger.info("Fetching all certificates");
        List<Enrollment> completedEnrollments = enrollmentRepo.findByStatus(EnrollmentStatus.COMPLETED);
        logger.info("Retrieved {} certificates", completedEnrollments.size());
        return completedEnrollments.stream().map(enrollment -> new CertificateDto(enrollment.getCourse().getId(), enrollment.getUser().getId(), enrollment.getEndDate(), "here is the link to download")).toList();
    }


    public CertificateDto getCertificateById() {
        logger.info("Fetching certificate by id");
        List<Enrollment> completedEnrollments = enrollmentRepo.findByStatus(EnrollmentStatus.COMPLETED);
        logger.info("Retrieved certificate");
        return completedEnrollments.stream().map(enrollment -> new CertificateDto(enrollment.getCourse().getId(), enrollment.getUser().getId(), enrollment.getEndDate(), "here is the link to download"))
                .findFirst().orElseThrow(() -> {
                    logger.warn("Certificate not found");
                    return new CustomException(HttpStatus.NOT_FOUND, "Certificate not found");
                });
    }


}
