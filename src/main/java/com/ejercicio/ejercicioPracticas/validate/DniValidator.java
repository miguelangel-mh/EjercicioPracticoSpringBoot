package com.ejercicio.ejercicioPracticas.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Implementación de la lógica de validación para la anotación {@link ValidDni}.
 *
 * Esta clase valida un DNI español comprobando:
 *
 *    Que el valor tenga el formato correcto: 8 dígitos y 1 letra.</li>
 *    Que la letra corresponda realmente al número introducido.</li>
 */

public class DniValidator implements ConstraintValidator<ValidDni, String> {

    /**
     * Secuencia oficial de letras utilizada para calcular la letra del DNI.
     */
    private static final String DNI_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";

    /**
     * Valida si el DNI recibido es correcto.
     *
     * @param dni valor del DNI a validar
     * @param context contexto de validación
     * @return {@code true} si el DNI es válido o si es nulo/en blanco
     *         para que lo gestione {@code @NotBlank}; {@code false} en caso contrario
     */
    @Override
    public boolean isValid(String dni, ConstraintValidatorContext context) {
        if (dni == null || dni.isBlank()) {
            return true;
        }

        String normalizedDni = dni.trim().toUpperCase();

        if (!normalizedDni.matches("\\d{8}[A-Z]")) {
            return false;
        }

        int number = Integer.parseInt(normalizedDni.substring(0, 8));
        char providedLetter = normalizedDni.charAt(8);
        char expectedLetter = DNI_LETTERS.charAt(number % 23);

        return providedLetter == expectedLetter;
    }
}
