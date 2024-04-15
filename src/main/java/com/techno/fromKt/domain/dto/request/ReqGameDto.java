package com.techno.fromKt.domain.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.util.Set;

@Getter
public class ReqGameDto {
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String type;
    @NotNull
    private Set<Integer> genre;
}
