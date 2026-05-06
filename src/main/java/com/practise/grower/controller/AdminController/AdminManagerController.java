package com.practise.grower.controller.AdminController;


import com.practise.grower.dto.Admin.ManagerRequestDto;
import com.practise.grower.dto.Admin.ManagerResponseDto;
import com.practise.grower.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/manager")
@RequiredArgsConstructor
public class AdminManagerController {

    private final AdminService adminService;

    // create a manager
    @PostMapping()
    public String createManager(@Valid @RequestBody ManagerRequestDto managerRequestDto){
        return adminService.createManager(managerRequestDto);
    }

    // get all managers
    @GetMapping("/all")
    public List<ManagerResponseDto> getAllManagers(){
        return adminService.getAllManagers();
    }

    // get manager by id
    @GetMapping("/{managerId}")
    public ManagerResponseDto getManagerById(@PathVariable Long managerId){
        return adminService.getManagerById(managerId);
    }

    // update manager by id
    @PutMapping("/{managerId}")
    public String updateManagerById(@PathVariable Long managerId, @RequestBody ManagerRequestDto managerRequestDto){
        return adminService.updateManagerById(managerId, managerRequestDto);
    }

        // delete manager by id
    @DeleteMapping("/{managerId}")
    public String deleteManagerById(@PathVariable Long managerId){
        return adminService.deleteManagerById(managerId);
    }

}
