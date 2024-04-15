package com.techno.fromKt.controller;

import com.techno.fromKt.domain.dto.request.ReqGameDto;
import com.techno.fromKt.domain.dto.request.ReqUserDto;
import com.techno.fromKt.domain.dto.response.ResGameDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.domain.dto.response.ResUserDto;
import com.techno.fromKt.service.GameService;
import com.techno.fromKt.service.UserService;
import com.techno.fromKt.util.JwtGenerator;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/detail")
    public ResponseEntity<ResMessageDto<ResGameDto>> findById(
        @RequestParam("id") int id,
        HttpServletRequest request
    ){
        return ResponseEntity.ok(service.getById(id, request.getHeader("token")));
    }

    @GetMapping
    public ResponseEntity<ResMessageDto<List<ResGameDto>>> findAll(
            HttpServletRequest request
    ){
        return ResponseEntity.ok(service.getAll(request.getHeader("token")));
    }

    @DeleteMapping
    public ResponseEntity<ResMessageDto<String>> delete(
            @RequestParam("id") int id
    ){
        return ResponseEntity.ok(service.delete(id));
    }

    @PutMapping
    public ResponseEntity<ResMessageDto<String>> update(
            @RequestParam("id") int id,
            @Valid @RequestBody ReqGameDto req
    ){
        return ResponseEntity.ok(service.update(id, req));
    }
}
