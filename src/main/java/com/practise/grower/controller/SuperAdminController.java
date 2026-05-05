package com.practise.grower.controller;

import com.practise.grower.dto.SuperAdmin.AdminRequestDto;
import com.practise.grower.dto.SuperAdmin.AdminResponceDto;
import com.practise.grower.service.SuperAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/superadmin")
@RequiredArgsConstructor
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    @PostMapping("/admin")
    public String registerAdmin(@Valid @RequestBody AdminRequestDto adminRequestDto){
        return superAdminService.registerAdmin(adminRequestDto);
    }

    @GetMapping("/admins")
    public List<AdminResponceDto> getAllAdmins(){
        return superAdminService.getAllAdmins();
    }


    @GetMapping("/admin/{adminId}")
    public AdminResponceDto getAdminById(@PathVariable Long adminId) {
        return superAdminService.getAdminById(adminId);
    }

    @PutMapping("/admin/{adminId}")
    public String updateAdmin(@PathVariable Long adminId, @Valid @RequestBody AdminRequestDto adminRequestDto) {
        return superAdminService.updateAdmin(adminId, adminRequestDto);
    }

    @DeleteMapping("/admin/{adminId}")
    public String deleteAdmin(@PathVariable Long adminId) {
        return superAdminService.deleteAdmin(adminId);
    }
}
