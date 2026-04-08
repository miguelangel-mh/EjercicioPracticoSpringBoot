package com.ejercicio.ejercicioPracticas.exception;

/**
 * Excepción de negocio utilizada para indicar incumplimientos
 * de las reglas funcionales de la aplicación.
 */
public class BusinessException extends RuntimeException{

    /**
     * Crea una nueva excepción de negocio con el mensaje indicado.
     *
     * @param message mensaje descriptivo del error producido.
     */
    public BusinessException(String message){
        super(message);
    }

}
