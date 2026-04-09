package com.ejercicio.ejercicioPracticas.validate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DniValidatorTest {

    private final DniValidator validator = new DniValidator();

    @Test
    void shouldReturnTrueWhenDniIsValid() {
        assertTrue(validator.isValid("12345678Z", null));
    }

    @Test
    void shouldReturnFalseWhenFormatIsInvalid() {
        assertFalse(validator.isValid("1234Z", null));
    }

    @Test
    void shouldReturnFalseWhenLetterIsIncorrect() {
        assertFalse(validator.isValid("12345678A", null));
    }

    @Test
    void shouldReturnTrueWhenValueIsNullBecauseNotBlankHandlesIt() {
        assertTrue(validator.isValid(null, null));
    }

    @Test
    void shouldReturnTrueWhenValueIsBlankBecauseNotBlankHandlesIt() {
        assertTrue(validator.isValid("   ", null));
    }
}
