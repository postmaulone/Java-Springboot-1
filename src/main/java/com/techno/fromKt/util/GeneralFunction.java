package com.techno.fromKt.util;

import com.techno.fromKt.domain.entity.GameEntity;
import com.techno.fromKt.domain.entity.GenreEntity;
import com.techno.fromKt.domain.entity.TypeEntity;
import com.techno.fromKt.exception.SomethingWrongException;
import com.techno.fromKt.repository.GameRepository;
import com.techno.fromKt.repository.GenreRepository;
import com.techno.fromKt.repository.TypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Configuration
public class GeneralFunction{
    TypeRepository typeRepo;
    GenreRepository genreRepo;
    GameRepository gameRepo;
    public TypeEntity typeAssign(String type) {
        TypeEntity typeFound = typeRepo.findByName(nullType(type));
        if (typeFound == null) {
            throw new RuntimeException("Type not found"); // Replace RuntimeException with DataNotFoundException if it's defined in your project
        }
        return typeFound; // Assuming 'getTypeId' returns a String. Make sure this is non-null or handled if it can be.
    }

    public String nullType(String type) {
        if (type.isEmpty()) {
            return "Free";
        } else {
            return type;
        }
    }

    public Set<GenreEntity> genreSearch(Set<Integer> genre){
        Set<GenreEntity> set = new HashSet<>();
        for (Integer genreId : genre) {
            GenreEntity entity = genreRepo.findById(genreId)
                    .orElseThrow(() -> new IllegalArgumentException("Genre not found"));
            set.add(entity);
        }
        return set;
    }

    public Set<String> encodeGenre(Set<GenreEntity> genre){
        Set<String> set = new HashSet<>();
        for (GenreEntity genreId : genre) {
            GenreEntity entity = genreRepo.findById(genreId.getId())
                    .orElseThrow(() -> new SomethingWrongException("Genre not found"));
            set.add(entity.getName());
        }
        return set;
    }

    public Set<GameEntity> gameSearch(Set<Integer> game, String type){
        Set<GameEntity> set = new HashSet<>();
        for (Integer gameId : game) {
            GameEntity entity = gameRepo.findById(gameId)
                    .orElseThrow(() -> new SomethingWrongException("Genre not found"));
            if(type.equals("T002")) {
                set.add(entity);
            }else if(entity.getType().getId().equals(type)){
                set.add(entity);
            }else{
                throw new SomethingWrongException("No Access To This Game");
            }
        }
        return set;
    }

    public Set<String> encodeGame(Set<GameEntity> game){
        Set<String> set = new HashSet<>();
        for (GameEntity gameId : game) {
            GameEntity entity = gameRepo.findById(gameId.getId())
                    .orElseThrow(() -> new SomethingWrongException("Genre not found"));
            set.add(entity.getName());
        }
        return set;
    }
}
