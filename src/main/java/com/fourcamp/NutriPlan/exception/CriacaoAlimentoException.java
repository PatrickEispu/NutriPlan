package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class CriacaoAlimentoException extends RuntimeException{

    public CriacaoAlimentoException(){
        super(Constantes.MSG_ERRO_CRIACAO_ALIMENTO);
    }

    public CriacaoAlimentoException(String message){
        super(message);
    }
}
