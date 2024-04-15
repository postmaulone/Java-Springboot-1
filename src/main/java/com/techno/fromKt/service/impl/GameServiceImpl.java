package com.techno.fromKt.service.impl;

import com.techno.fromKt.domain.dto.request.ReqGameDto;
import com.techno.fromKt.domain.dto.response.ResGameDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.domain.entity.GameEntity;
import com.techno.fromKt.domain.entity.GenreEntity;
import com.techno.fromKt.domain.entity.TypeEntity;
import com.techno.fromKt.repository.GameRepository;
import com.techno.fromKt.repository.GenreRepository;
import com.techno.fromKt.repository.TypeRepository;
import com.techno.fromKt.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {
    GameRepository repo;
    TypeRepository typeRepo;
    GenreRepository genreRepo;

    private TypeEntity typeAssign(String type) {
        TypeEntity typeFound = typeRepo.findByName(nullType(type));
        if (typeFound == null) {
            throw new RuntimeException("Type not found"); // Replace RuntimeException with DataNotFoundException if it's defined in your project
        }
        return typeFound; // Assuming 'getTypeId' returns a String. Make sure this is non-null or handled if it can be.
    }

    private String nullType(String type) {
        if (type.isEmpty()) {
            return "Free";
        } else {
            return type;
        }
    }

    private Set<GenreEntity> genreSearch(Set<Integer> genre){
        Set<GenreEntity> set = new HashSet<>();
        for (Integer genreId : genre) {
            GenreEntity entity = (GenreEntity) genreRepo.findById(genreId)
                    .orElseThrow(() -> new IllegalArgumentException("Genre not found"));
            set.add(entity);
        }
        return set;
    }

    @Override
    public ResMessageDto<String> create(ReqGameDto req) {
        GameEntity input = GameEntity.builder()
                .type(typeAssign(req.getType()))
                .name(req.getName())
                .genres(genreSearch(req.getGenre()))
                .build();

        Integer id = 0;
        GameEntity data = repo.save(input);

        id = data.getId();
        String message = id == 0 ? "Genre Added Failed" : "Genre Added Success";
        return new ResMessageDto<>(
                200, message,null
        );
    }

    @Override
    public ResMessageDto<String> update(int id, ReqGameDto req) {
        return null;
    }

    @Override
    public ResMessageDto<List<ResGameDto>> getAll() {
        return null;
    }

    @Override
    public ResMessageDto<ResGameDto> getById(int id) {
        return null;
    }
}
