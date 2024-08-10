package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class PlanoException extends RuntimeException {

    public PlanoException(){
        super(Constantes.MSG_PLANO_DATA);
    }

    public PlanoException(String message){
        super(message);
    }
}
