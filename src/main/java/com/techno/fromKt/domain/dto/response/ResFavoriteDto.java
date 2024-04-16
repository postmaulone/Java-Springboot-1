package com.techno.fromKt.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
public class ResFavoriteDto {
    private Integer id;
    private Set<String> game;
    private String username;
    private LocalDate added;
    private LocalDate updated;
}
