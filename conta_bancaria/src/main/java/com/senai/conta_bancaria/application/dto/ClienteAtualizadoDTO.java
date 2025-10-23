package com.senai.conta_bancaria.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ClienteAtualizadoDTO(
        @NotBlank(message = "O nome do titular da conta é obrigatório")
        @Schema(description = "Nome para Atualizar", example = "Jamily")
        String nome,
        @NotBlank(message = "O CPF é obrigatório")
        @Schema(description = "cpf para atualizar", example = "11111111111")
        String cpf
) {

}
