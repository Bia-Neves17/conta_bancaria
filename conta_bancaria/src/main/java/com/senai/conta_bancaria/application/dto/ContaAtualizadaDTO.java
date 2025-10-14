package com.senai.conta_bancaria.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ContaAtualizadaDTO(
        @NotNull(message = "O saldo da conta é obrigatório")
        @Positive(message = "O saldo não pode ser negativo")
        BigDecimal saldo,

        BigDecimal limite,

        BigDecimal rendimento,

        BigDecimal taxa
) {
}
