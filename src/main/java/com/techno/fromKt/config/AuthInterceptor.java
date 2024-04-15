package com.techno.fromKt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.util.JwtGenerator;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token == null) {
            ResMessageDto<String> body = new ResMessageDto<>(
                    HttpStatus.FORBIDDEN.value(), "You don't have permission", null
            );
            internalServerError(body, response);
            return false;
        }
        try {
            new JwtGenerator().decodeJwt(token).get("sub");
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            ResMessageDto<String> body = new ResMessageDto<>(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Invalid token",
//                    e.getMessage(),
                    null
            );
            internalServerError(body, response);
            return false;
        }
        return true;
    }

    private void internalServerError(ResMessageDto<String> body, HttpServletResponse response) throws IOException {
        response.setStatus(body.getStatus());
        response.setContentType("application/json");
        response.getWriter().write(convertObjectToJson(body));
    }

    private String convertObjectToJson(ResMessageDto<String> dto) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(dto);
    }
}
