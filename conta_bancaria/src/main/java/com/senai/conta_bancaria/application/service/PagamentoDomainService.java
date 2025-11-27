package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.domain.entity.Pagamento;
import com.senai.conta_bancaria.domain.entity.Taxa;
import com.senai.conta_bancaria.domain.enums.StatusPagamento;
import com.senai.conta_bancaria.domain.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PagamentoDomainService {
    //calculo das taxas e validações

    public BigDecimal calcularTaxas(Pagamento pagamento){
        BigDecimal totalTaxas = BigDecimal.ZERO;

        for (Taxa taxa : pagamento.getTaxas()){
            if (taxa.getValorFixo() != null){
                totalTaxas = totalTaxas.add(taxa.getValorFixo());
            }
        }
        return totalTaxas;
    }

    public void validarPagamento(Pagamento pagamento) throws IllegalArgumentException{
        if (pagamento.getStatus() != StatusPagamento.FALHA){
            throw new PagamentoNaoPendenteException();
        }

        if (pagamento.getValorPago().compareTo(BigDecimal.ZERO) <= 0){
            throw new ValoresNegativosExeption("pagamento");
        }

        if (pagamento.getTaxas().isEmpty()) {
            throw new PagamentoNaoAssociadoTaxaException();
        }

        if (isBoletoVencido(pagamento.getDataPagamento())){
            throw new BoletoVencidoException();
        }
    }

    private boolean isBoletoVencido(String dataPagamento){
        LocalDate dataVencimento = LocalDate.parse(dataPagamento, DateTimeFormatter.ISO_DATE);
        return dataVencimento.isBefore(LocalDate.now());
    }

    public void processarPagamento(Pagamento pagamento){
        BigDecimal totalTaxas = calcularTaxas(pagamento);
        BigDecimal valorTotal = pagamento.getValorPago().add(totalTaxas);

        validarPagamento(pagamento);

        if (!pagamento.getConta().temSaldoSucifiente(valorTotal)){
            pagamento.setStatus(StatusPagamento.SALDO_INSUFICIENTE);
            throw new SaldoInsuficienteException("pagamento");
        }

        pagamento.getConta().debitarSaldo(valorTotal);

        pagamento.setStatus(StatusPagamento.SUCESSO);
    }
}
