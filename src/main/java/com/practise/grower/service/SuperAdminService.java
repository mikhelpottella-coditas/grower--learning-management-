package com.practise.grower.service;

import com.practise.grower.dto.SuperAdmin.AdminRequestDto;
import com.practise.grower.dto.SuperAdmin.AdminResponceDto;
import com.practise.grower.entity.User;
import com.practise.grower.enums.Role;
import com.practise.grower.exception.CustomException;
import com.practise.grower.repository.UserRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuperAdminService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public String registerAdmin(@Valid AdminRequestDto adminRequestDto) {
        User user = new User();
        user.setUsername(adminRequestDto.name());
        user.setEmail(adminRequestDto.email());
        user.setPassword(passwordEncoder.encode(adminRequestDto.password()));
        user.setRole(Role.ADMIN);
        userRepo.save(user);
        return "Admin registered successfully";
    }


    public List<AdminResponceDto> getAllAdmins() {
        List<User> admins =  userRepo.findAllByRole(Role.ADMIN);
        List<AdminResponceDto> adminResponceDtos = new ArrayList<>();
        admins.forEach(admin -> {
            adminResponceDtos.add(new AdminResponceDto(admin.getId(),admin.getUsername(), admin.getEmail()));
        });

        return adminResponceDtos;
    }

    public AdminResponceDto getAdminById(Long adminId) {
        User admin = userRepo.findById(adminId).orElseThrow(()->new CustomException(HttpStatus.NOT_FOUND, "Admin not found with the given id"));
        return new AdminResponceDto(admin.getId(), admin.getUsername(), admin.getEmail());
    }

    public String updateAdmin(Long adminId, @Valid AdminRequestDto adminRequestDto) {
        User admin = userRepo.findById(adminId).orElseThrow(()->new CustomException(HttpStatus.NOT_FOUND, "Admin not found with the given id"));
        if(adminRequestDto.name()!=null) admin.setUsername(adminRequestDto.name());
        if(adminRequestDto.email()!=null)admin.setEmail(adminRequestDto.email());
        userRepo.save(admin);
        return "Admin updated successfully";
    }

    public String deleteAdmin(Long adminId) {
        User admin = userRepo.findById(adminId).orElseThrow(()->new CustomException(HttpStatus.NOT_FOUND, "Admin not found with the given id"));
        userRepo.delete(admin);
        return "Admin deleted successfully";
    }
}
