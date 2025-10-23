package com.senai.conta_bancaria.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferenciaDTO(
        @NotBlank(message = "O número da conta destino é obrigatório")
        @Schema(description = "conta destino para transferência", example = "123-4")
        String contaDestino,
        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor não pode ser negativo")
        @Schema(description = "valor para transferência", example = "120")
        BigDecimal valor
) {
}
