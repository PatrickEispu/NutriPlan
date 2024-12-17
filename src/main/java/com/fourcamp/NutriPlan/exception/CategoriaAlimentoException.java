package com.fourcamp.NutriPlan.exception;

public class CategoriaAlimentoException extends RuntimeException{

    public CategoriaAlimentoException(String msgCategoriaAliemntoInvalido) {
        super(msgCategoriaAliemntoInvalido);
    }
}
