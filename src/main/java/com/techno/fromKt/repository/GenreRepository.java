package com.techno.fromKt.repository;

import com.techno.fromKt.domain.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {
}
