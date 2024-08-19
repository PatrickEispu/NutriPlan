package com.fourcamp.NutriPlan.exception;

public class EmailException extends RuntimeException {
    public EmailException(String msgEmailInvalido) {
        super(msgEmailInvalido);
    }
}