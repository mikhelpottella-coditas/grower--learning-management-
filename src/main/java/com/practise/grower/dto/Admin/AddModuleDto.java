package com.practise.grower.dto.Admin;

import jakarta.validation.constraints.NotBlank;

public record AddModuleDto(

        @NotBlank(message = "Module name is required")
        String moduleName,

        String description,

        @NotBlank(message = "resource link  is required")
        String resourceLink
) {
}
