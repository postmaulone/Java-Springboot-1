package com.techno.fromKt.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Set;

@Getter
public class ReqFavoriteDto {
    @NotNull
    private Set<Integer> game;
}
