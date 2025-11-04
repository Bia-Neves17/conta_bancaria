package com.senai.conta_bancaria.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record PagamentoDTO(
        @NotBlank(message = "O campo numero da conta não pode estar em branco")
        @Schema(description = "numero da conta para o pagamento ", example = "123-4")
        String contaNumero,

        @NotBlank(message = "O campo valor não")
) {
}
