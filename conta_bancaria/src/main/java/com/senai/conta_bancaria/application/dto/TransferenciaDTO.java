package com.senai.conta_bancaria.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferenciaDTO(
        @NotBlank(message = "O número da conta destino é obrigatório")
        String contaDestino,
        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor não pode ser negativo")
        BigDecimal valor
) {
}
