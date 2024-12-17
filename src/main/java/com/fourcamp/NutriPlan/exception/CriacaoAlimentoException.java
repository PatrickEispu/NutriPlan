package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class CriacaoAlimentoException extends RuntimeException{

    public CriacaoAlimentoException(String msgCriacaoAlimentoExceptionInvalido) {
        super(msgCriacaoAlimentoExceptionInvalido);
    }
}
