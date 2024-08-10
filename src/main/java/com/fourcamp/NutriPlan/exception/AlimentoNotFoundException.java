package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class AlimentoNotFoundException extends RuntimeException{

    public AlimentoNotFoundException(){
        super(Constantes.MSG_ALIMENTO_NAO_ENCONTRADO);
    }

    public AlimentoNotFoundException(String message){
        super(message);
    }
}
