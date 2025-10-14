package com.senai.conta_bancaria.application.dto;

import jakarta.validation.constraints.NotBlank;

public record ClienteAtualizadoDTO(
        @NotBlank(message = "O nome do titular da conta é obrigatório")
        String nome,
        @NotBlank(message = "O CPF é obrigatório")
        String cpf
) {

}
