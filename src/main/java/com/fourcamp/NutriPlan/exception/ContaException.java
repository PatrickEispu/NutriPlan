package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class ContaException extends RuntimeException{

    public ContaException(String s){
        super(Constantes.MSG_ERRO_CRIACAO_CONTA);
    }
}
