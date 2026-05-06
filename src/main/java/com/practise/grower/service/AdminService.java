package com.practise.grower.service;

import com.practise.grower.dto.Admin.*;
import com.practise.grower.entity.Course;
import com.practise.grower.entity.Manager;
import com.practise.grower.entity.Module;
import com.practise.grower.entity.User;
import com.practise.grower.exception.CustomException;
import com.practise.grower.repository.CourseRepo;
import com.practise.grower.repository.ManagerRepo;
import com.practise.grower.repository.ModuleRepo;
import com.practise.grower.repository.UserRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final CourseRepo courseRepo;
    private final UserRepo userRepo;
    private final ModuleRepo moduleRepo;
    private final ManagerRepo managerRepo;
    private final PasswordEncoder passwordEncoder;

    public String createCourse(CourseDto courseRequestDto) {


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

        return "course created successfully";

    }

    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepo.findAll();
        List<CourseDto> courseDtoList = new ArrayList<>();

        courses.forEach(course -> {
            courseDtoList.add(new CourseDto(course.getId(),course.getCourseName(), course.getDescription(), course.getCourseDuration(), course.getPrerequisites(), course.getSkillSet()));
        });

        return courseDtoList;

    }

    public CourseDto getCourseById(Long courseId) {
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "course not found with the given id"));
        return new CourseDto(course.getId(),course.getCourseName(), course.getDescription(), course.getCourseDuration(), course.getPrerequisites(), course.getSkillSet());
    }

    public String updateCourse(Long courseId, @Valid CourseUpdateDto courseRequestDto) {
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "course not found with the given id"));

        if (courseRequestDto.courseName() != null) course.setCourseName(courseRequestDto.courseName());
        if (courseRequestDto.description() != null) course.setDescription(courseRequestDto.description());
        if (courseRequestDto.courseDuration() != null) course.setCourseDuration(courseRequestDto.courseDuration());
        if (courseRequestDto.prerequisites() != null) course.setPrerequisites(courseRequestDto.prerequisites());
        if (courseRequestDto.skillSet() != null) course.setSkillSet(courseRequestDto.skillSet());

        courseRepo.save(course);

        return "course updated successfully";
    }

    public String deleteCourse(Long courseId) {
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "course not found with the given id"));
        courseRepo.delete(course);
        return "course deleted successfully";
    }

    public String addModulesToCourse(Long courseId, List<AddModuleDto> moduleDtoList) {
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "course not found with the given id"));


        moduleDtoList.forEach(moduleDto -> {
            Module module = new Module();
            module.setModuleName(moduleDto.moduleName());
            module.setDescription(moduleDto.description());
            module.setResourceLink(moduleDto.resourceLink());
            course.addModule(module);
        });

        courseRepo.save(course);

        return "modules added to the course successfully";
    }


    public String updateModuleInCourse(Long courseId, Long moduleId, UpdateModuleDto moduleDto) {
        Module module = moduleRepo.findById(moduleId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "module not found with the given id"));

        if(moduleDto.moduleName() != null) module.setModuleName(moduleDto.moduleName());
        if(moduleDto.description() != null) module.setDescription(moduleDto.description());
        if(moduleDto.resourceLink() != null) module.setResourceLink(moduleDto.resourceLink());

        moduleRepo.save(module);
        return "module updated successfully in the course";
    }


    public String deleteModuleFromCourse(Long courseId, Long moduleId) {
        Module module = moduleRepo.findById(moduleId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "module not found with the given id"));
        moduleRepo.delete(module);
        return "module deleted successfully from the course";
    }

    public String createManager(@Valid ManagerRequestDto managerRequestDto) {
        User user = new User();
        user.setUsername(managerRequestDto.name());
        user.setEmail(managerRequestDto.email());
        user.setPassword(passwordEncoder.encode(managerRequestDto.password()));
        user.setRole(com.practise.grower.enums.Role.MANAGER);

        userRepo.save(user);

        Manager manager = new Manager();
        manager.setUser(user);
        managerRepo.save(manager);
        return "manager created successfully";
    }

    public List<ManagerResponseDto> getAllManagers() {
        List<Manager> managers = managerRepo.findAll();
        List<ManagerResponseDto> managerResponseDtoList = new ArrayList<>();

        managers.forEach(manager -> {
            managerResponseDtoList.add(new ManagerResponseDto(manager.getId(), manager.getUser().getUsername(), manager.getUser().getEmail(), manager.getDepartment()));
        });

        return managerResponseDtoList;
    }

    public ManagerResponseDto getManagerById(Long managerId) {
        Manager manager = managerRepo.findById(managerId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "manager not found with the given id"));
        return new ManagerResponseDto(manager.getId(), manager.getUser().getUsername(), manager.getUser().getEmail(), manager.getDepartment());
    }

    public String updateManagerById(Long managerId, ManagerRequestDto managerRequestDto) {
        Manager manager = managerRepo.findById(managerId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "manager not found with the given id"));

        if(managerRequestDto.name() != null) manager.getUser().setUsername(managerRequestDto.name());
        if(managerRequestDto.email() != null) manager.getUser().setEmail(managerRequestDto.email());
        if(managerRequestDto.password() != null) manager.getUser().setPassword(managerRequestDto.password());
        if(managerRequestDto.department() != null) manager.setDepartment(managerRequestDto.department());

        userRepo.save(manager.getUser());
        managerRepo.save(manager);
        return "manager updated successfully";
    }

    public String deleteManagerById(Long managerId) {
        Manager manager = managerRepo.findById(managerId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "manager not found with the given id"));
        User user = manager.getUser();
        userRepo.delete(user);
        managerRepo.delete(manager);
        return "manager deleted successfully";
    }
}
