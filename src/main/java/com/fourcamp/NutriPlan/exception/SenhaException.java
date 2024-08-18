package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class SenhaException extends RuntimeException {
    public SenhaException(String msgSenhaInvalido) {
        super(msgSenhaInvalido);
    }
}