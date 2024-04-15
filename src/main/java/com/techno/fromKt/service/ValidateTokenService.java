package com.techno.fromKt.service;

import com.techno.fromKt.domain.dto.response.ResJwtValidationDto;

public interface ValidateTokenService {
    ResJwtValidationDto validateToken(String token);
}
