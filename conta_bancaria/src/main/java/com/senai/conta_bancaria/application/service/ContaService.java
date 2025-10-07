package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.application.dto.ContaAtualizadaDTO;
import com.senai.conta_bancaria.application.dto.ContaResumoDTO;
import com.senai.conta_bancaria.application.dto.TransferenciaDTO;
import com.senai.conta_bancaria.application.dto.ValorSaqueDepositoDTO;
import com.senai.conta_bancaria.domain.entity.Conta;
import com.senai.conta_bancaria.domain.entity.ContaCorrente;
import com.senai.conta_bancaria.domain.entity.ContaPoupanca;
import com.senai.conta_bancaria.domain.exception.EntidadeNaoEncontradaException;
import com.senai.conta_bancaria.domain.exception.RendimentoInvalidoException;
import com.senai.conta_bancaria.domain.exception.TipoDeContaInvalidaException;
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
        var conta = buscarContaAtivaPorNumero(numero);
        return ContaResumoDTO.fromEntity(conta);
    }

    public ContaResumoDTO atualizarConta(String numero, ContaAtualizadaDTO dto) {
        Conta conta = buscarContaAtivaPorNumero(numero);

        if (conta instanceof ContaPoupanca poupanca){
            poupanca.setRendimento(dto.rendimento());
        } else if (conta instanceof ContaCorrente corrente) {
            corrente.setLimite(dto.limite());
            corrente.setTaxa(dto.taxa());
        } else {
            throw new TipoDeContaInvalidaException("");
        }
        conta.setSaldo(dto.saldo());
        return ContaResumoDTO.fromEntity(contaRepository.save(conta));
    }

    public void deletarConta(String numero) {
        Conta conta = buscarContaAtivaPorNumero(numero);
        conta.setAtiva(false);
        contaRepository.save(conta);
    }

    public ContaResumoDTO sacar(String numero, ValorSaqueDepositoDTO dto) {
        Conta conta = buscarContaAtivaPorNumero(numero);
        conta.sacar(dto.valor());
        return ContaResumoDTO.fromEntity(contaRepository.save(conta));
    }

    public ContaResumoDTO depositar(String numero, ValorSaqueDepositoDTO dto) {
        Conta conta = buscarContaAtivaPorNumero(numero);

        conta.depositar(dto.valor());
        return ContaResumoDTO.fromEntity(contaRepository.save(conta));
    }

    private Conta buscarContaAtivaPorNumero (String numero){
        return contaRepository.findByNumeroAndAtivaTrue(numero).orElseThrow(() -> new EntidadeNaoEncontradaException("conta"));
    }

    public ContaResumoDTO transferir(String numero, TransferenciaDTO dto) {
        Conta contaOrigem = buscarContaAtivaPorNumero(numero);
        Conta contaDestino = buscarContaAtivaPorNumero(numero);

        contaOrigem.transferir(dto.valor(), contaDestino);

        contaRepository.save(contaDestino);
        return ContaResumoDTO.fromEntity(contaRepository.save(contaOrigem));
    }

    public ContaResumoDTO aplicarRendimento(String numero) {
        Conta conta = buscarContaAtivaPorNumero(numero);
        if (conta instanceof ContaPoupanca contaPoupanca){
            contaPoupanca.aplicarRendimento();
            return ContaResumoDTO.fromEntity(contaRepository.save(contaPoupanca));
        }
        throw new RendimentoInvalidoException();
    }
}
