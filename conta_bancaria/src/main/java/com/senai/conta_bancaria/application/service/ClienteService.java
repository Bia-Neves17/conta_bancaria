package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.application.dto.ClienteRegistroDTO;
import com.senai.conta_bancaria.application.dto.ClienteResponseDTO;
import com.senai.conta_bancaria.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteResponseDTO regitrarCliente(ClienteRegistroDTO dto){

        var cliente = clienteRepository.findByCpfAndAtivoTrue(dto.cpf())
                .orElseGet(() -> clienteRepository.save(dto.toEntity())
                );

        var contas = cliente.getContas();

        var novaConta = dto.contaDTO().toEntity(cliente);

        boolean jaTemTipo = contas
                .stream()
                .anyMatch(
                        conta -> conta.getClass().equals(novaConta.getClass()) && conta.isAtiva()
                );
        if (jaTemTipo)
            throw new RuntimeException("Cliente já possui uma conta dessa tipo.");
        cliente.getContas().add(novaConta);
        return ClienteResponseDTO.fromEntity(clienteRepository.save(cliente));
    }

    public List<ClienteResponseDTO> listarClientesAtivos() {
        return clienteRepository.findAllByAtivoTrue().stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList();
    }
}
