package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class CategoriaAlimentoException extends RuntimeException{
    public CategoriaAlimentoException(String s){
        super(Constantes.MSG_CATEGORIA_ALIMENTO_INVALIDO);
    }
}
