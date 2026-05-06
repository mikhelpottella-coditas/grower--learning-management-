package com.practise.grower.controller.UserController;

import com.practise.grower.dto.User.UserProfileResponseDto;
import com.practise.grower.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final EmployeeService employeeService;


    @GetMapping("/profile/{employeeId}")
    public UserProfileResponseDto getUserProfile(@PathVariable Long employeeId) {
        return employeeService.getUserProfile(employeeId);
    }

    @PutMapping("/profile/update/{employeeId}")
    public String updateUserProfile(@PathVariable Long employeeId, @RequestBody UserProfileResponseDto userProfileResponseDto) {
        return employeeService.updateUserProfile(employeeId, userProfileResponseDto);
    }


    // change password
    @PutMapping ("/change-password/{employeeId}")
    public String changePassword(@PathVariable Long employeeId, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return employeeService.changePassword(employeeId, oldPassword, newPassword);
    }

}
