package com.techno.fromKt.service.impl;

import com.techno.fromKt.domain.dto.response.ResJwtValidationDto;
import com.techno.fromKt.service.ValidateTokenService;
import com.techno.fromKt.util.JwtGenerator;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class ValidateTokenServiceImpl implements ValidateTokenService {

    @Override
    public ResJwtValidationDto validateToken(String token) {
        Claims claim = new JwtGenerator().decodeJwt(token);
        return new ResJwtValidationDto(
                claim.getSubject(),
                claim.get("username").toString(),
                claim.get("type").toString()
        );
    }
}
