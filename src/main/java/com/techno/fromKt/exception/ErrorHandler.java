package com.techno.fromKt.exception;

import com.techno.fromKt.domain.dto.response.ResMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResMessageDto<String>> handleResponseStatusException(ResponseStatusException exception) {
        String message = exception.getReason();
        int errorStatus = HttpStatus.BAD_REQUEST.value();
        ResMessageDto<String> bodyResp = new ResMessageDto<>(errorStatus, message, null);
        return ResponseEntity.status(errorStatus).body(bodyResp);
    }
}
