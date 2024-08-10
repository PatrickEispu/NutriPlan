package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class FormatoException extends RuntimeException{

    public FormatoException(){
        super(Constantes.MSG_FORMATO_INVALIDO);
    }

    public FormatoException(String message){
        super(message);
    }
}
