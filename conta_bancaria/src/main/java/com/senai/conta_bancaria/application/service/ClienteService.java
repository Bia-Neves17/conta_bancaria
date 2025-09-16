package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.application.dto.ClienteDTO;
import com.senai.conta_bancaria.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public List<ClienteDTO> listarClientes(){
        return clienteRepository.findAll()
                .stream()
                .map(ClienteDTO::fromEntity)
                .toList();
    }

    //public ClienteDTO
}
