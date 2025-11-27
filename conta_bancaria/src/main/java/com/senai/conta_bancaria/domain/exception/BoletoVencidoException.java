package com.senai.conta_bancaria.domain.exception;

public class BoletoVencidoException extends RuntimeException {
    public BoletoVencidoException() {
        super("o boleto está vencido e não pode ser pago");
    }
}
