package com.practise.grower.controller.ManagerController;

import com.practise.grower.dto.User.UserResponseDto;
import com.practise.grower.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/manager")
public class ManagerEmployeeController {

    private final ManagerService managerService;

    // get all employees under a manager
    @GetMapping("/{managerId}/all")
    public List<UserResponseDto> getAllEmployeesUnderManager(@PathVariable Long managerId) {
        return managerService.getAllEmployeesUnderManager(managerId);
    }

    // get employee by id under a manager
    @GetMapping("/{employeeId}")
    public UserResponseDto getEmployeeByIdUnderManager(@PathVariable Long employeeId) {
        return managerService.getEmployeeByIdUnderManager(employeeId);
    }

    // filter all the employees who are on workStatus
    @GetMapping("/filter/{managerId}")
    public List<UserResponseDto> filterEmployeesByWorkStatus(@PathVariable Long managerId, @RequestParam String workStatus) {
        return managerService.filterEmployeesByWorkStatus(managerId,workStatus);
    }

}
