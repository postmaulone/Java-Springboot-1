package com.techno.fromKt.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResUserDto {
    private int id;
    private String name;
    private String username;
    private String email;
    private String type;
}


