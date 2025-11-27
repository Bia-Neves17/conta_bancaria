package com.senai.conta_bancaria.interface_ui.controller;

import com.senai.conta_bancaria.application.dto.TaxaDTO;
import com.senai.conta_bancaria.application.service.TaxaService;
import com.senai.conta_bancaria.domain.entity.Taxa;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taxas")
@RequiredArgsConstructor
public class TaxaController {
    private final TaxaService taxaService;

    @PostMapping
    public ResponseEntity<Taxa> criar(@RequestBody TaxaDTO dto) {
        return ResponseEntity.ok(taxaService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<Taxa>> findAll(){
        return ResponseEntity.ok(taxaService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taxa> findById(@PathVariable String id){
        return ResponseEntity.ok(taxaService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id){
        taxaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
