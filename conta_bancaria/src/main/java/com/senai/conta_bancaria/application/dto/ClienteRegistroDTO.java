package com.senai.conta_bancaria.application.dto;

import com.senai.conta_bancaria.domain.entity.Cliente;
import com.senai.conta_bancaria.domain.entity.Conta;
import com.senai.conta_bancaria.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

public record ClienteRegistroDTO(
        @NotBlank(message = "O nome do titular da conta é obrigatório")
        @Schema(description = "Nome para registro", example = "Amanda")
        String nome,
        @NotBlank(message = "O CPF é obrigatório")
        @Schema(description = "cpf para registro", example = "111111111")
        String cpf,
        @Valid
        @NotNull
        ContaResumoDTO contaDTO,
        @Schema(description = "e-mail para registro", example = "exemplo@gmail.com")
        String email,
        @Schema(description = "senha para registro", example = "123456")
        String senha,
        Role role
){

    public Cliente toEntity(){
        return Cliente.builder()
                .ativo(true)
                .nome(this.nome)
                .cpf(this.cpf)
                .contas(new ArrayList<Conta>())
                .email(this.email)
                .senha(this.senha)
                .role(this.role != null ? this.role : Role.CLIENTE)
                .build();
    }
}
