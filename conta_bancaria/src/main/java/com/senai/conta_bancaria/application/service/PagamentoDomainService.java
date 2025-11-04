package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.domain.entity.Pagamento;
import com.senai.conta_bancaria.domain.entity.Taxa;
import com.senai.conta_bancaria.domain.enums.StatusPagamento;
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
            throw new IllegalArgumentException("O pagamento não está pendente");
        }

        if (pagamento.getValorPago().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("O valor do pagamento deve ser maior que zero");
        }

        if (pagamento.getTaxas().isEmpty()) {
            throw new IllegalArgumentException("O pagamento deve ter pelo menos uma taxa associada");
        }

        if (isBoletoVencido(pagamento.getDataPagamento())){
            throw new IllegalArgumentException("o boleto está vencido e não pode ser pago");
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
            throw new IllegalStateException("Saldo insuficiente para realizar o pagamento.");
        }

        pagamento.getConta().debitarSaldo(valorTotal);

        pagamento.setStatus(StatusPagamento.SUCESSO);
    }
}
