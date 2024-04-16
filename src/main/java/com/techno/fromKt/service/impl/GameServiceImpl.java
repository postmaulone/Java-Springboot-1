package com.techno.fromKt.service.impl;

import com.techno.fromKt.domain.dto.request.ReqGameDto;
import com.techno.fromKt.domain.dto.response.ResGameDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.domain.entity.GameEntity;
import com.techno.fromKt.domain.entity.TypeEntity;
import com.techno.fromKt.exception.SomethingWrongException;
import com.techno.fromKt.repository.GameRepository;
import com.techno.fromKt.repository.TypeRepository;
import com.techno.fromKt.service.GameService;
import com.techno.fromKt.util.GeneralFunction;
import com.techno.fromKt.util.JwtGenerator;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {
    private final GeneralFunction function;
    GameRepository repo;
    TypeRepository typeRepo;

    @Override
    public ResMessageDto<String> create(ReqGameDto req) {
        GameEntity input = GameEntity.builder()
                .type(function.typeAssign(req.getType()))
                .name(req.getName())
                .genres(function.genreSearch(req.getGenre()))
                .build();

        try{
            Integer id = 0;
            GameEntity data = repo.save(input);
            id = data.getId();

            String message = id == 0 ? "Genre Added Failed" : "Genre Added Success";
            return new ResMessageDto<>(
                    200, message,null
            );
        }catch (Exception e){
            e.printStackTrace();
            throw new SomethingWrongException("Server Error");
        }
    }

    @Override
    public ResMessageDto<String> update(int id, ReqGameDto req) {
        Optional<GameEntity> data = Optional.of(repo.findById(id).get());
        if(!data.isPresent()){
            throw new SomethingWrongException("Data Not Found");
        }

        data.get().setType(function.typeAssign(req.getType()));
        data.get().setName(req.getName());
        data.get().setGenres(function.genreSearch(req.getGenre()));

        try{
            GameEntity update = repo.save(data.get());

            String message = id != update.getId()  ? "Genre Updated Failed" : "Genre Updated Success";
            return new ResMessageDto<>(
                    200, message,null
            );
        }catch (Exception e){
            e.printStackTrace();
            throw new SomethingWrongException("Server Error");
        }
    }

    @Override
    public ResMessageDto<List<ResGameDto>> getAll(String token) {

        Claims claim = new JwtGenerator().decodeJwt(token);
        TypeEntity typeEntity = typeRepo.findById(claim.get("type").toString()).get();
        if(typeEntity == null){
            throw new SomethingWrongException("Data Not Found");
        }

        List<GameEntity> listData;

        if(typeEntity.getName().equals("Free")){
            listData = repo.findByType(typeEntity);
        }else{
            listData = repo.findAll();
        }

        List<ResGameDto> listDto = new ArrayList<>();
        for (GameEntity data : listData){
            ResGameDto dto = ResGameDto.builder()
                    .id(data.getId())
                    .name(data.getName())
                    .type(data.getType().getName())
                    .genres(function.encodeGenre(data.getGenres()))
                    .build();
            listDto.add(dto);
        }
        return new ResMessageDto<>(
                200,
                "Success Get All Game",
                listDto
        );
    }

    @Override
    public ResMessageDto<ResGameDto> getById(int id, String token) {
        Claims claim = new JwtGenerator().decodeJwt(token);
        TypeEntity typeEntity = typeRepo.findById(claim.get("type").toString()).get();

        Optional<GameEntity> data = Optional.of(repo.findById(id).get());
        if(typeEntity == null || !data.isPresent()){
            throw new SomethingWrongException("Data Not Found");
        }

        ResGameDto dto = ResGameDto.builder()
                .id(data.get().getId())
                .name(data.get().getName())
                .type(data.get().getType().getName())
                .genres(function.encodeGenre(data.get().getGenres()))
                .build();
        if(typeEntity.getName().equals("Free") && data.get().getType().getName().equals("Free")){
            return new ResMessageDto<>(
                    200,
                    "Success Get Game By Id",
                    dto
            );
        }else if(typeEntity.getName().equals("Premium")){
            return new ResMessageDto<>(
                    200,
                    "Success Get Game By Id",
                    dto
            );
        }else{
            return new ResMessageDto<>(
                    200,
                    "No Access To This Game",
                    null
            );
        }
    }

    @Override
    public ResMessageDto<String> delete(int id) {

        Optional<GameEntity> data = Optional.of(repo.findById(id).get());
        if(!data.isPresent()){
            throw new SomethingWrongException("Data Not Found");
        }

        repo.delete(data.get());
        return new ResMessageDto<>(
                200,
                "Genre Deleted Success",
                null
        );
    }
}
