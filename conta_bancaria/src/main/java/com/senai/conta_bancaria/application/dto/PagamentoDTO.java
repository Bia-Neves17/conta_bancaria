package com.senai.conta_bancaria.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.Set;

public record PagamentoDTO(
        @NotBlank(message = "O campo numero da conta não pode estar em branco")
        @Schema(description = "numero da conta para o pagamento", example = "123-4")
        String contaNumero,

        @NotBlank(message = "O campo valor não")
        @Schema(description = "Valor pago no boleto", example = "300")
        BigDecimal valor,

        @NotBlank(message = "O campo data não pode estar em branco")
        @Schema(description = "Data do pagamento", example = "30/04/2025" )
        String dataPagamento,

        @NotBlank(message = "O campo id não pode estar em branco")
        @Schema(description = "IDs das taxas associadas ao pagamento", example = "12345678910")
        Set<String> idsTaxas
) {
}
