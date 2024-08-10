package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class SenhaException extends RuntimeException{

    public SenhaException(){
        super(Constantes.MSG_SENHA_INVALIDO);
    }

    public SenhaException(String message){
        super(message);
    }
}
