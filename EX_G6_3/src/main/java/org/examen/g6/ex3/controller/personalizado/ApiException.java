package org.examen.g6.ex3.controller.personalizado;

public class ApiException extends Exception{

    public ApiException(String mensaje){
        super(mensaje);
    }

}