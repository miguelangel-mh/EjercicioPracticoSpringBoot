package com.ejercicio.ejercicioPracticas.validate;

import com.ejercicio.ejercicioPracticas.exception.BusinessException;
import com.ejercicio.ejercicioPracticas.persistence.model.ContactEntity;
import com.ejercicio.ejercicioPracticas.persistence.model.PersonEntity;

import java.util.Optional;

public class PersonValidator {

    private PersonValidator() {
        super();
    }

    public static void validateDuplicatedDni(boolean existsByDni) {
        if (existsByDni) {
            throw new BusinessException("El DNI ya existe");
        }
    }

    public static void validateExistingPerson(Optional<PersonEntity> personEntityOptional) {
        if (personEntityOptional.isEmpty()) {
            throw new BusinessException("Persona no encontrada");
        }
    }

    public static void validateExistingContact(Optional<ContactEntity> contactEntityOptional) {
        if (contactEntityOptional.isEmpty()) {
            throw new BusinessException("Contacto no encontrado");
        }
    }

}
