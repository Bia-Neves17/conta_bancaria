package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.application.dto.ClienteAtualizadoDTO;
import com.senai.conta_bancaria.application.dto.ClienteRegistroDTO;
import com.senai.conta_bancaria.application.dto.ClienteResponseDTO;
import com.senai.conta_bancaria.domain.entity.Cliente;
import com.senai.conta_bancaria.domain.exception.ContaMesmoTipoException;
import com.senai.conta_bancaria.domain.exception.EntidadeNaoEncontradaException;
import com.senai.conta_bancaria.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new ContaMesmoTipoException();
        cliente.getContas().add(novaConta);
        return ClienteResponseDTO.fromEntity(clienteRepository.save(cliente));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarClientesAtivos() {
        return clienteRepository.findAllByAtivoTrue()
                .stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarClienteCpfAtivo(String cpf) {
        var cliente = buscarClientePorCpfAtivo(cpf);
        return ClienteResponseDTO.fromEntity(cliente);
    }

    public ClienteResponseDTO atualizarCliente(String cpf, ClienteAtualizadoDTO dto) {
        var cliente = buscarClientePorCpfAtivo(cpf);
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        return ClienteResponseDTO.fromEntity(clienteRepository.save(cliente));
    }

    public void deletarCliente(String cpf) {
        var cliente = buscarClientePorCpfAtivo(cpf);
        cliente.setAtivo(false);
        cliente.getContas().forEach(
                conta -> conta.setAtiva(false)
        );
        clienteRepository.save(cliente);
    }
    private Cliente buscarClientePorCpfAtivo(String cpf) {
        var cliente = clienteRepository.findByCpfAndAtivoTrue(cpf).orElseThrow(()-> new EntidadeNaoEncontradaException("cliente"));
        return cliente;
    }
}
