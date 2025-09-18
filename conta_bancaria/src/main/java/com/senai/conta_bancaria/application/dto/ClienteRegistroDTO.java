package com.senai.conta_bancaria.application.dto;

import com.senai.conta_bancaria.domain.entity.Cliente;

import java.util.ArrayList;
import java.util.List;

public record ClienteRegistroDTO(
        String nome,
        Long cpf,
        ContaResumoDTO conta
){
    public static ClienteRegistroDTO fromEntity(Cliente cliente){
        if (cliente == null) return null;
        return new ClienteRegistroDTO(
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getContas() != null ? cliente.getContas() : List.of()
        );
    }

    public Cliente toEntity(){
        Cliente cliente = new Cliente();
        cliente.setNome(this.nome);
        cliente.setCpf(this.cpf);
        cliente.setContas(this.conta != null ? new ArrayList<>(this.conta) : new ArrayList<>());
        return cliente;
    }
}
