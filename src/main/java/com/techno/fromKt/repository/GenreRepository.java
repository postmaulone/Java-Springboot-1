package com.techno.fromKt.repository;

import com.techno.fromKt.domain.entity.GenreEntity;
import com.techno.fromKt.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {

    GenreEntity findByName(String name);

}
