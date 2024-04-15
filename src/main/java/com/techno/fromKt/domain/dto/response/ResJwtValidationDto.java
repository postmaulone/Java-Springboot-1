package com.techno.fromKt.domain.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResJwtValidationDto {
    private String email;
    private int id;
    private String username;
    private String type;
}
