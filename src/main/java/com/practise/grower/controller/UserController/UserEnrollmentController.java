package com.practise.grower.controller.UserController;

import com.practise.grower.dto.Admin.CourseDto;
import com.practise.grower.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/enrollment")
public class UserEnrollmentController {

    private final UserService userService;

    // get all enrollments for a user by id
    @PostMapping("/{userId}")
    public List<CourseDto> getAllEnrollmentsForUserById(Long userId) {
        return userService.getAllEnrollmentsForUserById(userId);
    }



}
