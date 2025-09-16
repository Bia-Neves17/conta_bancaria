package com.senai.conta_bancaria.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cliente",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "cpf")
        }
)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 120)
    @NotBlank(message = "O campo nome não pode estar vazio")
    private String nome;

    @Column(nullable = false,length = 11)
    @NotNull(message = "O campo cpf não pode estar vazio")
    private Long cpf;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @NotBlank(message = "O campo contas não pode estar vazio")
    @JoinColumn(name = "contas_numero")
    private List<Conta> contas;

    @Column(nullable = false)
    private Boolean ativo;
}
