package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class ObjetivoException extends RuntimeException{

    public ObjetivoException(String s)
    {
        super(s);
    }

    public ObjetivoException() {
        super(Constantes.MSG_OBJETIVO_META_INVALIDO);
    }
}
