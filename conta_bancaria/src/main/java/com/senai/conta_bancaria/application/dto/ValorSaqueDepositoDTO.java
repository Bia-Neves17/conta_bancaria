package com.senai.conta_bancaria.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ValorSaqueDepositoDTO(
        @NotNull(message = "O valor é obrigatório")
        @Schema(description = "valor para depósito", example = "100.50")
        BigDecimal valor
) {
}
