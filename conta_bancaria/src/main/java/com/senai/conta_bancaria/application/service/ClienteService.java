package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.application.dto.ClienteRegistroDTO;
import com.senai.conta_bancaria.application.dto.ClienteResponseDTO;
import com.senai.conta_bancaria.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public ClienteResponseDTO regitrarCliente(ClienteRegistroDTO){
        return
    }

    @Transactional(readOnly = true)
    public List<ClienteRegistroDTO> listarClientes(){
        return clienteRepository.findAll()
                .stream()
                .map(ClienteRegistroDTO::fromEntity)
                .toList();
    }

    //public ClienteDTO
}
