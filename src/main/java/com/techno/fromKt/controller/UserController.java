package com.techno.fromKt.controller;

import com.techno.fromKt.domain.dto.request.ReqUserDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.domain.dto.response.ResUserDto;
import com.techno.fromKt.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResMessageDto<ResUserDto>> createNewUser(
            @Valid @RequestBody ReqUserDto req
    ) {
        return ResponseEntity.ok(userService.create(req));
    }

    @PutMapping("/update")
    public ResponseEntity<ResMessageDto<ResUserDto>> updateUser(
            @Valid @RequestBody ReqUserDto req,
            @RequestParam int id
    ) {
        return ResponseEntity.ok(userService.update(id, req));
    }

    @GetMapping
    public ResponseEntity<ResMessageDto<List<ResUserDto>>> getAllUser() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/")
    public ResponseEntity<ResMessageDto<ResUserDto>> getUserById(
            @RequestHeader String token
    ){
        return ResponseEntity.ok(userService.getById(token));
    }
}
