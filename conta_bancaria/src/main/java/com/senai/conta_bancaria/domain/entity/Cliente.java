package com.senai.conta_bancaria.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Cliente {

    @NotBlank(message = "O campo nome não pode estar vazio")
    private String nome;

    @NotNull(message = "O campo cpf não pode estar vazio")
    private Long cpf;

    @ManyToOne
    @NotBlank(message = "O campo contas não pode estar vazio")
    @JoinColumn(name = "contas_numero")
    private List<Conta> contas;
}
