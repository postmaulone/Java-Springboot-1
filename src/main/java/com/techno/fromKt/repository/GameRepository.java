package com.techno.fromKt.repository;

import com.techno.fromKt.domain.entity.GameEntity;
import com.techno.fromKt.domain.entity.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Integer> {
    List<GameEntity> findByType(TypeEntity type);
}
