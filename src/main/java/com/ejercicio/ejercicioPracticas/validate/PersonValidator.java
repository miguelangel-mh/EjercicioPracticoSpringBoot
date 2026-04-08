package com.ejercicio.ejercicioPracticas.validate;

import com.ejercicio.ejercicioPracticas.exception.BusinessException;
import com.ejercicio.ejercicioPracticas.persistence.model.ContactEntity;
import com.ejercicio.ejercicioPracticas.persistence.model.PersonEntity;

import java.util.Optional;

/**
 * Clase utilitaria encargada de validar distintas reglas de negocio
 * relacionadas con personas y contactos.
 */
public class PersonValidator {

    /**
     * Constructor privado para evitar la instanciación de la clase utilitaria.
     */
    private PersonValidator() {
        super();
    }

    /**
     * Valida si un DNI ya existe en el sistema.
     *
     * @param existsByDni indica si el DNI ya existe.
     * @throws BusinessException si el DNI ya existe.
     */
    public static void validateDuplicatedDni(boolean existsByDni) {
        if (existsByDni) {
            throw new BusinessException("El DNI ya existe");
        }
    }

    /**
     * Valida que una persona exista en el sistema.
     *
     * @param personEntityOptional contenedor opcional con la persona.
     * @throws BusinessException si la persona no existe.
     */
    public static void validateExistingPerson(Optional<PersonEntity> personEntityOptional) {
        if (personEntityOptional.isEmpty()) {
            throw new BusinessException("Persona no encontrada");
        }
    }

    /**
     * Valida que un contacto exista en el sistema.
     *
     * @param contactEntityOptional contenedor opcional con el contacto.
     * @throws BusinessException si el contacto no existe.
     */
    public static void validateExistingContact(Optional<ContactEntity> contactEntityOptional) {
        if (contactEntityOptional.isEmpty()) {
            throw new BusinessException("Contacto no encontrado");
        }
    }

}