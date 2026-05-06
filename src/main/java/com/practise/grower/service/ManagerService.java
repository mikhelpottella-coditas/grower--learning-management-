package com.practise.grower.service;

import com.practise.grower.dto.Admin.CourseDto;
import com.practise.grower.dto.User.CertificateDto;
import com.practise.grower.dto.User.UserResponseDto;
import com.practise.grower.entity.Employee;
import com.practise.grower.entity.User;
import com.practise.grower.exception.CustomException;
import com.practise.grower.repository.EmployeeRepo;
import com.practise.grower.repository.ManagerRepo;
import com.practise.grower.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final EmployeeRepo employeeRepo;
    private final UserRepo userRepo;
    private final ManagerRepo managerRepo;


    public List<UserResponseDto> getAllEmployeesUnderManager(Long managerId) {

        List<User> userList = userRepo.findAllByManager(managerRepo.findById(managerId).orElseThrow());

//        List<CourseDto> courseDtoList = userList.stream().map(user-> user.getCourse().stream().map(course ->
//                new CourseDto(course.getId(), course.getCourseName(),course.getDescription(),
//                        course.getCourseDuration(), course.getPrerequisites(), course.getSkillSet()))
//                        .toList()).flatMap(List::stream).toList();
//
//        List<CertificateDto> certificateDtoList = userList.stream().map(user -> user.getCertificates().stream().map(certificate ->
//                new CertificateDto( certificate.getUser().getId(), certificate.getCourse().getId(),
//                       certificate.getIssuedAt(),certificate.getCertificateUrl())).toList()).flatMap(List::stream).toList();


        return userList.stream().map(user ->
                new UserResponseDto(user.getId(), user.getUsername(),
                        user.getEmail(), user.getEmployee().getWorkStatus(),
                        new ArrayList<>(),new ArrayList<>())).toList();
    }


    public UserResponseDto getEmployeeByIdUnderManager(Long employeeId) {
        Employee employee = employeeRepo.findById(employeeId).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "employee not found with the given id"));
        User user = employee.getUser();

        List<CourseDto> courseDtoList = user.getCourse().stream().map(course ->
                new CourseDto(course.getId(), course.getCourseName(),course.getDescription(),
                        course.getCourseDuration(), course.getPrerequisites(), course.getSkillSet()))
                .toList();
        List<CertificateDto> certificateDtoList = user.getCertificates().stream().map(certificate ->
                new CertificateDto( certificate.getUser().getId(), certificate.getCourse().getId(),
                        certificate.getIssuedAt(),certificate.getCertificateUrl())).toList();


        return new UserResponseDto(user.getId(), user.getUsername(),
                user.getEmail(), user.getEmployee().getWorkStatus(),
                courseDtoList,certificateDtoList);
    }


    public List<UserResponseDto> filterEmployeesByWorkStatus(Long managerId,String workStatus) {

        List<UserResponseDto> userResponseDtoList = getAllEmployeesUnderManager(managerId);

        return userResponseDtoList.stream().filter(user-> user.workStatus().name().equals(workStatus)).toList();

    }
}


