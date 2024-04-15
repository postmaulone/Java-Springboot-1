package com.techno.fromKt.repository;

import com.techno.fromKt.domain.entity.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<TypeEntity, Integer> {
    TypeEntity findByDescription(String description);
}
