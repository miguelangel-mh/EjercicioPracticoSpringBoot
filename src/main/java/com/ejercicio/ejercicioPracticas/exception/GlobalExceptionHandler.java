package com.ejercicio.ejercicioPracticas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones de la aplicación.
 * Centraliza el tratamiento de errores de negocio, validación y errores genéricos.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestiona las excepciones de negocio lanzadas por la aplicación.
     *
     * @param exception excepción de negocio producida.
     * @return respuesta HTTP con código 400 y el mensaje de error.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(final BusinessException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    /**
     * Gestiona los errores de validación producidos en los datos de entrada.
     *
     * @param exception excepción de validación producida.
     * @return respuesta HTTP con código 400 y el primer mensaje de error encontrado.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(
            final MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Validation error");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    /**
     * Gestiona cualquier excepción no controlada de forma específica.
     *
     * @param exception excepción producida.
     * @return respuesta HTTP con código 500 y un mensaje genérico.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(final Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error");
    }

}