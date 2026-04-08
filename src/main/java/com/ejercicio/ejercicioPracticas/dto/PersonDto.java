package com.ejercicio.ejercicioPracticas.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO que representa los datos de una persona.
 */
@Schema(description = "DTO de salida de persona")
public class PersonDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador de la persona", example = "1")
    private Long idPerson;

    @Schema(description = "Nombre de la persona", example = "Miguel Ángel")
    private String name;

    @Schema(description = "Apellidos de la persona", example = "Martínez Herrera")
    private String lastName;

    @Schema(description = "Nombre completo de la persona", example = "Miguel Ángel Martínez Herrera")
    private String fullName;

    @Schema(description = "DNI de la persona", example = "12345678A")
    private String dni;

    @Schema(description = "Contacto de la persona")
    private ContactDetailsDto contactDetails;

    public PersonDto() {
        super();
    }

    private PersonDto(final Builder builder) {
        this.idPerson = builder.idPerson;
        this.name = builder.name;
        this.lastName = builder.lastName;
        this.fullName = builder.fullName;
        this.dni = builder.dni;
        this.contactDetails = builder.contactDetails;
    }

    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(final Long idPerson) {
        this.idPerson = idPerson;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(final String dni) {
        this.dni = dni;
    }

    public ContactDetailsDto getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(final ContactDetailsDto contactDetails) {
        this.contactDetails = contactDetails;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long idPerson;
        private String name;
        private String lastName;
        private String fullName;
        private String dni;
        private ContactDetailsDto contactDetails;

        public Builder idPerson(final Long idPerson) {
            this.idPerson = idPerson;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder fullName(final String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder dni(final String dni) {
            this.dni = dni;
            return this;
        }

        public Builder contactDetails(final ContactDetailsDto contactDetails) {
            this.contactDetails = contactDetails;
            return this;
        }

        public PersonDto build() {
            return new PersonDto(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PersonDto{");
        builder.append("idPerson=").append(idPerson);
        builder.append(", name='").append(name).append('\'');
        builder.append(", lastName='").append(lastName).append('\'');
        builder.append(", fullName='").append(fullName).append('\'');
        builder.append(", dni='").append(dni).append('\'');
        builder.append(", contactDetails=").append(contactDetails);
        builder.append('}');
        return builder.toString();
    }
}