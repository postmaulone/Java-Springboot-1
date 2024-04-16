package com.techno.fromKt.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "mst_favorite")
public class FavoriteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_favorite")
    private Integer id;
    @ManyToMany
    @JoinTable(
            name = "favorite_game",
            joinColumns = @JoinColumn(name = "favorite_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private Set<GameEntity> game;
    @Column(name = "user_added")
    private String username;
    @Column(name = "dt_added")
    private LocalDate added;
    @Column(name = "dt_updated")
    private LocalDate updated;
}
