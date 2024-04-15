package com.techno.fromKt.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReqLoginDto {
    @NotBlank
    @Email(message = "email must in the right email format")
    private String email;

    @NotBlank
    @Size(min = 8, message = "password must be >8 char")
    private String password;
}
