package com.senai.conta_bancaria.application.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferenciaDTO(
        @NotNull(message = "O valor é obrigatório")
        String contaDestino,
        BigDecimal valor
) {
}
