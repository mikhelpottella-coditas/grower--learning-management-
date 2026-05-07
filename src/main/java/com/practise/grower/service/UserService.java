package com.practise.grower.service;

import com.practise.grower.dto.Admin.CourseDto;
import com.practise.grower.dto.LoginDto;
import com.practise.grower.dto.RegisterDto;
import com.practise.grower.entity.Employee;
import com.practise.grower.entity.Enrollment;
import com.practise.grower.entity.User;
import com.practise.grower.enums.Role;
import com.practise.grower.exception.CustomException;
import com.practise.grower.repository.EmployeeRepo;
import com.practise.grower.repository.UserRepo;
import com.practise.grower.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    private final UserRepo userRepo;
    private final EmployeeRepo employeeRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            logger.warn("User not found with the name : {}",username);
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public String register(@Valid RegisterDto registerDto) {
        logger.info("Registering user");
        if (userRepo.existsByEmail(registerDto.email())) {
            logger.warn("Email already exists");
            throw new CustomException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        User user = new User();
        user.setUsername(registerDto.name());
        user.setEmail(registerDto.email());
        user.setPassword(passwordEncoder.encode(registerDto.password()));
        user.setRole(Role.USER);
        userRepo.save(user);

        Employee employee = new Employee();
        employee.setUser(user);
        employeeRepo.save(employee);

        logger.info("User registered successfully");
        return "User registered successfully";
    }

    public String login(@Valid LoginDto loginDto) {
        logger.info("Logging in user");
        User user = userRepo.findByUsername(loginDto.username());
        if(user == null) {
            logger.warn("Invalid credentials for user");
            throw  new CustomException(HttpStatus.NOT_FOUND, "Invalid credentials");
        }
        if(!passwordEncoder.matches(loginDto.password(), user.getPassword())){
            logger.warn("Invalid password for user");
            throw  new CustomException(HttpStatus.NOT_FOUND, "Invalid credentials");
        }

        logger.info("User logged in successfully");
        return jwtUtil.generateToken(user.getUsername(), user.getEmail());
    }

    public List<CourseDto> getAllEnrollmentsForUserById(Long userId) {
        logger.info("Fetching enrollments for user ID: {}",userId);
        User user = userRepo.findById(userId).orElseThrow(() -> {
            logger.warn("User not found ");
            return new CustomException(HttpStatus.NOT_FOUND, "User not found with the given id");
        });
        List<Enrollment> enrollmentList = user.getEnrollments();

        logger.info("Fetched enrollments for user  ");
        return enrollmentList.stream().map(enrollment ->

                new CourseDto(enrollment.getCourse().getId(),
                        enrollment.getCourse().getCourseName(),
                        enrollment.getCourse().getDescription(),
                        enrollment.getCourse().getCourseDuration(),
                        enrollment.getCourse().getSkillSet(),
                        enrollment.getCourse().getPrerequisites())).toList();

    }
}
