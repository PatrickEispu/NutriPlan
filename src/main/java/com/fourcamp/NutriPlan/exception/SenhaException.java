package com.fourcamp.NutriPlan.exception;

public class SenhaException extends RuntimeException {
    public SenhaException(String msgSenhaInvalido) {
        super(msgSenhaInvalido);
    }
}