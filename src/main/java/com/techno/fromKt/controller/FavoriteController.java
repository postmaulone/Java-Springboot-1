package com.techno.fromKt.controller;

import com.techno.fromKt.domain.dto.request.ReqFavoriteDto;
import com.techno.fromKt.domain.dto.response.ResFavoriteDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.service.FavoriteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/favorite")
@AllArgsConstructor
public class FavoriteController {
    private final FavoriteService service;
    @PostMapping()
    public ResponseEntity<ResMessageDto<String>> create(
            @Valid @RequestBody ReqFavoriteDto req,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(service.create(req, request.getHeader("token")));
    }

    @GetMapping("/detail")
    public ResponseEntity<ResMessageDto<ResFavoriteDto>> findById(
            @RequestParam("id") int id,
            HttpServletRequest request
    ){
        return ResponseEntity.ok(service.getById(id, request.getHeader("token")));
    }

    @GetMapping
    public ResponseEntity<ResMessageDto<List<ResFavoriteDto>>> findAll(
            HttpServletRequest request
    ){
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping
    public ResponseEntity<ResMessageDto<String>> delete(
            @RequestParam("id") int id
    ){
        return ResponseEntity.ok(service.delete(id));
    }

//    @PutMapping
//    public ResponseEntity<ResMessageDto<String>> update(
//            @RequestParam("id") int id,
//            @Valid @RequestBody ReqFavoriteDto req
//    ){
//        return ResponseEntity.ok(service.update(id, req));
//    }
}