package com.senai.conta_bancaria.domain.exception;

public class PagamentoNaoAssociadoTaxaException extends RuntimeException {
    public PagamentoNaoAssociadoTaxaException() {
        super("O pagamento deve ter pelo menos uma taxa associada");
    }
}
