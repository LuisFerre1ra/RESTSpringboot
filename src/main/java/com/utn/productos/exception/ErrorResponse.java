package com.utn.productos.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String path;

    public ErrorResponse(int status, String error, String path) {
        this.status = status;
        this.error = error;
        this.path = path;
    }
}