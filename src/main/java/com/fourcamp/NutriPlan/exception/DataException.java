package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class DataException extends RuntimeException{

    public DataException(){
        super(Constantes.MSG_DATA_INVALIDO);
    }

    public DataException(String message){
        super(message);
    }
}
