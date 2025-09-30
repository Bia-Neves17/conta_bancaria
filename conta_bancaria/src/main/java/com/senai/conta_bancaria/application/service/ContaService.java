package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.application.dto.ContaAtualizadaDTO;
import com.senai.conta_bancaria.application.dto.ContaResumoDTO;
import com.senai.conta_bancaria.domain.entity.Conta;
import com.senai.conta_bancaria.domain.entity.ContaCorrente;
import com.senai.conta_bancaria.domain.entity.ContaPoupanca;
import com.senai.conta_bancaria.domain.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContaService {

    private final ContaRepository contaRepository;

    @Transactional(readOnly = true)
    public List<ContaResumoDTO> listarContasAtivas() {
        return contaRepository.findAllByAtivaTrue()
                .stream()
                .map(ContaResumoDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ContaResumoDTO buscarContaNumeroAtiva(String numero) {
        var conta = contaRepository.findByNumeroAndAtivaTrue(numero).orElseThrow(()-> new RuntimeException("Conta não encontrada"));
        return ContaResumoDTO.fromEntity(conta);
    }

    public ContaResumoDTO atualizarConta(String numero, ContaAtualizadaDTO dto) {
        Conta conta = contaRepository.findByNumeroAndAtivaTrue(numero).orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (conta instanceof ContaPoupanca poupanca){
            poupanca.setRendimento(dto.rendimento());
        } else if (conta instanceof ContaCorrente corrente) {
            corrente.setLimite(dto.limite());
            corrente.setTaxa(dto.taxa());
        } else {
            throw new RuntimeException("Tipo de conta invãlido");
        }
        conta.setSaldo(dto.saldo());
        return ContaResumoDTO.fromEntity(contaRepository.save(conta));
    }

    public void deletarConta(String numero) {
        Conta conta = contaRepository.findByNumeroAndAtivaTrue(numero).orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.setAtiva(false);
        contaRepository.save(conta);
    }
}
