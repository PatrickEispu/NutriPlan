package com.fourcamp.NutriPlan.exception;

public class ClienteException extends RuntimeException{

    public ClienteException(String msgClienteInvalido) {
        super(msgClienteInvalido);
    }

}
