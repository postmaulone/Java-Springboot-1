package com.techno.fromKt.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@Builder
@Table(name = "mst_type_user")
public class TypeEntity {
    @Id
    @Column(name = "id_type")
    private String id;

    @Column(name = "name_type")
    private String name;

    @OneToMany(mappedBy = "type")
    private Set<GameEntity> games;
}
