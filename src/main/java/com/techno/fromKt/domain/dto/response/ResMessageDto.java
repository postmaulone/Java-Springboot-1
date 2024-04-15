package com.techno.fromKt.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResMessageDto<T> {
    private int status = 200;
    private String message = "Success";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
