package com.senai.conta_bancaria.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Taxa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String id;

    private String descricao;

    private BigDecimal percentual;

    private BigDecimal valorFixo;

    @ManyToMany(mappedBy = "taxas")
    private Set<Pagamento> pagamentos = new HashSet<>();
}
