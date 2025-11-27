package com.senai.conta_bancaria.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

public class TaxaInvalidaException extends RuntimeException {
    public TaxaInvalidaException() {
        super("Taxa não encontrada ou inválida");
    }
}
