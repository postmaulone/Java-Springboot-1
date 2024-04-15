package com.techno.fromKt.repository;

import com.techno.fromKt.domain.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity, Integer> {
}
