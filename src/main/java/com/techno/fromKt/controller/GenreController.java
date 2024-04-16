package com.techno.fromKt.controller;

import com.techno.fromKt.domain.dto.request.ReqGenreDto;
import com.techno.fromKt.domain.dto.request.ReqUserDto;
import com.techno.fromKt.domain.dto.response.ResGenreDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.domain.dto.response.ResUserDto;
import com.techno.fromKt.service.GenreService;
import com.techno.fromKt.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/genre")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping()
    public ResponseEntity<ResMessageDto<String>> createNewGenre(
            @Valid @RequestBody ReqGenreDto req
    ) {
        return ResponseEntity.ok(genreService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResMessageDto<String>> updateGenre(
            @PathVariable int id,
            @Valid @RequestBody ReqGenreDto req
    ) {
        return ResponseEntity.ok(genreService.update(id, req));
    }

    @GetMapping()
    public ResponseEntity<ResMessageDto<List<ResGenreDto>>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResMessageDto<ResGenreDto>> getGenreById(
            @PathVariable int id
    ) {
        return ResponseEntity.ok(genreService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResMessageDto<String>> deleteGenreById(
            @PathVariable int id
    ) {
        ResMessageDto<String> deleteResult = genreService.delete(id);
        return ResponseEntity.ok(deleteResult);
    }
}
