package com.senai.conta_bancaria.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class AuthDTO {
    public record LoginRequest(
            @Schema(description = "E-mail de login", example = "exemplo@gmail.com")
            String email,
            @Schema(description = "Senha para login", example = "123456")
            String senha
    ) {}
    public record TokenResponse(
            String token
    ) {}
}
