package com.ejercicio.ejercicioPracticas.validate;

import com.ejercicio.ejercicioPracticas.exception.BusinessException;
import com.ejercicio.ejercicioPracticas.persistence.model.ContactEntity;
import com.ejercicio.ejercicioPracticas.persistence.model.PersonEntity;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PersonValidatorTest {

    @Test
    void validateDuplicatedDniTest() {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> PersonValidator.validateDuplicatedDni(true)
        );

        assertEquals("El DNI ya existe", exception.getMessage());
    }

    @Test
    void validateDuplicatedDniNotExistsTest() {
        assertDoesNotThrow(() -> PersonValidator.validateDuplicatedDni(false));
    }

    @Test
    void validateExistingPersonTest() {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> PersonValidator.validateExistingPerson(Optional.empty())
        );

        assertEquals("Persona no encontrada", exception.getMessage());
    }

    @Test
    void validateExistingPersonExistsTest() {
        PersonEntity personEntity = new PersonEntity();

        assertDoesNotThrow(() -> PersonValidator.validateExistingPerson(Optional.of(personEntity)));
    }

    @Test
    void validateExistingContactTest() {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> PersonValidator.validateExistingContact(Optional.empty())
        );

        assertEquals("Contacto no encontrado", exception.getMessage());
    }

    @Test
    void validateExistingContactExistsTest() {
        ContactEntity contactEntity = new ContactEntity();

        assertDoesNotThrow(() -> PersonValidator.validateExistingContact(Optional.of(contactEntity)));
    }
}
