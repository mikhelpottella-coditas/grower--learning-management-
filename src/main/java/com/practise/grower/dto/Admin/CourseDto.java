package com.practise.grower.dto.Admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseDto(

        Long id,

        @NotBlank(message = "Course name is required")
        String courseName,


        String description,

        @NotNull(message = "Duration is required")
        Integer courseDuration,

        String prerequisites,

        String skillSet

        ) {

}
