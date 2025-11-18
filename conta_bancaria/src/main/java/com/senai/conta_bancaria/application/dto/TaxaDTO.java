package com.senai.conta_bancaria.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.UUID;

public record TaxaDTO(

        String id,

        @NotBlank(message = "Descrição é obrigatória")
        @Schema(description = "Descrição da taxa")
        String descricao,

        @Schema(description = "porcentagem para aplicação da taxa")
        BigDecimal percentual,

        @Schema(description = "valor fixo para aplicação da taxa")
        BigDecimal valorFixo
) {

    @AssertTrue(message = "Informe apenas percetual ou valorFixo")
    private boolean tipoTaxaValido(){
    return (percentual != null && valorFixo == null) ||
            (percentual == null && valorFixo != null);
    }
}
