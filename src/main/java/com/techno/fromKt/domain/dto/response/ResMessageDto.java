package com.techno.fromKt.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResMessageDto<T> {
    private int status = 200;
    private String message = "Success";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

}
