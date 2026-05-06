package com.practise.grower.controller.UserController;

import com.practise.grower.dto.User.CertificateDto;
import com.practise.grower.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/certificate")
public class UserCertificateController {

    private final EmployeeService employeeService;

    // get all the certificates of the user
    @GetMapping("{userId}")
    public List<CertificateDto> getAllCertificatesOfUser(){
        return employeeService.getAllCertificatesOfUser();
    }

    // get the certificate by id
    @GetMapping("/{certificateId}")
    public CertificateDto getCertificateById(){
        return employeeService.getCertificateById();
    }

}
