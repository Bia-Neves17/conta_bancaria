package com.senai.conta_bancaria.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_numero", nullable = false)
    private Conta conta;

    private String boleto;

    private BigDecimal valorPago;

    private String dataPagamento;

    private
}
