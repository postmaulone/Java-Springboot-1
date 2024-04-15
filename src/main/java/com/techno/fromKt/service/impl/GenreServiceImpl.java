package com.techno.fromKt.service.impl;

import com.techno.fromKt.domain.dto.request.ReqGenreDto;
import com.techno.fromKt.domain.dto.response.ResGenreDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.domain.entity.GenreEntity;
import com.techno.fromKt.repository.GenreRepository;
import com.techno.fromKt.repository.TypeRepository;
import com.techno.fromKt.repository.UserRepository;
import com.techno.fromKt.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    UserRepository userRepository;
    TypeRepository typeRepository;
    GenreRepository genreRepository;

    @Override
    public ResMessageDto<String> create(ReqGenreDto req) {
//        checkDupeUsr(req.getUsername());
        GenreEntity usedGenre = genreRepository.findByName(req.getName());
        if (usedGenre != null) {
            throw new IllegalArgumentException("Genre name already");
        }
//        checkDupeEml(req.getEmail());
//        Argon2 password = Argon2Factory.create();
        GenreEntity input = GenreEntity.builder()
                .name(req.getName())
                .build();
        GenreEntity data = genreRepository.save(input);
        String message = data.getId() > 0 ? "Genre added!" : "";
        return new ResMessageDto<>(
                200,
                message,
                null
        );
    }

    @Override
    public ResMessageDto<String> update(int id, ReqGenreDto req) {
        return null;
    }

    @Override
    public ResMessageDto<List<ResGenreDto>> getAll() {
        return null;
    }

    @Override
    public ResMessageDto<ResGenreDto> getById(int id) {
        return null;
    }
}
