package com.senai.conta_bancaria.interface_ui.controller;

import com.senai.conta_bancaria.application.dto.ContaAtualizadaDTO;
import com.senai.conta_bancaria.application.dto.ContaResumoDTO;
import com.senai.conta_bancaria.application.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conta")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService contaService;

    //get de todos e por número

    @GetMapping
    public ResponseEntity<List<ContaResumoDTO>>  listarContasAtivas(){
        return ResponseEntity.ok(contaService.listarContasAtivas());
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<ContaResumoDTO> buscarContaNumeroAtiva(@PathVariable String numero){
        return ResponseEntity.ok(contaService.buscarContaNumeroAtiva(numero));
    }

    @PutMapping("/{numero}")
    public ResponseEntity<ContaResumoDTO> atualizarConta(@PathVariable String numero, @RequestBody ContaAtualizadaDTO dto){
        return ResponseEntity.ok(contaService.atualizarConta(numero, dto));
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> deletarConta(@PathVariable String numero){
        contaService.deletarConta(numero);
        return ResponseEntity.noContent().build();
    }
}
