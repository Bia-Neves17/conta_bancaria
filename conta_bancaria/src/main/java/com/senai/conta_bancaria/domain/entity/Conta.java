package com.senai.conta_bancaria.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_conta", discriminatorType = DiscriminatorType.STRING, length = 20)
@Data
@MappedSuperclass
public abstract class Conta {

    private String id;

    @NotNull(message = "O número da conta não pode estar vazio")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String numero;

    @PositiveOrZero(message = "O saldo não pode ser negativo")
    private BigDecimal saldo;
}
