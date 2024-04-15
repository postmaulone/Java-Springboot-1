package com.techno.fromKt.repository;

import com.techno.fromKt.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
    int countByUsername(String username);
    int countByEmail(String email);
}
