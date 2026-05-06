package com.practise.grower.dto.User;


import com.practise.grower.dto.Admin.CourseDto;
import com.practise.grower.enums.WorkStatus;

import java.util.List;

public record UserResponseDto(
        Long id,
        String name,
        String email,
        WorkStatus workStatus,
        List<CourseDto> courseDtoList,
        List<CertificateDto> certificateDtoList
) {
}
