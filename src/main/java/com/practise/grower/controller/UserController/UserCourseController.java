package com.practise.grower.controller.UserController;

import com.practise.grower.dto.Admin.CourseDto;
import com.practise.grower.service.AdminService;
import com.practise.grower.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/course")
public class UserCourseController {

    private final AdminService adminService;
    private final EmployeeService employeeService;

    @GetMapping("/all")
    public List<CourseDto> getAllCourses(){
        return adminService.getAllCourses();
    }

    @GetMapping("{courseId}")
    public CourseDto getCourseById(Long courseId){
        return adminService.getCourseById(courseId);
    }

    // enroll course
    @PostMapping("/course/{employeeId}/{courseId}/enroll")
    public String enrollCourse(Long employeeId, Long courseId) {
        return employeeService.enrollCourse(employeeId, courseId);
    }

    // completed courses
    @GetMapping("/employee/{employeeId}/completed")
    public List<CourseDto> getCompletedCourses(Long employeeId) {
        return employeeService.getCompletedCourses(employeeId);
    }
}
