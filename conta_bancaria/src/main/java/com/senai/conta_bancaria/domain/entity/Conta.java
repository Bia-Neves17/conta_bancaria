package com.senai.conta_bancaria.domain.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class Conta {

    @NotNull(message = "O número da conta não pode estar vazio")
    @GeneratedValue(strategy = GenerationType.UUID)
    private int numero;

    @PositiveOrZero(message = "O saldo não pode ser negativo")
    private double saldo;
}
