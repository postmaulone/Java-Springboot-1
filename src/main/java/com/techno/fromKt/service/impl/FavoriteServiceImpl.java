package com.techno.fromKt.service.impl;

import com.techno.fromKt.domain.dto.request.ReqFavoriteDto;
import com.techno.fromKt.domain.dto.response.ResFavoriteDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.domain.entity.FavoriteEntity;
import com.techno.fromKt.domain.entity.GameEntity;
import com.techno.fromKt.domain.entity.TypeEntity;
import com.techno.fromKt.exception.SomethingWrongException;
import com.techno.fromKt.repository.FavoriteRepository;
import com.techno.fromKt.repository.TypeRepository;
import com.techno.fromKt.service.FavoriteService;
import com.techno.fromKt.util.GeneralFunction;
import com.techno.fromKt.util.JwtGenerator;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class FavoriteServiceImpl implements FavoriteService  {

    private final GeneralFunction function;

    FavoriteRepository repo;
    TypeRepository typeRepo;

    @Override
    public ResMessageDto<String> create(ReqFavoriteDto req, String token) {
        Claims claim = new JwtGenerator().decodeJwt(token);

        String username = (String) claim.get("username");

        Optional<FavoriteEntity> data = repo.findByUsername(username);
        FavoriteEntity input = null;
        if(!data.isPresent()){

            input = FavoriteEntity.builder()
                    .username(username)
                    .added(LocalDate.now(ZoneId.of("Asia/Jakarta")))
                    .updated(LocalDate.now(ZoneId.of("Asia/Jakarta")))
                    .game(function.gameSearch(req.getGame(), claim.get("type").toString()))
                    .build();
        }else{
            input = data.get();
            Set<GameEntity> game = input.getGame();
            game.addAll(function.gameSearch(req.getGame(),claim.get("type").toString()));

            input.setUpdated(LocalDate.now(ZoneId.of("Asia/Jakarta")));
            input.setGame(game);
        }

        try{
            Integer id = 0;
            assert input != null;
            FavoriteEntity data2 = repo.save(input);
            id = data2.getId();

            String message = id == 0 ? "Favorite Added Failed" : "Favorite Added Success";
            return new ResMessageDto<>(
                    200, message,null
            );
        }catch (Exception e){
            e.printStackTrace();
            throw new SomethingWrongException("Server Error");
        }
    }

    @Override
    public ResMessageDto<String> update(int id, ReqFavoriteDto req) {
        return null;
    }

    @Override
    public ResMessageDto<List<ResFavoriteDto>> getAll() {
        List<FavoriteEntity> listData = repo.findAll();

        List<ResFavoriteDto> response = new ArrayList<>();
        for(FavoriteEntity data : listData){
            ResFavoriteDto dto = ResFavoriteDto.builder()
                    .id(data.getId())
                    .game(function.encodeGame(data.getGame()))
                    .username(data.getUsername())
                    .added(data.getAdded())
                    .updated(data.getUpdated())
                    .build();
            response.add(dto);
        }

        return new ResMessageDto<>(
                200,
                "Success Get Game By Id",
                response
        );
    }

    @Override
    public ResMessageDto<ResFavoriteDto> getById(int id, String token) {
        Claims claim = new JwtGenerator().decodeJwt(token);

        Optional<FavoriteEntity> data = Optional.of(repo.findByIdAndUsername(id, claim.get("username").toString()).get());
        if(!data.isPresent()){
            throw new SomethingWrongException("Data Not Found");
        }

        ResFavoriteDto dto = ResFavoriteDto.builder()
                .id(data.get().getId())
                .game(function.encodeGame(data.get().getGame()))
                .username(data.get().getUsername())
                .added(data.get().getAdded())
                .updated(data.get().getUpdated())
                .build();
            return new ResMessageDto<>(
                    200,
                    "Success Get Game By Id",
                    dto
            );
    }

    @Override
    public ResMessageDto<String> delete(int id) {
        Optional<FavoriteEntity> data = Optional.of(repo.findById(id).get());
        if(!data.isPresent()){
            throw new SomethingWrongException("Data Not Found");
        }

        Set<GameEntity> game = data.get().getGame();
        game.clear();
        data.get().setGame(game);

        repo.save(data.get());
        return new ResMessageDto<>(
                200,
                "Favorite Deleted Success",
                null
        );
    }
}
