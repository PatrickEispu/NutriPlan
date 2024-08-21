package com.fourcamp.NutriPlan.exception;

public class CategoriaClienteException extends RuntimeException{

    public CategoriaClienteException(String msgCategoriaClienteInvalido) {
        super(msgCategoriaClienteInvalido);
    }
}
