package com.practise.grower.service;

import com.practise.grower.dto.Admin.*;
import com.practise.grower.dto.User.UserResponseDto;
import com.practise.grower.entity.*;
import com.practise.grower.entity.Module;
import com.practise.grower.enums.Role;
import com.practise.grower.exception.CustomException;
import com.practise.grower.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    private final CourseRepo courseRepo;
    private final UserRepo userRepo;
    private final ModuleRepo moduleRepo;
    private final ManagerRepo managerRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepo employeeRepo;

    public String createCourse(CourseDto courseRequestDto) {
        logger.info("Creating course");

        Course course = new Course();
        course.setCourseName(courseRequestDto.courseName());
        if (courseRequestDto.description() != null) course.setDescription(courseRequestDto.description());
        if (courseRequestDto.courseDuration() != null) course.setCourseDuration(courseRequestDto.courseDuration());
        if (courseRequestDto.prerequisites() != null) course.setPrerequisites(courseRequestDto.prerequisites());
        if (courseRequestDto.skillSet() != null) course.setSkillSet(courseRequestDto.skillSet());


        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(username);
        user.addCourse(course);
        userRepo.save(user);
        courseRepo.save(course);

        logger.info("Course created successfully");
        return "course created successfully";

    }

    public List<CourseDto> getAllCourses() {
        logger.info("Fetching all courses");
        List<Course> courses = courseRepo.findAll();
        List<CourseDto> courseDtoList = new ArrayList<>();

        courses.forEach(course -> {
            courseDtoList.add(new CourseDto(course.getId(),course.getCourseName(), course.getDescription(), course.getCourseDuration(), course.getPrerequisites(), course.getSkillSet()));
        });

        logger.info("Fetched courses");
        return courseDtoList;

    }

    public CourseDto getCourseById(Long courseId) {
        logger.info("Fetching course by ID");
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "course not found with the given id"));
        return new CourseDto(course.getId(),course.getCourseName(), course.getDescription(), course.getCourseDuration(), course.getPrerequisites(), course.getSkillSet());
    }

    public String updateCourse(Long courseId, @Valid CourseUpdateDto courseRequestDto) {
        logger.info("Updating course ID");
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "course not found with the given id"));

        if (courseRequestDto.courseName() != null) course.setCourseName(courseRequestDto.courseName());
        if (courseRequestDto.description() != null) course.setDescription(courseRequestDto.description());
        if (courseRequestDto.courseDuration() != null) course.setCourseDuration(courseRequestDto.courseDuration());
        if (courseRequestDto.prerequisites() != null) course.setPrerequisites(courseRequestDto.prerequisites());
        if (courseRequestDto.skillSet() != null) course.setSkillSet(courseRequestDto.skillSet());

        courseRepo.save(course);

        logger.info("Course updated successfully");
        return "course updated successfully";
    }

    public String deleteCourse(Long courseId) {
        logger.info("Deleting course");
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "course not found with the given id"));
        courseRepo.delete(course);
        logger.info("Course deleted successfully");
        return "course deleted successfully";
    }

    public String addModulesToCourse(Long courseId, List<AddModuleDto> moduleDtoList) {
        logger.info("Adding modules to course ");
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "course not found with the given id"));


        moduleDtoList.forEach(moduleDto -> {
            Module module = new Module();
            module.setModuleName(moduleDto.moduleName());
            module.setDescription(moduleDto.description());
            module.setResourceLink(moduleDto.resourceLink());
            course.addModule(module);
        });

        courseRepo.save(course);

        logger.info("Modules added to course successfully");
        return "modules added to the course successfully";
    }


    public String updateModuleInCourse(Long courseId, Long moduleId, UpdateModuleDto moduleDto) {
        logger.info("Updating module in course");
        Module module = moduleRepo.findById(moduleId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "module not found with the given id"));

        if(moduleDto.moduleName() != null) module.setModuleName(moduleDto.moduleName());
        if(moduleDto.description() != null) module.setDescription(moduleDto.description());
        if(moduleDto.resourceLink() != null) module.setResourceLink(moduleDto.resourceLink());

        moduleRepo.save(module);
        logger.info("Module updated successfully");
        return "module updated successfully in the course";
    }


    public String deleteModuleFromCourse(Long courseId, Long moduleId) {
        logger.info("Deleting module from course");
        Module module = moduleRepo.findById(moduleId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "module not found with the given id"));
        moduleRepo.delete(module);
        logger.info("Module deleted successfully");
        return "module deleted successfully from the course";
    }

    public String createManager(@Valid ManagerRequestDto managerRequestDto) {
        logger.info("Creating manager");
        User user = new User();
        user.setUsername(managerRequestDto.name());
        user.setEmail(managerRequestDto.email());
        user.setPassword(passwordEncoder.encode(managerRequestDto.password()));
        user.setRole(com.practise.grower.enums.Role.MANAGER);

        userRepo.save(user);

        Manager manager = new Manager();
        manager.setUser(user);
        managerRepo.save(manager);
        logger.info("Manager created successfully");
        return "manager created successfully";
    }

    public List<ManagerResponseDto> getAllManagers() {
        logger.info("Fetching all managers");
        List<Manager> managers = managerRepo.findAll();
        List<ManagerResponseDto> managerResponseDtoList = new ArrayList<>();

        managers.forEach(manager -> {
            managerResponseDtoList.add(new ManagerResponseDto(manager.getId(), manager.getUser().getUsername(), manager.getUser().getEmail(), manager.getDepartment()));
        });

        logger.info("Fetched managers");
        return managerResponseDtoList;
    }

    public ManagerResponseDto getManagerById(Long managerId) {
        logger.info("Fetching manager by ID");
        Manager manager = managerRepo.findById(managerId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "manager not found with the given id"));
        return new ManagerResponseDto(manager.getId(), manager.getUser().getUsername(), manager.getUser().getEmail(), manager.getDepartment());
    }

    public String updateManagerById(Long managerId, ManagerRequestDto managerRequestDto) {
        logger.info("Updating manager ID");
        Manager manager = managerRepo.findById(managerId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "manager not found with the given id"));

        if(managerRequestDto.name() != null) manager.getUser().setUsername(managerRequestDto.name());
        if(managerRequestDto.email() != null) manager.getUser().setEmail(managerRequestDto.email());
        if(managerRequestDto.password() != null) manager.getUser().setPassword(managerRequestDto.password());
        if(managerRequestDto.department() != null) manager.setDepartment(managerRequestDto.department());

        userRepo.save(manager.getUser());
        managerRepo.save(manager);
        logger.info("Manager updated successfully");
        return "manager updated successfully";
    }

    public String deleteManagerById(Long managerId) {
        logger.info("Deleting manager by ID");
        Manager manager = managerRepo.findById(managerId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "manager not found with the given id"));
        User user = manager.getUser();
        userRepo.delete(user);
        managerRepo.delete(manager);
        logger.info("Manager deleted successfully");
        return "manager deleted successfully";
    }

    public List<UserResponseDto> getAllEmployees() {
        logger.info("Fetching all employees");
        List<User> users = userRepo.findByRole(Role.USER);
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();

        users.forEach(user -> {
            userResponseDtoList.add(new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(),user.getEmployee().getWorkStatus(),new ArrayList<>(),new ArrayList<>()));
        });


        return userResponseDtoList;
    }


    public String deleteUserById(Long id) {
        logger.info("Deleting user by ID");
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "user not found with the given id"));
        User user = employee.getUser();
        userRepo.delete(user);
        employeeRepo.delete(employee);
        logger.info("User deleted successfully");
        return "user deleted successfully";
    }
}
