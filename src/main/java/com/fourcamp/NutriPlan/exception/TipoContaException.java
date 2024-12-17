package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class TipoContaException extends RuntimeException{
    public TipoContaException(String msgTipoContaExceptionInvalido) {
        super(msgTipoContaExceptionInvalido);
    }
}
