package com.techno.fromKt.domain.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReqUserDto {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "name cannot contain numeric")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[^\\s]*$", message = "username cannot contain spaces")
    private String username;

    @NotBlank
    @Email(message = "email must in the right email format")
    private String email;

    @NotBlank
    @Size(min = 8, message = "password must be >8 char")
    private String password;

    @NotBlank
    private String type;
}

