package com.ejercicio.ejercicioPracticas.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Anotación personalizada para validar un DNI español.
 *
 * Esta validación comprueba que el valor tenga un formato correcto
 * de 8 dígitos seguidos de una letra y que dicha letra coincida con
 * la calculada a partir del número.
 *
 * La validación real se delega en la clase {@link DniValidator}.
 */

@Documented
@Constraint(validatedBy = DniValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDni {

    /**
     * Mensaje de error por defecto que se devolverá cuando el DNI no sea válido.
     *
     * @return mensaje de error por defecto
     */
    String message() default "El DNI no tiene un formato válido o la letra no es correcta";

    /**
     * Permite agrupar restricciones de validación.
     *
     * @return grupos de validación
     */
    Class<?>[] groups() default {};

    /**
     * Permite asociar metadatos adicionales a la restricción.
     *
     * @return payload asociado a la validación
     */
    Class<? extends Payload>[] payload() default {};
}
