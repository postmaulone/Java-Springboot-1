package com.techno.fromKt.repository;

import com.techno.fromKt.domain.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {
    Optional<FavoriteEntity> findByUsername(String username);
    Optional<FavoriteEntity> findByIdAndUsername(Integer id, String username);
}
