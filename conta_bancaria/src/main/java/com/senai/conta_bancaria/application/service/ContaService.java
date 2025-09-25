package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.application.dto.ContaResumoDTO;
import com.senai.conta_bancaria.domain.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;

    public List<ContaResumoDTO> listarContasAtivas() {
        return contaRepository.findAllByAtivaTrue()
                .stream()
                .map(ContaResumoDTO::fromEntity)
                .toList();
    }
}
