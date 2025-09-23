package com.senai.conta_bancaria.interface_ui.controller;

import com.senai.conta_bancaria.application.dto.ClienteRegistroDTO;
import com.senai.conta_bancaria.application.dto.ClienteResponseDTO;
import com.senai.conta_bancaria.application.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;


    @PostMapping
    public ResponseEntity<ClienteResponseDTO> registarCliente(@RequestBody ClienteRegistroDTO dto){
        ClienteResponseDTO novoCliente = clienteService.regitrarCliente(dto);
        return ResponseEntity.created(URI.create("/api/novoCliente/cpf/"+ novoCliente.cpf())).body(novoCliente);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesAtivos(){
        return ResponseEntity.ok(clienteService.listarClientesAtivos());
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesCpfAtivos(){
        return ResponseEntity.ok(clienteService.listarClientesCpfAtivos());
    }
}
