package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class TipoContaException extends RuntimeException{

    public TipoContaException(String s){
        super(Constantes.MSG_TIPO_CONTA_NAO_ENCONTRADA);
    }
}
