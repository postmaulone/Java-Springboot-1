package com.techno.fromKt.repository;

import com.techno.fromKt.domain.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {
}
