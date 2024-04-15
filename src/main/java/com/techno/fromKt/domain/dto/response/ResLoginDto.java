package com.techno.fromKt.domain.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResLoginDto {
    private String email;
    private String token;
}
