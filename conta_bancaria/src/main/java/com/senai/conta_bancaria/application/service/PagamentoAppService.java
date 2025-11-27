package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.domain.entity.Pagamento;
import com.senai.conta_bancaria.domain.entity.Taxa;
import com.senai.conta_bancaria.domain.enums.StatusPagamento;
import com.senai.conta_bancaria.domain.exception.PagamentoInvalidoException;
import com.senai.conta_bancaria.domain.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PagamentoAppService {
    private final PagamentoDomainService pagamentoDomainService;
    private final PagamentoRepository pagamentoRepository;

    public void processarPagamento(Pagamento pagamento, Set<Taxa> taxas){
        pagamento.setTaxas(taxas);

        try {
            pagamentoDomainService.processarPagamento(pagamento);
        } catch (IllegalStateException | IllegalArgumentException e){
            pagamento.setStatus(StatusPagamento.FALHA);
            pagamentoRepository.save(pagamento);
            throw new PagamentoInvalidoException();
        }

        pagamentoRepository.save(pagamento);
    }
}
