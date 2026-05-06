package com.practise.grower.dto.Admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseUpdateDto (
        Long id,

        String courseName,


        String description,

        Integer courseDuration,

        String prerequisites,

        String skillSet
){
}
