package com.senai.conta_bancaria.domain.exception;

public class ValoresNegativosExeption extends RuntimeException {
    public ValoresNegativosExeption(String operacao) {
        super("Não é possível realizar "+operacao+" com valores negativos");
    }
}
