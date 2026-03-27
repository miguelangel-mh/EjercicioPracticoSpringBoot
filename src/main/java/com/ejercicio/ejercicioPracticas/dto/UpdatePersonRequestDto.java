package com.ejercicio.ejercicioPracticas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

@Schema(description = "DTO de entrada para actualizar persona")
public class UpdatePersonRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Nombre de la persona", example = "Miguel Ángel")
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Schema(description = "Apellidos de la persona", example = "Martinez Herrera")
    @NotBlank(message = "Los apellidos son obligatorios")
    private String lastName;

    @Schema(description = "Contacto de la persona")
    @NotNull(message = "Falta información de contacto")
    @Valid
    private ContactDetailsDto contactDetails;

    public UpdatePersonRequestDto() {
        super();
    }

    private UpdatePersonRequestDto(final Builder builder) {
        this.name = builder.name;
        this.lastName = builder.lastName;
        this.contactDetails = builder.contactDetails;
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

        private String name;
        private String lastName;
        private ContactDetailsDto contactDetails;

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder contactDetails(final ContactDetailsDto contactDetails) {
            this.contactDetails = contactDetails;
            return this;
        }

        public UpdatePersonRequestDto build() {
            return new UpdatePersonRequestDto(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UpdatePersonRequestDto{");
        builder.append("name='").append(name).append('\'');
        builder.append(", lastName='").append(lastName).append('\'');
        builder.append(", contactDetails=").append(contactDetails);
        builder.append('}');
        return builder.toString();
    }

}
