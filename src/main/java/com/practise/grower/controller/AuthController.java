package com.practise.grower.controller;

import com.practise.grower.dto.LoginDto;
import com.practise.grower.dto.RegisterDto;
import com.practise.grower.security.JwtUtil;
import com.practise.grower.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterDto registerDto) {
        String result = userService.register(registerDto);
        logger.info("User registered successfully");
        return result;
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginDto loginDto) {
        logger.info("User login attempt for username");
        String result = userService.login(loginDto);
        logger.info("User logged in successfully");
        return result;
    }
}
