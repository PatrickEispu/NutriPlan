package com.fourcamp.NutriPlan.exception;

import com.fourcamp.NutriPlan.utils.Constantes;

public class CategoriaException extends RuntimeException{

    public CategoriaException(){
        super(Constantes.MSG_CATEGORIA_INVALIDO);
    }

    public CategoriaException(String message){
        super(message);
    }
}
