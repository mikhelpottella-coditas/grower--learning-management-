package com.practise.grower.controller;

import com.practise.grower.dto.LoginDto;
import com.practise.grower.dto.RegisterDto;
import com.practise.grower.security.JwtUtil;
import com.practise.grower.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterDto registerDto) {
        return userService.register(registerDto);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }
}
