package com.techno.fromKt.domain.dto.response;

import com.techno.fromKt.domain.entity.GenreEntity;
import com.techno.fromKt.domain.entity.TypeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResGameDto {
    private Integer id;
    private String name;
    private String type;
    private Set<String> genres;
}
