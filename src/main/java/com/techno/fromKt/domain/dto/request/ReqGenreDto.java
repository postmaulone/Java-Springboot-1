package com.techno.fromKt.domain.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class ReqGenreDto {
    @NotBlank
    private String name;

}

