package com.senai.conta_bancaria.domain.exception;

public class TipoDeContaInvalidaException extends RuntimeException {
    public TipoDeContaInvalidaException(String tipoC) {
        super("Tipo de conta " +tipoC+" inválida. Os tipos válidos são: 'CORRENTE' e 'POUPANCA'");
    }
}
