package com.practise.grower.controller.AdminController;

import com.practise.grower.dto.User.UserProfileResponseDto;
import com.practise.grower.dto.User.UserResponseDto;
import com.practise.grower.service.AdminService;
import com.practise.grower.service.EmployeeService;
import com.practise.grower.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminService adminService;
    private final ManagerService managerService;
    private final EmployeeService employeeService;

    // get all the Employees
    @GetMapping("/all")
    public List<UserResponseDto> getAllEmployees(){
        return adminService.getAllEmployees();
    }

    // get user by id
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id){
        return managerService.getEmployeeByIdUnderManager(id);
    }

    @PutMapping("/{id}")
    public String updateUserById(@PathVariable Long id, @RequestBody UserProfileResponseDto userProfileResponseDto){
       return employeeService.updateUserProfile(id, userProfileResponseDto);
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable Long id) {
        return adminService.deleteUserById(id);
    }



}
