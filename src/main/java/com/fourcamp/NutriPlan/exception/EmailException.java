package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class EmailException extends RuntimeException {

    public EmailException(){
        super(Constantes.MSG_EMAIL_INVALIDO);
    }

    public EmailException(String message){
        super(message);
    }

}
