package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class TempoMetaException extends RuntimeException{

    public TempoMetaException(){
        super(Constantes.MSG_TEMPO_META_INVALIDO);
    }

    public TempoMetaException(String message){
        super(message);
    }
}
