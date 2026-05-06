package com.practise.grower.dto.User;

import com.practise.grower.enums.WorkStatus;

public record UserProfileResponseDto(
        Long id,
        String name,
        String email,
        WorkStatus workStatus
) {
}
