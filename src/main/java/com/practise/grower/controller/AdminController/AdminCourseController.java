package com.practise.grower.controller.AdminController;

import com.practise.grower.dto.Admin.*;
import com.practise.grower.dto.Admin.CourseDto;
import com.practise.grower.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/course")
@RequiredArgsConstructor
public class AdminCourseController {

    private final AdminService adminService;

    // create a new course
    @PostMapping()
    public String createCourse(@Valid @RequestBody CourseDto courseRequestDto) {
        return adminService.createCourse(courseRequestDto);
    }

    // get all courses
    @GetMapping("/all")
    public List<CourseDto> getAllCourses() {
        return adminService.getAllCourses();
    }

//    get course by id
    @GetMapping("/{courseId}")
    public CourseDto getCourseById(@PathVariable Long courseId) {
        return adminService.getCourseById(courseId);
    }

    // update course by id
    @PutMapping("/{courseId}")
    public String updateCourse(@PathVariable Long courseId, @Valid @RequestBody CourseUpdateDto courseRequestDto) {
        return adminService.updateCourse(courseId, courseRequestDto);
    }

    // delete course by id
    @DeleteMapping("/{courseId}")
    public String deleteCourse(@PathVariable Long courseId) {
        return adminService.deleteCourse(courseId);
    }

    // add modules to the course
    @PostMapping("/{courseId}/modules")
    public String addModulesToCourse(@PathVariable Long courseId, @RequestBody List<AddModuleDto> moduleDtoList) {
        return adminService.addModulesToCourse(courseId, moduleDtoList);
    }

    // update modules in the course
    @PutMapping("{courseId}/modules/{moduleId}")
    public String updateModuleInCourse(@PathVariable Long courseId, @PathVariable Long moduleId, @RequestBody UpdateModuleDto moduleDto) {
        return adminService.updateModuleInCourse(courseId, moduleId, moduleDto);
    }


     // delete module
    @DeleteMapping("{courseId}/modules/{moduleId}")
    public String deleteModuleFromCourse(@PathVariable Long courseId, @PathVariable Long moduleId) {
        return adminService.deleteModuleFromCourse(courseId, moduleId);
    }



}
