package com.practise.grower.dto.Admin;

import jakarta.validation.constraints.NotBlank;

public record UpdateModuleDto(
        String moduleName,

        String description,

        String resourceLink
) {
}
