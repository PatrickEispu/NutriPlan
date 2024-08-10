package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class AutenticacaoException extends RuntimeException{

    public AutenticacaoException(){
        super(Constantes.MSG_SENHA_INCORRETA);
    }

    public AutenticacaoException(String message){
        super(message);
    }
}
