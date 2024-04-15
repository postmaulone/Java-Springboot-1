package com.techno.fromKt.controller;

import com.techno.fromKt.domain.dto.request.ReqGameDto;
import com.techno.fromKt.domain.dto.request.ReqUserDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.domain.dto.response.ResUserDto;
import com.techno.fromKt.service.GameService;
import com.techno.fromKt.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/game")
@AllArgsConstructor
public class GameController {
    private final GameService service;
    @PostMapping()
    public ResponseEntity<ResMessageDto<String>> createGame(
            @Valid @RequestBody ReqGameDto req
    ) {
        return ResponseEntity.ok(service.create(req));
    }
}
