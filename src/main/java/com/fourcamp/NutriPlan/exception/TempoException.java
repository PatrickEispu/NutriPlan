package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class TempoException extends RuntimeException{

    public TempoException(String s) {
        super(s);
    }

     public TempoException()
    {
        super(Constantes.MSG_TEMPO_META_INVALIDO);
    }

}
