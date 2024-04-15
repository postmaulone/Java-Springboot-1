package com.techno.fromKt.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor
@Builder
@Table(name = "mst_favorite")
public class FavoriteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_favorite")
    private Integer id;
    @Column(name = "user_added")
    private String username;
    @Column(name = "dt_added")
    private String add;
    @Column(name = "dt_updated")
    private String updated;
}
