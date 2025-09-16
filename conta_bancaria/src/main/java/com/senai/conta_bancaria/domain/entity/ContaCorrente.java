package com.senai.conta_bancaria.domain.entity;

import jakarta.persistence.Entity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class ContaCorrente extends Conta{

    private BigDecimal limite;

    private BigDecimal taxa;
}
