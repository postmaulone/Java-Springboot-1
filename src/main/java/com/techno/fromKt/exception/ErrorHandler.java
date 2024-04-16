package com.techno.fromKt.exception;

import com.techno.fromKt.domain.dto.response.ResMessageDto;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<String> errors = new ArrayList<>();
        BindingResult bindingResult = exception.getBindingResult();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.add(fieldError.getDefaultMessage());
        }
        Map<String, Object> result = new HashMap<>();
        result.put("status", "F");
        result.put("error", "field");
        result.put("message", errors);
        return ResponseEntity.badRequest().body(result);
    }
    @ExceptionHandler(SomethingWrongException.class)
    public ResponseEntity<ResMessageDto<String>> handleDataNotFound(RuntimeException exception) {
        exception.printStackTrace();
        return ResponseEntity.badRequest().body(new ResMessageDto<>(500, (String) exception.getMessage(), null));
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResMessageDto<String>> handleResponseStatusException(ResponseStatusException exception) {
        String message = exception.getReason();
        int errorStatus = HttpStatus.BAD_REQUEST.value();
        ResMessageDto<String> bodyResp = new ResMessageDto<>(errorStatus, message, null);
        return ResponseEntity.status(errorStatus).body(bodyResp);
    }
}
