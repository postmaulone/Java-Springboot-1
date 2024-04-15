package com.techno.fromKt.controller;

import com.techno.fromKt.domain.dto.request.ReqLoginDto;
import com.techno.fromKt.domain.dto.response.ResJwtValidationDto;
import com.techno.fromKt.domain.dto.response.ResLoginDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.service.LoginService;
import com.techno.fromKt.service.ValidateTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/user")
public class LoginController {
    private final LoginService loginService;
    private final ValidateTokenService validateTokenService;

    public LoginController(LoginService loginService, ValidateTokenService validateTokenService) {
        this.loginService = loginService;
        this.validateTokenService = validateTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResMessageDto<ResLoginDto>> login(@RequestBody ReqLoginDto req) {
        ResLoginDto res = loginService.login(req);
        return ResponseEntity.ok(new ResMessageDto<>(200, "Success Login", res));
    }

    @GetMapping("/validate")
    public ResponseEntity<ResMessageDto<ResJwtValidationDto>> validate(@RequestHeader String token) {
        ResJwtValidationDto res = validateTokenService.validateToken(token);
        return ResponseEntity.ok(new ResMessageDto<>(200, "Success Decode Jwt", res));
    }
}
