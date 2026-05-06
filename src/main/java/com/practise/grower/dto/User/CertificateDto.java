package com.practise.grower.dto.User;

import java.time.LocalDateTime;

public record CertificateDto(
        Long courseId,
        Long UserId,
        LocalDateTime issueDate,
        String CertificateUrl
) {
}
