package com.senai.conta_bancaria.interface_ui.controller;

import com.senai.conta_bancaria.application.dto.ContaAtualizadaDTO;
import com.senai.conta_bancaria.application.dto.ContaResumoDTO;
import com.senai.conta_bancaria.application.dto.TransferenciaDTO;
import com.senai.conta_bancaria.application.dto.ValorSaqueDepositoDTO;
import com.senai.conta_bancaria.application.service.ContaService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ContaResumoDTO> atualizarConta(@PathVariable String numero, @Valid @RequestBody ContaAtualizadaDTO dto){
        return ResponseEntity.ok(contaService.atualizarConta(numero, dto));
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> deletarConta(@PathVariable String numero){
        contaService.deletarConta(numero);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{numero}/sacar")
    public ResponseEntity<ContaResumoDTO> sacar(@PathVariable String numero, @Valid @RequestBody ValorSaqueDepositoDTO dto){
        return ResponseEntity.ok(contaService.sacar(numero, dto));
    }

    @PostMapping("/{numero}/depositar")
    public  ResponseEntity<ContaResumoDTO> depositar(@PathVariable String numero, @Valid @RequestBody ValorSaqueDepositoDTO dto){
        return ResponseEntity.ok(contaService.depositar(numero, dto));
    }

    @PostMapping("/{numero}/transferir")
    public ResponseEntity<ContaResumoDTO> transferir(@PathVariable String numero, @Valid @RequestBody TransferenciaDTO dto){
        return ResponseEntity.ok(contaService.transferir(numero, dto));
    }

    @PostMapping("/{numero}/rendimento")
    public ResponseEntity<ContaResumoDTO> aplicarRendimento(@PathVariable String numero){
        return ResponseEntity.ok(contaService.aplicarRendimento(numero));
    }


}
