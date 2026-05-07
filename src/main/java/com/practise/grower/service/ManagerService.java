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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private static final Logger logger = LoggerFactory.getLogger(ManagerService.class);

    private final EmployeeRepo employeeRepo;
    private final UserRepo userRepo;
    private final ManagerRepo managerRepo;


    public List<UserResponseDto> getAllEmployeesUnderManager(Long managerId) {
        logger.info("Fetching all employees for manager: {}", managerId);

        List<User> userList = userRepo.findAllByManager(managerRepo.findById(managerId).orElseThrow());
        logger.info("Retrieved {} employees for manager: {}", userList.size(), managerId);

        return userList.stream().map(user ->
                new UserResponseDto(user.getId(), user.getUsername(),
                        user.getEmail(), user.getEmployee().getWorkStatus(),
                        new ArrayList<>(),new ArrayList<>())).toList();
    }


    public UserResponseDto getEmployeeByIdUnderManager(Long employeeId) {
        logger.info("Fetching details for employee: {}", employeeId);

        Employee employee = employeeRepo.findById(employeeId).orElseThrow(()-> {
            logger.warn("Employee not found with id: {}", employeeId);
            return new CustomException(HttpStatus.NOT_FOUND, "employee not found with the given id");
        });
        User user = employee.getUser();

        List<CourseDto> courseDtoList = user.getCourse().stream().map(course ->
                new CourseDto(course.getId(), course.getCourseName(),course.getDescription(),
                        course.getCourseDuration(), course.getPrerequisites(), course.getSkillSet()))
                .toList();
        List<CertificateDto> certificateDtoList = user.getCertificates().stream().map(certificate ->
                new CertificateDto( certificate.getUser().getId(), certificate.getCourse().getId(),
                        certificate.getIssuedAt(),certificate.getCertificateUrl())).toList();

        logger.info("Retrieved employee details for: {} with {} courses and {} certificates", employeeId, courseDtoList.size(), certificateDtoList.size());

        return new UserResponseDto(user.getId(), user.getUsername(),
                user.getEmail(), user.getEmployee().getWorkStatus(),
                courseDtoList,certificateDtoList);
    }


    public List<UserResponseDto> filterEmployeesByWorkStatus(Long managerId,String workStatus) {
        logger.info("Filtering employees for manager: {} with work status: {}", managerId, workStatus);

        List<UserResponseDto> userResponseDtoList = getAllEmployeesUnderManager(managerId);

        List<UserResponseDto> filteredList = userResponseDtoList.stream().filter(user-> user.workStatus().name().equals(workStatus)).toList();
        logger.info("Filtered {} employees with work status: {}", filteredList.size(), workStatus);

        return filteredList;
    }
}
